package scalikejdbc.bigquery

import com.google.cloud.bigquery.{BigQuery, TableResult}
import scalikejdbc._

class WrappedQueryResponse(
  private[bigquery] val underlying: TableResult,
  private[bigquery] val rsIterator: Iterator[WrappedResultSet])

class QueryExecutor(bigQuery: BigQuery, config: QueryConfig) {

  def execute(statement: SQLSyntax): WrappedQueryResponse = {

    val request = QueryRequestBuilder(statement)
      .setUseLegacySql(config.useLegacySql)
      .setUseQueryCache(config.useQueryCache)
      .build()

    val response = bigQuery.query(request)
    val rs = new BqResultSet(response)

    new WrappedQueryResponse(response, new ResultSetIterator(rs))
  }
}
