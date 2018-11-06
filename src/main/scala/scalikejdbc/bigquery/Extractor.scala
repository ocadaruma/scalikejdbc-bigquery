package scalikejdbc.bigquery

import scalikejdbc._

class Extractor[A](statement: SQLSyntax, f: WrappedResultSet => A) {

  def iterator: Runner[Iterator[A]] = Runner(statement)(_.map(f))

  def list: Runner[Seq[A]] = Runner(statement)(_.map(f).toList)

  def single: Runner[Option[A]] = Runner(statement) { rs =>
    val rows = (rs map f).toList
    rows match {
      case Nil => None
      case one :: Nil => Option(one)
      case _ => throw TooManyRowsException(1, rows.size)
    }
  }

  def first: Runner[Option[A]] = Runner(statement)(_.map(f).toStream.headOption)
}

trait ExtractorBuilder {

  def statement: SQLSyntax

  def map[A](f: WrappedResultSet => A): Extractor[A] = new Extractor[A](statement, f)
}
