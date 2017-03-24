package scalikejdbc

import com.google.cloud.bigquery.DatasetId

object BqExtension {

  implicit class BqSQLSyntaxSupport[A](self: SQLSyntaxSupport[A]) {

    class StandardTableAsAliasSQLSyntaxBuilder(datasetId: DatasetId) {
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

    def in(datasetId: DatasetId): StandardTableAsAliasSQLSyntaxBuilder = {
      new StandardTableAsAliasSQLSyntaxBuilder(datasetId)
    }
  }
}
