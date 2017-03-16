package scalikejdbc.bigquery

import scalikejdbc.WrappedResultSet

class OneToManyExtractor[TOne, TMany, TResult](
  f: WrappedResultSet => TOne,
  g: WrappedResultSet => Option[TMany],
  h: (TOne, Seq[TMany]) => TResult
) {
  def list
}

object OneToManyExtractor {
  def one[A](f: WrappedResultSet => A): One[A] = ???

  class One[A](f: WrappedResultSet => A) {
    def toMany[B](g: WrappedResultSet => Option[B]): ToMany[B] = ???

    class ToMany[B](g: WrappedResultSet => Option[B]) {
      def map[C](h: (A, Seq[B]) => C): OneToManyExtractor[A, B, C] = ???
    }
  }

  OneToManyExtractor.one(_.long(1)).toMany(_.stringOpt(1)).map { (s, t) =>
    t
  }
}
