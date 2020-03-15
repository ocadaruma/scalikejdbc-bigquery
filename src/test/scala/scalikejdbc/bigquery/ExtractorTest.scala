package scalikejdbc.bigquery

import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpec
import scalikejdbc.{Extractor => _, _}

class ExtractorTest extends AnyFunSpec with MockFactory {

  describe("iterator") {
    it("should return iterator of rows") {

      val statement = sqls"select * from user"
      val extractor = new Extractor[Int](statement, rs => rs.int("id"))

      // stub ResultSet
      val user1 = stub[WrappedResultSet]; (user1.int(_: String)).when("id").returns(1)
      val user2 = stub[WrappedResultSet]; (user2.int(_: String)).when("id").returns(2)
      val user3 = stub[WrappedResultSet]; (user3.int(_: String)).when("id").returns(3)

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator(user1, user2, user3)) // FIXME: stub underlying QueryResponse appropriately (other than null)
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.iterator.run(executorStub).result.toSeq == Iterator(1, 2, 3).toSeq)
    }

    it("should return empty when result is empty") {

      val statement = sqls"select * from user"
      val extractor = new Extractor[Int](statement, rs => rs.int("id"))

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator.empty)
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.iterator.run(executorStub).result.toSeq == Iterator.empty.toSeq)
    }
  }

  describe("list") {
    it("should return list of rows") {

      val statement = sqls"select * from user"
      val extractor = new Extractor[Int](statement, rs => rs.int("id"))

      // stub ResultSet
      val user1 = stub[WrappedResultSet]; (user1.int(_: String)).when("id").returns(1)
      val user2 = stub[WrappedResultSet]; (user2.int(_: String)).when("id").returns(2)
      val user3 = stub[WrappedResultSet]; (user3.int(_: String)).when("id").returns(3)

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator(user1, user2, user3)) // FIXME: stub underlying QueryResponse appropriately (other than null)
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.list.run(executorStub).result == Seq(1, 2, 3))
    }

    it("should return Nil when result is empty") {

      val statement = sqls"select * from user"
      val extractor = new Extractor[Int](statement, rs => rs.int("id"))

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator.empty)
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.list.run(executorStub).result == Nil)
    }
  }

  describe("single") {
    it("should return single row") {

      val statement = sqls"select * from user limit 1"
      val extractor = new Extractor[Int](statement, rs => rs.int("id"))

      // stub ResultSet
      val user = stub[WrappedResultSet]; (user.int(_: String)).when("id").returns(1)

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator(user))
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.single.run(executorStub).result.contains(1))
    }

    it("should return None when result is empty") {

      val statement = sqls"select * from user limit 1"
      val extractor = new Extractor[Int](statement, rs => rs.int("id"))

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator.empty)
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.single.run(executorStub).result.isEmpty)
    }

    it("should throw exception when result is two or more") {

      val statement = sqls"select * from user"
      val extractor = new Extractor[Int](statement, rs => rs.int("id"))

      // stub ResultSet
      val user1 = stub[WrappedResultSet]; (user1.int(_: String)).when("id").returns(1)
      val user2 = stub[WrappedResultSet]; (user2.int(_: String)).when("id").returns(2)

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator(user1, user2))
      (executorStub.execute _).when(statement).returns(responseStub)

      intercept[TooManyRowsException] {
        extractor.single.run(executorStub).result
      }
    }
  }

  describe("first") {
    it("should return single row") {

      val statement = sqls"select * from user order by id"
      val extractor = new Extractor[Int](statement, rs => rs.int("id"))

      // stub ResultSet
      val user1 = stub[WrappedResultSet]; (user1.int(_: String)).when("id").returns(1)
      val user2 = stub[WrappedResultSet]; (user2.int(_: String)).when("id").returns(2)
      val user3 = stub[WrappedResultSet]; (user3.int(_: String)).when("id").returns(3)

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator(user1, user2, user3))
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.first.run(executorStub).result.contains(1))
    }

    it("should return None when result is empty") {

      val statement = sqls"select * from user order by id"
      val extractor = new Extractor[Int](statement, rs => rs.int("id"))

      // stub executor
      val executorStub = stub[QueryExecutor]
      val responseStub = new WrappedQueryResponse(null, Iterator.empty)
      (executorStub.execute _).when(statement).returns(responseStub)

      assert(extractor.first.run(executorStub).result.isEmpty)
    }
  }
}
