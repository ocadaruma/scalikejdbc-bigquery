package scalikejdbc.bigquery

import com.google.cloud.bigquery.QueryResponse

/**
 * Represents a response from BigQuery.
 */
case class Response[A](result: A, underlying: QueryResponse)
