package scalikejdbc.bigquery

/**
 * These configurations are used to build QueryRequest.
 * @param useLegacySql If you use legacy SQL, set true.
 * @param useQueryCache If you want to disable BigQuery's query cache, set false.
 */
case class QueryConfig(
  useLegacySql: Boolean = false,
  useQueryCache: Boolean = true)
