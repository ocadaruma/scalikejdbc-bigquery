package scalikejdbc.bigquery

/**
 * Represents a response from BigQuery.
 */
case class Response[A](result: A)
