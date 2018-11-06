package scalikejdbc.bigquery

import scalikejdbc._

import scala.collection.immutable.Stream.Empty
import scala.collection.mutable

class OneToManyExtractor[TOne, TMany, TResult](
  statement: SQLSyntax,
  f: WrappedResultSet => TOne,
  g: WrappedResultSet => Option[TMany],
  h: (TOne, Seq[TMany]) => TResult
) {

  def list: Runner[Seq[TResult]] = Runner(statement)(mapOneToMany(_).toList)

  def single: Runner[Option[TResult]] = Runner(statement){ rs =>
    val rows = mapOneToMany(rs).toStream
    rows match {
      case Empty => None
      case one #:: Empty => Option(one)
      case _ => throw TooManyRowsException(1, rows.size)
    }
  }

  def first: Runner[Option[TResult]] = Runner(statement)(mapOneToMany(_).headOption)

  private[this] def mapOneToMany(rsIterator: Iterator[WrappedResultSet]): Seq[TResult] = {
    val buffer = mutable.LinkedHashMap.empty[TOne, Seq[TMany]]

    rsIterator.foreach { rs =>
      val one = f(rs)
      val current = buffer.getOrElse(one, Vector.empty)

      buffer(one) = current ++ g(rs).toSeq
    }

    buffer.map { case (one, many) =>
      h(one, many)
    }(collection.breakOut)
  }
}

trait OneToManyExtractorBuilder {

  def statement: SQLSyntax

  def one[TOne](f: WrappedResultSet => TOne): One[TOne] = new One(f)

  class One[TOne](f: WrappedResultSet => TOne) {
    def toMany[TMany](g: WrappedResultSet => Option[TMany]): ToMany[TMany] = new ToMany(g)

    class ToMany[TMany](g: WrappedResultSet => Option[TMany]) {
      def map[TResult](h: (TOne, Seq[TMany]) => TResult): OneToManyExtractor[TOne, TMany, TResult] =
        new OneToManyExtractor(statement, f, g, h)
    }
  }
}
