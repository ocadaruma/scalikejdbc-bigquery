package scalikejdbc

import com.google.cloud.bigquery.DatasetId

object BqExtension {

  def standardTableReference(
    datasetId: DatasetId,
    tableName: String,
    tableAliasName: Option[String] = None): TableAsAliasSQLSyntax = {

    val tableReference = s"`${datasetId.getProject}.${datasetId.getDataset}.${tableName}`"

    val withAlias = tableAliasName.fold(tableReference) { alias =>
      s"${tableReference} ${alias}"
    }

    TableAsAliasSQLSyntax(withAlias)
  }
}
