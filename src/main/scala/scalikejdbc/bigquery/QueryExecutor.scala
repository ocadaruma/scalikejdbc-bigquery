package scalikejdbc.bigquery

import com.google.cloud.bigquery.{QueryResponse, BigQuery}
import scalikejdbc._

import scala.concurrent.duration._

class WrappedQueryResponse(
  private[bigquery] val underlying: QueryResponse,
  private[bigquery] val rsTraversable: Traversable[WrappedResultSet])

class QueryExecutor(bigQuery: BigQuery, config: QueryConfig) {

  def execute(statement: SQLSyntax): WrappedQueryResponse = {

    val builder = QueryRequestBuilder(statement)
      .setMaxWaitTime(30.minutes.toMillis) // TODO: make configurable
      .setUseLegacySql(config.useLegacySql)
      .setUseQueryCache(config.useQueryCache)

    val request = builder.build()

    val response = bigQuery.query(request)

    val rs = new BqResultSet(response.getResult)

    new WrappedQueryResponse(response, new ResultSetTraversable(rs))
  }
}
