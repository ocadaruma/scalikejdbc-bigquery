package scalikejdbc.bigquery

import java.time.{LocalDate, LocalTime, ZoneId, ZonedDateTime}

import com.google.cloud.bigquery._
import org.scalatest.flatspec.AnyFlatSpec

class BqResultSetTest extends AnyFlatSpec {

  it should "be able to instantiate from null Schema" in {
    val tableResult = MockUtil.tableResultFromSeq(Nil, null)
    new BqResultSet(tableResult)
  }

  it should "correctly traversable" in {

    val row1 = Seq(BqParameter.String("first")).map(MockUtil.fieldValueFromParameter(_))
    val row2 = Seq(BqParameter.String("second")).map(MockUtil.fieldValueFromParameter(_))
    val row3 = Seq(BqParameter.String("third")).map(MockUtil.fieldValueFromParameter(_))

    val schema = Schema.of(Field.of("name", LegacySQLTypeName.STRING));
    val queryResult = MockUtil.tableResultFromSeq(Seq(row1, row2, row3), schema)

    val resultSet = new BqResultSet(queryResult)

    assert(resultSet.next())
    assert(resultSet.next())
    assert(resultSet.next())
    assert(!resultSet.next())
  }

  it should "correctly get value" in {

    val row = Seq(
      BqParameter.Int64(42L),
      BqParameter.Float64(3.14159),
      BqParameter.Bool(true),
      BqParameter.String("hello"),
      BqParameter.Bytes(Array[Byte](104, 101, 108, 108, 111)),
      BqParameter.Date(LocalDate.of(2017, 3, 22)),
//      BqParameter.DateTime
      BqParameter.Time(LocalTime.of(19, 58, 0, 0)),
      BqParameter.Timestamp(ZonedDateTime.of(2017, 3, 22, 19, 58, 0, 0, ZoneId.of("Asia/Tokyo")))
    ).map(MockUtil.fieldValueFromParameter(_))

    val fields = Seq(
      Field.of("int64_column", LegacySQLTypeName.INTEGER),
      Field.of("float64_column", LegacySQLTypeName.FLOAT),
      Field.of("bool_column", LegacySQLTypeName.BOOLEAN),
      Field.of("string_column", LegacySQLTypeName.STRING),
      Field.of("bytes_column", LegacySQLTypeName.BYTES),
      Field.of("date_column", LegacySQLTypeName.STRING),
      Field.of("time_column", LegacySQLTypeName.STRING),
      Field.of("timestamp_column", LegacySQLTypeName.TIMESTAMP)
    )

    val schema = Schema.of(fields: _*)

    val queryResult = MockUtil.tableResultFromSeq(Seq(row), schema)

    val resultSet = new BqResultSet(queryResult)

    assert(resultSet.next())

    // int64
    assert(resultSet.getInt(0) == 42)
    assert(resultSet.getInt("int64_column") == 42)

    // float64
    assert(resultSet.getDouble(1) == 3.14159)
    assert(resultSet.getDouble("float64_column") == 3.14159)

    // bool
    assert(resultSet.getBoolean(2) == true)
    assert(resultSet.getBoolean("bool_column") == true)

    // string
    assert(resultSet.getString(3) == "hello")
    assert(resultSet.getString("string_column") == "hello")

    // bytes
    assert(resultSet.getBytes(4).sameElements(Array[Byte](104, 101, 108, 108, 111)))
    assert(resultSet.getBytes("bytes_column").sameElements(Array[Byte](104, 101, 108, 108, 111)))

    // date
    assert(resultSet.getDate(5).toLocalDate == LocalDate.of(2017, 3, 22))
    assert(resultSet.getDate("date_column").toLocalDate == LocalDate.of(2017, 3, 22))

    // time
    assert(resultSet.getTime(6).toLocalTime == LocalTime.of(19, 58, 0, 0))
    assert(resultSet.getTime("time_column").toLocalTime == LocalTime.of(19, 58, 0, 0))

    // timestamp
    assert(resultSet.getTimestamp(7).toInstant == ZonedDateTime.of(2017, 3, 22, 19, 58, 0, 0, ZoneId.of("Asia/Tokyo")).toInstant)
    assert(resultSet.getTimestamp("timestamp_column").toInstant == ZonedDateTime.of(2017, 3, 22, 19, 58, 0, 0, ZoneId.of("Asia/Tokyo")).toInstant)
  }
}
