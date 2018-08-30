package scalikejdbc.bigquery

import com.google.cloud.bigquery.TableResult

/**
 * Represents a response from BigQuery.
 */
case class Response[A](result: A, underlying: TableResult)
