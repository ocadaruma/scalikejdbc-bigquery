package com.google.cloud.bigquery

import java.time.format.DateTimeFormatter
import java.util.Base64

import com.google.cloud.bigquery.FieldValue.Attribute
import scalikejdbc.bigquery.BqParameter

import scala.collection.JavaConverters._

object MockUtil {

  def fieldValueFromParameter(parameter: BqParameter, attribute: Attribute = Attribute.PRIMITIVE): FieldValue = {
    val underlying: AnyRef = parameter match {
      case BqParameter.Int64(value) =>
        value.toString
      case BqParameter.Float64(value) =>
        value.toString
      case BqParameter.Bool(value) =>
        value.toString
      case BqParameter.String(value) =>
        value
      case BqParameter.Bytes(value) =>
        // Bytes values are Base64 encoded.
        Base64.getEncoder.encodeToString(value)
      case BqParameter.Date(value) =>
        value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
      case BqParameter.DateTime(value) =>
        value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))
      case BqParameter.Time(value: java.time.LocalTime) =>
        value.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS"))
      case BqParameter.Timestamp(value) =>
        val secPart = value.toEpochSecond
        val fractionPart = value.getNano.toDouble / 1000000000L

        (secPart.toDouble + fractionPart).toString
      // TODO: case Array(value) =>
      // TODO: case Struct() =>
    }

    new FieldValue(attribute, underlying)
  }

  def queryResultFromSeq(source: Seq[Seq[FieldValue]], schema: Schema): QueryResult = {
    val builder = QueryResult.newBuilder()

    builder.setSchema(schema)
    builder.setResults(source.map(_.asJava).asJava)

    builder.build()
  }
}
