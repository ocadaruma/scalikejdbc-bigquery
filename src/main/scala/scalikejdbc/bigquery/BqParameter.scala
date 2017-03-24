package scalikejdbc.bigquery

/**
 * Represents a parameter to be bound to SQL statement.
 */
sealed abstract class BqParameter

object BqParameter {

  case class Int64(value: Long) extends BqParameter

  case class Float64(value: Double) extends BqParameter

  case class Bool(value: Boolean) extends BqParameter

  case class String(value: java.lang.String) extends BqParameter

  case class Bytes(value: scala.Array[Byte]) extends BqParameter

  case class Date(value: java.time.LocalDate) extends BqParameter

  case class DateTime(value: java.time.LocalDateTime) extends BqParameter

  case class Time(value: java.time.LocalTime) extends BqParameter

  case class Timestamp(value: java.time.ZonedDateTime) extends BqParameter

  // TODO: case class Array[A <: BqParameter](value: scala.Array[A]) extends BqParameter

  // TODO: case class Struct() extends BqParameter
}
