package scalikejdbc

import com.google.cloud.bigquery.DatasetId

package object bigquery {

//  implicit class BqInterpolation(val sc: StringContext) extends AnyVal {
//    def bq(parameters: Any*): BqSQL = {
//      val statement = new SQLInterpolationString(sc).sqls(parameters)
//      new BqSQL(statement)
//    }
//  }

  implicit class BqSQLSyntaxSupport[A](private val self: SQLSyntaxSupport[A]) extends AnyVal {
    def in(datasetId: DatasetId): StandardTableAsAliasSQLSyntaxBuilder[A] = {
      new StandardTableAsAliasSQLSyntaxBuilder[A](datasetId, self)
    }
  }

  class StandardTableAsAliasSQLSyntaxBuilder[A](datasetId: DatasetId, self: SQLSyntaxSupport[A]) {
    def as(provider: SyntaxProvider[A]): TableAsAliasSQLSyntax = {
      val qualifiedTableName = s"`${datasetId.getProject}.${datasetId.getDataset}.${self.tableName}`"

      // same implementation as SQLSyntaxSupport#as
      if (self.tableName == provider.tableAliasName) {
        TableAsAliasSQLSyntax(qualifiedTableName, self.table.rawParameters, Some(provider))
      } else {
        TableAsAliasSQLSyntax(qualifiedTableName + " " + provider.tableAliasName, Nil, Some(provider))
      }
    }
  }

  /**
   * Corresponds to QueryDSLFeature#withSQL
   */
  def bq(builder: SQLBuilder[_]): BqSQL = new BqSQL(builder.sql)
}
