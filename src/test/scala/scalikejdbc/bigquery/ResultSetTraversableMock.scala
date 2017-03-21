package scalikejdbc.bigquery

import scalikejdbc._

class ResultSetTraversableMock(underlying: Seq[WrappedResultSet]) extends ResultSetTraversable(null) {
  override def foreach[U](f: (WrappedResultSet) => U): Unit = underlying.foreach(f)
}
