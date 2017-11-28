package scalikejdbc.bigquery

import com.google.cloud.bigquery.BigQuery.{QueryOption, QueryResultsOption}
import com.google.cloud.bigquery.{BigQuery, QueryResponse}
import scalikejdbc._

import scala.concurrent.duration._

class WrappedQueryResponse(
  private[bigquery] val underlying: QueryResponse,
  private[bigquery] val rsTraversable: Traversable[WrappedResultSet])

class QueryExecutor(bigQuery: BigQuery, config: QueryConfig) {

  def execute(statement: SQLSyntax): WrappedQueryResponse = {

    val builder = QueryRequestBuilder(statement)
      .setUseLegacySql(config.useLegacySql)
      .setUseQueryCache(config.useQueryCache)

    val queryOptions = Seq(
      QueryOption.of(QueryResultsOption.maxWaitTime(30.minutes.toMillis)) // TODO: make configurable
    )

    val request = builder.build()

    val response = bigQuery.query(request, queryOptions: _*)

    val rs = new BqResultSet(response.getResult)

    new WrappedQueryResponse(response, new ResultSetTraversable(rs))
  }
}
