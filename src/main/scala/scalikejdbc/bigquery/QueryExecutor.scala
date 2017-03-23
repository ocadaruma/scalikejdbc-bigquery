package scalikejdbc.bigquery

import com.google.cloud.bigquery.BigQuery
import scalikejdbc._

case class WrappedQueryResponse(
  rsTraversable: Traversable[WrappedResultSet])

class QueryExecutor(bigQuery: BigQuery, config: QueryConfig) {

  def execute(statement: SQLSyntax): WrappedQueryResponse = {

    val builder = QueryRequestBuilder(statement)
      .setMaxWaitTime(0L) // infinite timeout
      .setUseLegacySql(config.useLegacySql)
      .setUseQueryCache(config.useQueryCache)

    val request = builder.build()

    val response = bigQuery.query(request)

    val rs = new BqResultSet(response.getResult)

    WrappedQueryResponse(new ResultSetTraversable(rs))
  }
}
