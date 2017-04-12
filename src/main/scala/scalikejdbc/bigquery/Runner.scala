package scalikejdbc.bigquery

import scalikejdbc._

trait Runner[A] {

  /**
   * Transform ResultSet to desired type.
   */
  protected def mapResultSet(rsTraversable: Traversable[WrappedResultSet]): A

  /**
   * A SQL statement to be executed.
   */
  def statement: SQLSyntax

  def run(queryExecutor: QueryExecutor): Response[A] = {
    val response = queryExecutor.execute(statement)
    val result = mapResultSet(response.rsTraversable)
    Response(result, response.underlying)
  }
}

object Runner {

  /**
   * A factory method to instantiate Runner.
   */
  def apply[A](stmt: SQLSyntax)(mapper: Traversable[WrappedResultSet] => A): Runner[A] = new Runner[A] {

    def statement: SQLSyntax = stmt

    protected def mapResultSet(rsTraversable: Traversable[WrappedResultSet]): A = mapper(rsTraversable)
  }
}
