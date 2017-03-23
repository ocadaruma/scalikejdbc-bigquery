package scalikejdbc

import com.google.cloud.bigquery.{QueryParameterValue, QueryRequest}
import scalikejdbc.bigquery.{Format, BqParameter, BqPreparedStatement}

import scala.collection.JavaConverters._

object QueryRequestBuilder {

  /**
   * Instantiate QueryRequestBuilder that SQL statement and parameters are set.
   */
  def apply(statement: SQLSyntax): QueryRequest.Builder = {

    val builder = QueryRequest.newBuilder(statement.value)
    val ps = new BqPreparedStatement

    statement.rawParameters.zipWithIndex.foreach { case (param, index) =>
      param match {
        case binder: ParameterBinder =>
          binder(ps, index)
        case _ =>
          throw new UnsupportedOperationException(
            s"directly embedded parameters are not supported. use scalikejdbc.ParameterBinderFactory. index: ${index}, parameter : ${param}")
      }
    }

    val parameters = ps.parameters.toList
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
            QueryParameterValue.date(value.format(Format.date))
          case BqParameter.DateTime(value) =>
            QueryParameterValue.dateTime(value.format(Format.dateTime))
          case BqParameter.Time(value) =>
            QueryParameterValue.time(value.format(Format.time))
          case BqParameter.Timestamp(value) =>
            QueryParameterValue.timestamp(value.format(Format.timestamp))
        }
      }.asJava

    builder.setPositionalParameters(parameters)
  }
}
