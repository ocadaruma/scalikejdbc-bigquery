package scalikejdbc

package object bigquery {

  implicit class BqInterpolation(val sc: StringContext) extends AnyVal {
    def bq(parameters: Any*): BqSQL = {
      val statement = new SQLInterpolationString(sc).sqls(parameters)
      new BqSQL(statement)
    }
  }

  def bq(builder: SQLBuilder[_]): BqSQL = new BqSQL(builder.sql)
}
