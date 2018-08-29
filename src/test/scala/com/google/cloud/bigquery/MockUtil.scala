package com.google.cloud.bigquery

import java.util.Base64

import com.google.api.gax.paging.Page
import com.google.cloud.PageImpl
import com.google.cloud.PageImpl.NextPageFetcher
import com.google.cloud.bigquery.FieldValue.Attribute
import scalikejdbc.bigquery.{BqParameter, Format}

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
        value.format(Format.date)
      case BqParameter.DateTime(value) =>
        value.format(Format.isoDateTime)
      case BqParameter.Time(value: java.time.LocalTime) =>
        value.format(Format.time)
      case BqParameter.Timestamp(value) =>
        val secPart = value.toEpochSecond
        val fractionPart = value.getNano.toDouble / 1000000000L

        (secPart.toDouble + fractionPart).toString
      // TODO: case Array(value) =>
      // TODO: case Struct() =>
    }

    FieldValue.of(attribute, underlying)
  }

  def tableResultFromSeq(source: Seq[Seq[FieldValue]], schema: Schema): TableResult = {
    val page = new PageImpl(
      new NextPageFetcher[FieldValueList] {
        override def getNextPage: Page[FieldValueList] = null
      },
      "cursor",
      source.map(seq => FieldValueList.of(seq.asJava)).asJava)

    new TableResult(schema, source.size, page)
  }
}
