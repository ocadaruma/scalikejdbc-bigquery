package scalikejdbc.bigquery

import java.sql.{Timestamp, Date}
import java.time.{ZoneId, ZonedDateTime, LocalDate}
import java.util.{TimeZone, Calendar}

import org.scalatest.FlatSpec

class BqPrepareStatementTest extends FlatSpec {

  it should "holds parameter and indices correctly" in {

    val statement = new BqPreparedStatement

    statement.setString(1, "one")
    statement.setBoolean(2, true)

    assert(statement.parameters.size == 2)
    assert(statement.parameters(1) == BqParameter.String("one"))
    assert(statement.parameters(2) == BqParameter.Bool(true))
  }

  it should "holds LocalDate correctly" in {

    val statement = new BqPreparedStatement

    val date = LocalDate.of(2017, 3, 21)
    statement.setDate(1, Date.valueOf(date))

    assert(statement.parameters(1) == BqParameter.Date(date))
  }

  it should "holds ZonedDateTime correctly" in {

    val statement = new BqPreparedStatement

    val time = ZonedDateTime.of(2017, 3, 21, 10, 0, 0, 0, ZoneId.of("Asia/Tokyo"))
    statement.setTimestamp(1, Timestamp.from(time.toInstant), Calendar.getInstance(TimeZone.getTimeZone(time.getZone)))

    assert(statement.parameters(1) == BqParameter.Timestamp(time))
  }
}
