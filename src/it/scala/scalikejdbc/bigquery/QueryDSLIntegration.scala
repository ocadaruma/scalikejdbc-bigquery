package scalikejdbc.bigquery

import java.time.{ZonedDateTime, ZoneId}

import com.google.cloud.bigquery.DatasetId
import org.scalatest.FlatSpec
import scalikejdbc._

class QueryDSLIntegration extends FlatSpec with BigQueryFixture {

  /* ========================
  Suppose that tables and rows like following exist in DataSet:"scalikejdbc_bigquery_integration".

  create table scalikejdbc_bigquery_integration.post (
    id INT64,
    body STRING,
    posted_at TIMESTAMP
  );

  create table scalikejdbc_bigquery_integration.tag (
    id INT64,
    post_id INT64,
    name STRING
  );

  insert into scalikejdbc_bigquery_integration.post (id, body, posted_at) values
    (1, 'first post about jvm languages', '2017-03-23T10:00:00.000000'),
    (2, 'second post', '2017-03-24T10:00:00.000000'),
    (3, 'third post about lightweight languages', '2017-03-25T10:00:00.000000');

  insert into scalikejdbc_bigquery_integration.tag(id, post_id, name) values
    (1, 1, 'java'),
    (2, 1, 'scala'),
    (3, 3, 'ruby'),
    (4, 3, 'python'),
    (5, 3, 'perl');

  ======================== */

  it should "work correctly on standard SQL" in {
    val bigQuery = mkBigQuery()
    val queryConfig = QueryConfig()

    val dataset = DatasetId.of(projectId(), "scalikejdbc_bigquery_integration")

    val executor = new QueryExecutor(bigQuery, queryConfig)

    import Post.p, Tag.t, Post.postIdBinders
    val sub = SubQuery.syntax("sub").include(p)

    val response = bq {
      select(sub.result.*, t.result.*).from(
        select(p.result.*).from(Post in dataset as p)
          .where.in(p.id, Seq(PostId(1), PostId(2), PostId(3)))
          .limit(3)
          .offset(0)
          .as(sub)
      )
        .leftJoin(Tag in dataset as t)
        .on(sub(p).id, t.postId)
        .orderBy(sub(p).id, t.id)
    }
      .one(Post(_, sub(p).resultName))
      .toMany(Tag.opt)
      .map(PostWithTags)
      .list
      .run(executor)

    // handle environment-dependent TimeZone difference
    val result = response.result.map(p => p.copy(post = p.post.copy(postedAt = p.post.postedAt.withZoneSameInstant(ZoneId.of("UTC")))))

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

  it should "work correctly on standard SQL single result" in {
    val bigQuery = mkBigQuery()
    val queryConfig = QueryConfig()

    val dataset = DatasetId.of(projectId(), "scalikejdbc_bigquery_integration")

    val executor = new QueryExecutor(bigQuery, queryConfig)

    import Post.p, Post.postIdBinders

    val response = bq {
      select(sqls.count).from(Post in dataset as p)
        .where.in(p.id, Seq(PostId(1), PostId(2)))
    }
      .map(_.long(0))
      .single
      .run(executor)

    val result = response.result.get

    assert(result == 2)
  }

  it should "correctly bind timeZone parameter" in {
    val bigQuery = mkBigQuery()
    val queryConfig = QueryConfig()

    val dataset = DatasetId.of(projectId(), "scalikejdbc_bigquery_integration")

    val executor = new QueryExecutor(bigQuery, queryConfig)

    import Post.p

    val response = bq {
      select(p.result.id).from(Post in dataset as p)
        .where.eq(p.postedAt, ZonedDateTime.of(2017, 3, 23, 10, 0, 0, 0, ZoneId.of("UTC")))
    }
      .map(_.long(p.resultName.id))
      .single
      .run(executor)

    val result = response.result.get

    assert(result == 1L)
  }
}
