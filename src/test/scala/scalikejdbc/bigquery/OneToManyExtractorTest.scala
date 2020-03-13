package scalikejdbc.bigquery

import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpec
import scalikejdbc.{Extractor => _, OneToManyExtractor => _, _}

class OneToManyExtractorTest extends AnyFunSpec with MockFactory {

  describe("list") {
    it("should return list of aggregated rows") {

      val statement = sqls"select * from post left join tag"
      val extractor = new OneToManyExtractor[Int, Int, (Int, Seq[Int])](statement, _.int("post_id"), _.intOpt("tag_id"), (_, _))

      // stub ResultSet
      val post1_tag1 = stub[WrappedResultSet]; (post1_tag1.int(_: String)).when("post_id").returns(1); (post1_tag1.intOpt(_: String)).when("tag_id").returns(Some(101))
      val post1_tag2 = stub[WrappedResultSet]; (post1_tag2.int(_: String)).when("post_id").returns(1); (post1_tag2.intOpt(_: String)).when("tag_id").returns(Some(102))
      val post1_tag3 = stub[WrappedResultSet]; (post1_tag3.int(_: String)).when("post_id").returns(1); (post1_tag3.intOpt(_: String)).when("tag_id").returns(Some(103))
      val post2_tag1 = stub[WrappedResultSet]; (post2_tag1.int(_: String)).when("post_id").returns(2); (post2_tag1.intOpt(_: String)).when("tag_id").returns(Some(201))
      val post2_tag2 = stub[WrappedResultSet]; (post2_tag2.int(_: String)).when("post_id").returns(2); (post2_tag2.intOpt(_: String)).when("tag_id").returns(Some(202))
      val post3 = stub[WrappedResultSet]; (post3.int(_: String)).when("post_id").returns(3); (post3.intOpt(_: String)).when("tag_id").returns(None)

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator(post1_tag1, post1_tag2, post1_tag3, post2_tag1, post2_tag2, post3))
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.list.run(executorStub).result == Seq((1, Seq(101, 102, 103)), (2, Seq(201, 202)), (3, Nil)))
    }

    it("should return Nil when result is empty") {

      val statement = sqls"select * from post left join tag"
      val extractor = new OneToManyExtractor[Int, Int, (Int, Seq[Int])](statement, _.int("post_id"), _.intOpt("tag_id"), (_, _))

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator.empty)
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.list.run(executorStub).result.isEmpty)
    }
  }

  describe("single") {
    it("should return single aggregated row") {

      val statement = sqls"select * from post left join tag"
      val extractor = new OneToManyExtractor[Int, Int, (Int, Seq[Int])](statement, _.int("post_id"), _.intOpt("tag_id"), (_, _))

      // stub ResultSet
      val post1_tag1 = stub[WrappedResultSet]; (post1_tag1.int(_: String)).when("post_id").returns(1); (post1_tag1.intOpt(_: String)).when("tag_id").returns(Some(101))
      val post1_tag2 = stub[WrappedResultSet]; (post1_tag2.int(_: String)).when("post_id").returns(1); (post1_tag2.intOpt(_: String)).when("tag_id").returns(Some(102))
      val post1_tag3 = stub[WrappedResultSet]; (post1_tag3.int(_: String)).when("post_id").returns(1); (post1_tag3.intOpt(_: String)).when("tag_id").returns(Some(103))

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator(post1_tag1, post1_tag2, post1_tag3))
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.single.run(executorStub).result.contains((1, Seq(101, 102, 103))))
    }

    it("should return None when result is empty") {

      val statement = sqls"select * from post left join tag"
      val extractor = new OneToManyExtractor[Int, Int, (Int, Seq[Int])](statement, _.int("post_id"), _.intOpt("tag_id"), (_, _))

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator.empty)
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.single.run(executorStub).result.isEmpty)
    }

    it("should throw exception when result is two or more") {

      val statement = sqls"select * from post left join tag"
      val extractor = new OneToManyExtractor[Int, Int, (Int, Seq[Int])](statement, _.int("post_id"), _.intOpt("tag_id"), (_, _))

      // stub ResultSet
      val post1_tag1 = stub[WrappedResultSet]; (post1_tag1.int(_: String)).when("post_id").returns(1); (post1_tag1.intOpt(_: String)).when("tag_id").returns(Some(101))
      val post1_tag2 = stub[WrappedResultSet]; (post1_tag2.int(_: String)).when("post_id").returns(1); (post1_tag2.intOpt(_: String)).when("tag_id").returns(Some(102))
      val post1_tag3 = stub[WrappedResultSet]; (post1_tag3.int(_: String)).when("post_id").returns(1); (post1_tag3.intOpt(_: String)).when("tag_id").returns(Some(103))
      val post2_tag1 = stub[WrappedResultSet]; (post2_tag1.int(_: String)).when("post_id").returns(2); (post2_tag1.intOpt(_: String)).when("tag_id").returns(Some(201))
      val post2_tag2 = stub[WrappedResultSet]; (post2_tag2.int(_: String)).when("post_id").returns(2); (post2_tag2.intOpt(_: String)).when("tag_id").returns(Some(202))
      val post3 = stub[WrappedResultSet]; (post3.int(_: String)).when("post_id").returns(3); (post3.intOpt(_: String)).when("tag_id").returns(None)

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator(post1_tag1, post1_tag2, post1_tag3, post2_tag1, post2_tag2, post3))
      (executorStub.execute _).when(statement).returns(responseStub)

      intercept[TooManyRowsException] {
        extractor.single.run(executorStub).result
      }
    }
  }

  describe("first") {
    it("should return single aggregated row") {

      val statement = sqls"select * from post left join tag"
      val extractor = new OneToManyExtractor[Int, Int, (Int, Seq[Int])](statement, _.int("post_id"), _.intOpt("tag_id"), (_, _))

      // stub ResultSet
      val post1_tag1 = stub[WrappedResultSet]; (post1_tag1.int(_: String)).when("post_id").returns(1); (post1_tag1.intOpt(_: String)).when("tag_id").returns(Some(101))
      val post1_tag2 = stub[WrappedResultSet]; (post1_tag2.int(_: String)).when("post_id").returns(1); (post1_tag2.intOpt(_: String)).when("tag_id").returns(Some(102))
      val post1_tag3 = stub[WrappedResultSet]; (post1_tag3.int(_: String)).when("post_id").returns(1); (post1_tag3.intOpt(_: String)).when("tag_id").returns(Some(103))
      val post2_tag1 = stub[WrappedResultSet]; (post2_tag1.int(_: String)).when("post_id").returns(2); (post2_tag1.intOpt(_: String)).when("tag_id").returns(Some(201))
      val post2_tag2 = stub[WrappedResultSet]; (post2_tag2.int(_: String)).when("post_id").returns(2); (post2_tag2.intOpt(_: String)).when("tag_id").returns(Some(202))
      val post3 = stub[WrappedResultSet]; (post3.int(_: String)).when("post_id").returns(3); (post3.intOpt(_: String)).when("tag_id").returns(None)

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator(post1_tag1, post1_tag2, post1_tag3, post2_tag1, post2_tag2, post3))
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.first.run(executorStub).result.contains((1, Seq(101, 102, 103))))
    }

    it("should return None when result is emptyy") {

      val statement = sqls"select * from post left join tag"
      val extractor = new OneToManyExtractor[Int, Int, (Int, Seq[Int])](statement, _.int("post_id"), _.intOpt("tag_id"), (_, _))

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator.empty)
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.first.run(executorStub).result.isEmpty)
    }
  }
}
