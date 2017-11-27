package scalikejdbc

import java.time.ZoneId

import com.google.cloud.bigquery.{QueryJobConfiguration, QueryParameterValue}
import scalikejdbc.bigquery.{BqParameter, BqPreparedStatement, Format}

import scala.collection.JavaConverters._

object QueryRequestBuilder {

  /**
   * Instantiate QueryRequestBuilder that SQL statement and parameters are set.
   */
  def apply(statement: SQLSyntax): QueryJobConfiguration.Builder = {

    val builder = QueryJobConfiguration.newBuilder(statement.value)
    val ps = new BqPreparedStatement

    // almost same implementation as scalikejdbc.StatementExecutor
    statement.rawParameters.zipWithIndex.foreach { case (param, index) =>
      param match {
        case binder: ParameterBinder =>
          binder(ps, index)
        case p: BigDecimal => ps.setBigDecimal(index, p.bigDecimal)
        case p: BigInt => ps.setBigDecimal(index, new java.math.BigDecimal(p.bigInteger))
        case p: Boolean => ps.setBoolean(index, p)
        case p: Byte => ps.setByte(index, p)
        case p: java.sql.Date => ps.setDate(index, p)
        case p: Double => ps.setDouble(index, p)
        case p: Float => ps.setFloat(index, p)
        case p: Int => ps.setInt(index, p)
        case p: Long => ps.setLong(index, p)
        case p: Short => ps.setShort(index, p)
        case p: String => ps.setString(index, p)
        case p: java.sql.Time => ps.setTime(index, p)
        case p: java.sql.Timestamp => ps.setTimestamp(index, p)
        case p: java.util.Date => ps.setTimestamp(index, p.toSqlTimestamp)
        case p: org.joda.time.DateTime => ps.setTimestamp(index, p.toDate.toSqlTimestamp)
        case p: org.joda.time.LocalDateTime => ps.setTimestamp(index, p.toDate.toSqlTimestamp)
        case p: org.joda.time.LocalDate => ps.setDate(index, p.toDate.toSqlDate)
        case p: org.joda.time.LocalTime => ps.setTime(index, p.toSqlTime)
        case p: java.time.ZonedDateTime => ps.setTimestamp(index, java.sql.Timestamp.from(p.toInstant))
        case p: java.time.OffsetDateTime => ps.setTimestamp(index, java.sql.Timestamp.from(p.toInstant))
        case p: java.time.Instant => ps.setTimestamp(index, java.sql.Timestamp.from(p))
        case p: java.time.LocalDateTime =>
          ps.setTimestamp(index, org.joda.time.LocalDateTime.parse(p.toString).toDate.toSqlTimestamp)
        case p: java.time.LocalDate =>
          ps.setDate(index, org.joda.time.LocalDate.parse(p.toString).toDate.toSqlDate)
        case p: java.time.LocalTime =>
          ps.setTime(index, org.joda.time.LocalTime.parse(p.toString).toSqlTime)
        case _ =>
          throw new UnsupportedOperationException(
            s"unsupported parameter type. index: ${index}, parameter : ${param}, class: ${param.getClass}")
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
            QueryParameterValue.timestamp(value.withZoneSameInstant(ZoneId.of("UTC")).format(Format.timestamp))
        }
      }.asJava

    builder.setPositionalParameters(parameters)
  }
}
