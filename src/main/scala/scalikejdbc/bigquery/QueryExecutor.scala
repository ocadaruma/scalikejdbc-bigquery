package scalikejdbc.bigquery

import com.google.cloud.bigquery.BigQuery.QueryResultsOption
import com.google.cloud.bigquery.{BigQuery, JobInfo, TableResult}
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

    val queryResultsOptions = Seq(
      config.pageSize.map(QueryResultsOption.pageSize)
    ).flatten

    val job = bigQuery.create(JobInfo.of(request))
    val response = job.getQueryResults(queryResultsOptions: _*)
    val rs = new BqResultSet(response)

    new WrappedQueryResponse(response, new ResultSetIterator(rs))
  }
}
