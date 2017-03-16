package scalikejdbc.bigquery

import java.time.format.DateTimeFormatter

import com.google.cloud.bigquery.{BigQueryOptions, QueryParameterValue, QueryRequest}
import scalikejdbc._

import scala.collection.JavaConverters._

class QueryExecutor {
  def execute(statement: BqPreparedStatement, syntax: SQLSyntax): Unit = {

    val builder = QueryRequest.newBuilder(syntax.value)

    val parameters = statement.parameters.toList
      .sortBy { case (parameterIndex, _) => parameterIndex }
      .map { case (_, parameter) =>
        parameter match {
          case BqParameter.Int64(value) =>
            QueryParameterValue.int64(value)
          case BqParameter.Float64(value) =>
            QueryParameterValue.float64(value)
          case BqParameter.Bool(value) =>
            QueryParameterValue.bool(value)
          case BqParameter.String(value) =>
            QueryParameterValue.string(value)
          case BqParameter.Bytes(value) =>
            QueryParameterValue.bytes(value)
          case BqParameter.Date(value) =>
            QueryParameterValue.date(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
          case BqParameter.DateTime(value) =>
            QueryParameterValue.dateTime(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")))
          case BqParameter.Time(value) =>
            QueryParameterValue.time(value.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS")))
          case BqParameter.Timestamp(value) =>
            QueryParameterValue.time(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSZZ")))
        }
      }.asJava

    builder.setPositionalParameters(parameters)
    val request = builder.build()

    val bigquery = BigQueryOptions.getDefaultInstance.getService
    val response = bigquery.query(request)
    response.getResult.getSchema.getFields.get(0).getType
    response.getResult.iterateAll().asScala.map { list =>
      list.asScala.toList(0)
    }
  }
}
