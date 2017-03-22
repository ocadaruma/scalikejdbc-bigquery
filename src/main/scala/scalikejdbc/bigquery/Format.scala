package scalikejdbc.bigquery

import java.time.format.DateTimeFormatter

object Format {

  val date = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  val dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")

  val isoDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

  val time = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS")

  val timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSZZ")
}
