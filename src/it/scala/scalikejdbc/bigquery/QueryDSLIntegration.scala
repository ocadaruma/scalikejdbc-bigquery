package scalikejdbc.bigquery

import java.time.{ZonedDateTime, ZoneId}

import com.google.cloud.bigquery.DatasetId
import org.scalatest.FlatSpec
import scalikejdbc._, BqExtension._

class QueryDSLIntegration extends FlatSpec with BigQueryFixture {

  //========================
  // Suppose that tables and rows like following exist in DataSet:"scalikejdbc_bigquery_integration".
  //
  //     create table post (
  //       id INT64,
  //       body STRING,
  //       posted_at TIMESTAMP,
  //     );
  //
  //     create table tag (
  //       id INT64,
  //       post_id INT64,
  //       name STRING
  //     );
  //
  //     insert into post(id, body, posted_at) values (1, 'first post about jvm languages', '2017-03-23T10:00:00.000000+0000')
  //     insert into tag(id, post_id, name) values (1, 1, 'java');
  //     insert into tag(id, post_id, name) values (2, 1, 'scala');
  //
  //     insert into post(id, body, posted_at) values (2, 'second post', '2017-03-24T10:00:00.000000+0000')
  //
  //     insert into post(id, body, posted_at) values (3, 'third post about lightweight languages', '2017-03-25T10:00:00.000000+0000')
  //     insert into tag(id, post_id, name) values (3, 3, 'ruby');
  //     insert into tag(id, post_id, name) values (4, 3, 'python');
  //     insert into tag(id, post_id, name) values (5, 3, 'perl');
  //
  //========================
  it should "work correctly on standard SQL" in {
    val bigQuery = mkBigQuery()
    val queryConfig = QueryConfig()

    val dataset = DatasetId.of(projectId(), "scalikejdbc_bigquery_integration")

    val executor = new QueryExecutor(bigQuery, queryConfig)

    import Post.p, Tag.t, Post.postIdBinders
    val sub = SubQuery.syntax("sub").include(p)

    val response = bq {
      select(sub.result.*, t.result.*).from(
        select(p.result.*).from(standardTableReference(dataset, Post.tableName, Some(p.tableAliasName)))
          .where.in(p.id, Seq(PostId(1), PostId(2), PostId(3)))
          .limit(3)
          .offset(0)
          .as(sub)
      )
        .leftJoin(standardTableReference(dataset, Tag.tableName, Some(t.tableAliasName)))
        .on(sub(p).id, t.postId)
        .orderBy(sub(p).id, t.id)
    }
      .one(Post(_, sub(p).resultName))
      .toMany(Tag.opt)
      .map(PostWithTags)
      .list
      .run(executor)

    val result = response.result

    assert(result.size == 3)

    assert(result(0) == PostWithTags(
      Post(PostId(1), "first post about jvm languages", ZonedDateTime.of(2017, 3, 23, 10, 0, 0, 0, ZoneId.of("UTC"))),
      Seq(
        Tag(TagId(1), PostId(1), "java"),
        Tag(TagId(2), PostId(1), "scala")
      )
    ))

    assert(result(1) == PostWithTags(
      Post(PostId(2), "second post", ZonedDateTime.of(2017, 3, 24, 10, 0, 0, 0, ZoneId.of("UTC"))),
      Nil
    ))

    assert(result(2) == PostWithTags(
      Post(PostId(3), "third post about lightweight languages", ZonedDateTime.of(2017, 3, 25, 10, 0, 0, 0, ZoneId.of("UTC"))),
      Seq(
        Tag(TagId(3), PostId(3), "ruby"),
        Tag(TagId(4), PostId(3), "python"),
        Tag(TagId(5), PostId(3), "perl")
      )
    ))
  }
}
