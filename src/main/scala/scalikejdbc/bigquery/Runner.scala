package scalikejdbc.bigquery

import scalikejdbc._

trait Runner[A] {

  /**
   * Transform ResultSet to desired type.
   */
  protected def mapResultSet(rsIterator: Iterator[WrappedResultSet]): A

  /**
   * A SQL statement to be executed.
   */
  def statement: SQLSyntax

  def run(queryExecutor: QueryExecutor): Response[A] = {
    val response = queryExecutor.execute(statement)
    val result = mapResultSet(response.rsIterator)
    Response(result, response.underlying)
  }
}

object Runner {

  /**
   * A factory method to instantiate Runner.
   */
  def apply[A](stmt: SQLSyntax)(mapper: Iterator[WrappedResultSet] => A): Runner[A] = new Runner[A] {

    def statement: SQLSyntax = stmt

    protected def mapResultSet(rsIterator: Iterator[WrappedResultSet]): A = mapper(rsIterator)
  }
}
