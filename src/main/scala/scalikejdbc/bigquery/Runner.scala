package scalikejdbc.bigquery

import scalikejdbc._

trait Runner[A] {

  /**
   * Transform ResultSet to desired type.
   */
  protected def mapResultSet(rsTraversable: ResultSetTraversable): A

  /**
   * A SQL statement to be executed.
   */
  def statement: SQLSyntax

  def run(queryExecutor: QueryExecutor): Response[A] = ???

//  def runAsync(queryExecutor: QueryExecutor): Future[Response[A]] = {
//
//  }
}

object Runner {

  /**
   * A factory method to instantiate Runner.
   */
  def apply[A](stmt: SQLSyntax)(mapper: ResultSetTraversable => A): Runner[A] = new Runner[A] {

    def statement: SQLSyntax = stmt

    protected def mapResultSet(rsTraversable: ResultSetTraversable): A = mapper(rsTraversable)
  }
}
