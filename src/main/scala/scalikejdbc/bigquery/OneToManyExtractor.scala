package scalikejdbc.bigquery

import scalikejdbc._

import scala.collection.mutable

class OneToManyExtractor[TOne, TMany, TResult](
  statement: SQLSyntax,
  f: WrappedResultSet => TOne,
  g: WrappedResultSet => Option[TMany],
  h: (TOne, Seq[TMany]) => TResult
) {

  def list: Runner[Seq[TResult]] = Runner(statement)(mapOneToMany)

  def single: Runner[Option[TResult]] = Runner(statement)(mapOneToMany(_).headOption)

  private[this] def mapOneToMany(rsTraversable: ResultSetTraversable): Seq[TResult] = {
    val buffer = mutable.LinkedHashMap.empty[TOne, Seq[TMany]]

    rsTraversable.foreach { rs =>
      val one = f(rs)
      g(rs).foreach { many =>
        val current = buffer.getOrElse(one, Vector.empty)
        buffer(one) = current :+ many
      }
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
