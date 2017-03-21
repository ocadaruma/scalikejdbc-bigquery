package scalikejdbc.bigquery

import org.scalamock.scalatest.MockFactory
import org.scalatest.FunSpec
import scalikejdbc._

class ExtractorTest extends FunSpec with MockFactory {

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
      (executorStub.execute _).when(statement).returns(new ResultSetTraversableMock(Seq(user1, user2, user3)))

      assert(extractor.list.run(executorStub).result == Seq(1, 2, 3))
    }

    it("should return Nil when result is empty") {

      val statement = sqls"select * from user"
      val extractor = new Extractor[Int](statement, rs => rs.int("id"))

      // stub executor
      val executorStub = stub[QueryExecutor]
      (executorStub.execute _).when(statement).returns(new ResultSetTraversableMock(Nil))

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
      (executorStub.execute _).when(statement).returns(new ResultSetTraversableMock(Seq(user)))

      assert(extractor.single.run(executorStub).result == Some(1))
    }

    it("should return None when result is empty") {

      val statement = sqls"select * from user limit 1"
      val extractor = new Extractor[Int](statement, rs => rs.int("id"))

      // stub executor
      val executorStub = stub[QueryExecutor]
      (executorStub.execute _).when(statement).returns(new ResultSetTraversableMock(Nil))

      assert(extractor.single.run(executorStub).result == None)
    }
  }
}
