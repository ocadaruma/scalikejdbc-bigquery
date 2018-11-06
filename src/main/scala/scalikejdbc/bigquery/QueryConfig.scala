package scalikejdbc.bigquery

/**
 * These configurations are used to build QueryRequest.
 * @param useLegacySql If you use legacy SQL, set true.
 * @param useQueryCache If you want to disable BigQuery's query cache, set false.
 * @param pageSize the maximum number of rows returned per page.
 */
case class QueryConfig(
  useLegacySql: Boolean = false,
  useQueryCache: Boolean = true,
  pageSize: Option[Long] = None)
