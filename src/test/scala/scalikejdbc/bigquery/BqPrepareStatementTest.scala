package scalikejdbc.bigquery

import java.sql.{Date, Timestamp}
import java.time.{LocalDate, ZoneId, ZonedDateTime}
import java.util.Calendar

import org.scalatest.flatspec.AnyFlatSpec

class BqPrepareStatementTest extends AnyFlatSpec {

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

    val time = ZonedDateTime.of(2017, 3, 21, 10, 0, 0, 0, ZoneId.systemDefault())
    statement.setTimestamp(1, Timestamp.from(time.toInstant))
    statement.setTimestamp(2, Timestamp.from(time.toInstant), Calendar.getInstance())

    assert(statement.parameters(1) == BqParameter.Timestamp(time))
    assert(statement.parameters(2) == BqParameter.Timestamp(time))
  }
}
