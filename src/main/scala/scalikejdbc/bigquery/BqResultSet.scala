package scalikejdbc.bigquery

import java.io.{Reader, InputStream}
import java.math.BigDecimal
import java.net.URL
import java.sql.{Array => SqlArray, _}
import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalTime, Instant}
import java.util
import java.util.Calendar

import com.google.cloud.bigquery.{FieldValue, QueryResult}

import scala.collection.JavaConverters._

class BqResultSet(underlying: QueryResult) extends ResultSet {

  private[this] val resultIterator: Iterator[Seq[FieldValue]] = underlying.iterateAll().asScala.map(_.asScala)
  private[this] val columnNameIndexMap: Map[String, Int] = underlying.getSchema.getFields.asScala.zipWithIndex
    .map { case (field, index) => (field.getName, index) }.toMap

  private[this] var current: Seq[FieldValue] = null

  private[this] def sqlTimestampFromEpochMicro(epochMicro: Long): Timestamp = {
    val micro = 1000000L
    val sec = epochMicro / micro
    val microAdjustment = epochMicro % micro

    Timestamp.from(Instant.ofEpochSecond(sec, microAdjustment * 1000L))
  }

  private[this] def sqlTimeFromStringValue(time: String): Time = {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS")
    Time.valueOf(LocalTime.parse(time, formatter))
  }

  private[this] def sqlDateFromStringValue(date: String): Date = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    Date.valueOf(LocalDate.parse(date, formatter))
  }

  def getType: Int =
    throw new UnsupportedOperationException("getType is not supported")

  def isBeforeFirst: Boolean =
    throw new UnsupportedOperationException("isBeforeFirst is not supported")

  def next(): Boolean = if (resultIterator.hasNext) {
    current = resultIterator.next(); true
  } else {
    false
  }

  def updateString(columnIndex: Int, x: String): Unit =
    throw new UnsupportedOperationException("updateString is not supported")

  def updateString(columnLabel: String, x: String): Unit =
    throw new UnsupportedOperationException("updateString is not supported")

  def getTimestamp(columnIndex: Int): Timestamp =
    sqlTimestampFromEpochMicro(current(columnIndex).getTimestampValue)

  def getTimestamp(columnLabel: String): Timestamp =
    sqlTimestampFromEpochMicro(current(columnNameIndexMap(columnLabel)).getTimestampValue)

  // TODO: use calendar
  def getTimestamp(columnIndex: Int, cal: Calendar): Timestamp =
    sqlTimestampFromEpochMicro(current(columnIndex).getTimestampValue)

  // TODO: use calendar
  def getTimestamp(columnLabel: String, cal: Calendar): Timestamp =
    sqlTimestampFromEpochMicro(current(columnNameIndexMap(columnLabel)).getTimestampValue)

  def updateNString(columnIndex: Int, nString: String): Unit =
    throw new UnsupportedOperationException("updateNString is not supported")

  def updateNString(columnLabel: String, nString: String): Unit =
    throw new UnsupportedOperationException("updateNString is not supported")

  def clearWarnings(): Unit =
    throw new UnsupportedOperationException("clearWarnings is not supported")

  def updateTimestamp(columnIndex: Int, x: Timestamp): Unit =
    throw new UnsupportedOperationException("updateTimestamp is not supported")

  def updateTimestamp(columnLabel: String, x: Timestamp): Unit =
    throw new UnsupportedOperationException("updateTimestamp is not supported")

  def updateByte(columnIndex: Int, x: Byte): Unit =
    throw new UnsupportedOperationException("updateByte is not supported")

  def updateByte(columnLabel: String, x: Byte): Unit =
    throw new UnsupportedOperationException("updateByte is not supported")

  def updateBigDecimal(columnIndex: Int, x: BigDecimal): Unit =
    throw new UnsupportedOperationException("updateBigDecimal is not supported")

  def updateBigDecimal(columnLabel: String, x: BigDecimal): Unit =
    throw new UnsupportedOperationException("updateBigDecimal is not supported")

  def updateDouble(columnIndex: Int, x: Double): Unit =
    throw new UnsupportedOperationException("updateDouble is not supported")

  def updateDouble(columnLabel: String, x: Double): Unit =
    throw new UnsupportedOperationException("updateDouble is not supported")

  def updateDate(columnIndex: Int, x: Date): Unit =
    throw new UnsupportedOperationException("updateDate is not supported")

  def updateDate(columnLabel: String, x: Date): Unit =
    throw new UnsupportedOperationException("updateDate is not supported")

  def isAfterLast: Boolean =
    throw new UnsupportedOperationException("isAfterLast is not supported")

  def updateBoolean(columnIndex: Int, x: Boolean): Unit =
    throw new UnsupportedOperationException("updateBoolean is not supported")

  def updateBoolean(columnLabel: String, x: Boolean): Unit =
    throw new UnsupportedOperationException("updateBoolean is not supported")

  def getBinaryStream(columnIndex: Int): InputStream =
    throw new UnsupportedOperationException("getBinaryStream is not supported")

  def getBinaryStream(columnLabel: String): InputStream =
    throw new UnsupportedOperationException("getBinaryStream is not supported")

  def beforeFirst(): Unit =
    throw new UnsupportedOperationException("beforeFirst is not supported")

  def updateNCharacterStream(columnIndex: Int, x: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("updateNCharacterStream is not supported")

  def updateNCharacterStream(columnLabel: String, reader: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("updateNCharacterStream is not supported")

  def updateNCharacterStream(columnIndex: Int, x: Reader): Unit =
    throw new UnsupportedOperationException("updateNCharacterStream is not supported")

  def updateNCharacterStream(columnLabel: String, reader: Reader): Unit =
    throw new UnsupportedOperationException("updateNCharacterStream is not supported")

  def updateNClob(columnIndex: Int, nClob: NClob): Unit =
    throw new UnsupportedOperationException("updateNClob is not supported")

  def updateNClob(columnLabel: String, nClob: NClob): Unit =
    throw new UnsupportedOperationException("updateNClob is not supported")

  def updateNClob(columnIndex: Int, reader: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("updateNClob is not supported")

  def updateNClob(columnLabel: String, reader: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("updateNClob is not supported")

  def updateNClob(columnIndex: Int, reader: Reader): Unit =
    throw new UnsupportedOperationException("updateNClob is not supported")

  def updateNClob(columnLabel: String, reader: Reader): Unit =
    throw new UnsupportedOperationException("updateNClob is not supported")

  def last(): Boolean =
    throw new UnsupportedOperationException("last is not supported")

  def isLast: Boolean =
    throw new UnsupportedOperationException("isLast is not supported")

  def getNClob(columnIndex: Int): NClob =
    throw new UnsupportedOperationException("getNClob is not supported")

  def getNClob(columnLabel: String): NClob =
    throw new UnsupportedOperationException("getNClob is not supported")

  def getCharacterStream(columnIndex: Int): Reader =
    throw new UnsupportedOperationException("getCharacterStream is not supported")

  def getCharacterStream(columnLabel: String): Reader =
    throw new UnsupportedOperationException("getCharacterStream is not supported")

  def updateArray(columnIndex: Int, x: SqlArray): Unit =
    throw new UnsupportedOperationException("updateArray is not supported")

  def updateArray(columnLabel: String, x: SqlArray): Unit =
    throw new UnsupportedOperationException("updateArray is not supported")

  def updateBlob(columnIndex: Int, x: Blob): Unit =
    throw new UnsupportedOperationException("updateBlob is not supported")

  def updateBlob(columnLabel: String, x: Blob): Unit =
    throw new UnsupportedOperationException("updateBlob is not supported")

  def updateBlob(columnIndex: Int, inputStream: InputStream, length: Long): Unit =
    throw new UnsupportedOperationException("updateBlob is not supported")

  def updateBlob(columnLabel: String, inputStream: InputStream, length: Long): Unit =
    throw new UnsupportedOperationException("updateBlob is not supported")

  def updateBlob(columnIndex: Int, inputStream: InputStream): Unit =
    throw new UnsupportedOperationException("updateBlob is not supported")

  def updateBlob(columnLabel: String, inputStream: InputStream): Unit =
    throw new UnsupportedOperationException("updateBlob is not supported")

  def getDouble(columnIndex: Int): Double =
    current(columnIndex).getDoubleValue

  def getDouble(columnLabel: String): Double =
    current(columnNameIndexMap(columnLabel)).getDoubleValue

  def getArray(columnIndex: Int): SqlArray =
    throw new UnsupportedOperationException("getArray is not supported")

  def getArray(columnLabel: String): SqlArray =
    throw new UnsupportedOperationException("getArray is not supported")

  def isFirst: Boolean =
    throw new UnsupportedOperationException("isFirst is not supported")

  def getURL(columnIndex: Int): URL =
    throw new UnsupportedOperationException("getURL is not supported")

  def getURL(columnLabel: String): URL =
    throw new UnsupportedOperationException("getURL is not supported")

  def updateRow(): Unit =
    throw new UnsupportedOperationException("updateRow is not supported")

  def insertRow(): Unit =
    throw new UnsupportedOperationException("insertRow is not supported")

  def getMetaData: ResultSetMetaData =
    throw new UnsupportedOperationException("getMetaData is not supported")

  def updateBinaryStream(columnIndex: Int, x: InputStream, length: Int): Unit =
    throw new UnsupportedOperationException("updateBinaryStream is not supported")

  def updateBinaryStream(columnLabel: String, x: InputStream, length: Int): Unit =
    throw new UnsupportedOperationException("updateBinaryStream is not supported")

  def updateBinaryStream(columnIndex: Int, x: InputStream, length: Long): Unit =
    throw new UnsupportedOperationException("updateBinaryStream is not supported")

  def updateBinaryStream(columnLabel: String, x: InputStream, length: Long): Unit =
    throw new UnsupportedOperationException("updateBinaryStream is not supported")

  def updateBinaryStream(columnIndex: Int, x: InputStream): Unit =
    throw new UnsupportedOperationException("updateBinaryStream is not supported")

  def updateBinaryStream(columnLabel: String, x: InputStream): Unit =
    throw new UnsupportedOperationException("updateBinaryStream is not supported")

  def absolute(row: Int): Boolean =
    throw new UnsupportedOperationException("absolute is not supported")

  def updateRowId(columnIndex: Int, x: RowId): Unit =
    throw new UnsupportedOperationException("updateRowId is not supported")

  def updateRowId(columnLabel: String, x: RowId): Unit =
    throw new UnsupportedOperationException("updateRowId is not supported")

  def getRowId(columnIndex: Int): RowId =
    throw new UnsupportedOperationException("getRowId is not supported")

  def getRowId(columnLabel: String): RowId =
    throw new UnsupportedOperationException("getRowId is not supported")

  def moveToInsertRow(): Unit =
    throw new UnsupportedOperationException("moveToInsertRow is not supported")

  def rowInserted(): Boolean =
    throw new UnsupportedOperationException("rowInserted is not supported")

  def getFloat(columnIndex: Int): Float =
    current(columnIndex).getDoubleValue.toFloat

  def getFloat(columnLabel: String): Float =
    current(columnNameIndexMap(columnLabel)).getDoubleValue.toFloat

  def getBigDecimal(columnIndex: Int, scale: Int): BigDecimal =
    throw new UnsupportedOperationException("getBigDecimal is not supported")

  def getBigDecimal(columnLabel: String, scale: Int): BigDecimal =
    throw new UnsupportedOperationException("getBigDecimal is not supported")

  def getBigDecimal(columnIndex: Int): BigDecimal =
    throw new UnsupportedOperationException("getBigDecimal is not supported")

  def getBigDecimal(columnLabel: String): BigDecimal =
    throw new UnsupportedOperationException("getBigDecimal is not supported")

  def getClob(columnIndex: Int): Clob =
    throw new UnsupportedOperationException("getClob is not supported")

  def getClob(columnLabel: String): Clob =
    throw new UnsupportedOperationException("getClob is not supported")

  def getRow: Int =
    throw new UnsupportedOperationException("getRow is not supported")

  def getLong(columnIndex: Int): Long =
    current(columnIndex).getLongValue

  def getLong(columnLabel: String): Long =
    current(columnNameIndexMap(columnLabel)).getLongValue

  def getHoldability: Int =
    throw new UnsupportedOperationException("getHoldability is not supported")

  def updateFloat(columnIndex: Int, x: Float): Unit =
    throw new UnsupportedOperationException("updateFloat is not supported")

  def updateFloat(columnLabel: String, x: Float): Unit =
    throw new UnsupportedOperationException("updateFloat is not supported")

  def afterLast(): Unit =
    throw new UnsupportedOperationException("afterLast is not supported")

  def refreshRow(): Unit =
    throw new UnsupportedOperationException("refreshRow is not supported")

  def getNString(columnIndex: Int): String =
    throw new UnsupportedOperationException("getNString is not supported")

  def getNString(columnLabel: String): String =
    throw new UnsupportedOperationException("getNString is not supported")

  def deleteRow(): Unit =
    throw new UnsupportedOperationException("deleteRow is not supported")

  def getConcurrency: Int =
    throw new UnsupportedOperationException("getConcurrency is not supported")

  def updateObject(columnIndex: Int, x: scala.Any, scaleOrLength: Int): Unit =
    throw new UnsupportedOperationException("updateObject is not supported")

  def updateObject(columnIndex: Int, x: scala.Any): Unit =
    throw new UnsupportedOperationException("updateObject is not supported")

  def updateObject(columnLabel: String, x: scala.Any, scaleOrLength: Int): Unit =
    throw new UnsupportedOperationException("updateObject is not supported")

  def updateObject(columnLabel: String, x: scala.Any): Unit =
    throw new UnsupportedOperationException("updateObject is not supported")

  def getFetchSize: Int =
    throw new UnsupportedOperationException("getFetchSize is not supported")

  def getTime(columnIndex: Int): Time =
    sqlTimeFromStringValue(current(columnIndex).getStringValue)

  def getTime(columnLabel: String): Time =
    sqlTimeFromStringValue(current(columnNameIndexMap(columnLabel)).getStringValue)

  // TODO: use calendar
  def getTime(columnIndex: Int, cal: Calendar): Time =
    sqlTimeFromStringValue(current(columnIndex).getStringValue)

  // TODO: use calendar
  def getTime(columnLabel: String, cal: Calendar): Time =
    sqlTimeFromStringValue(current(columnNameIndexMap(columnLabel)).getStringValue)

  def updateCharacterStream(columnIndex: Int, x: Reader, length: Int): Unit =
    throw new UnsupportedOperationException("updateCharacterStream is not supported")

  def updateCharacterStream(columnLabel: String, reader: Reader, length: Int): Unit =
    throw new UnsupportedOperationException("updateCharacterStream is not supported")

  def updateCharacterStream(columnIndex: Int, x: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("updateCharacterStream is not supported")

  def updateCharacterStream(columnLabel: String, reader: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("updateCharacterStream is not supported")

  def updateCharacterStream(columnIndex: Int, x: Reader): Unit =
    throw new UnsupportedOperationException("updateCharacterStream is not supported")

  def updateCharacterStream(columnLabel: String, reader: Reader): Unit =
    throw new UnsupportedOperationException("updateCharacterStream is not supported")

  def getByte(columnIndex: Int): Byte =
    current(columnIndex).getLongValue.toByte

  def getByte(columnLabel: String): Byte =
    current(columnNameIndexMap(columnLabel)).getLongValue.toByte

  def getBoolean(columnIndex: Int): Boolean =
    current(columnIndex).getBooleanValue

  def getBoolean(columnLabel: String): Boolean =
    current(columnNameIndexMap(columnLabel)).getBooleanValue

  def setFetchDirection(direction: Int): Unit =
    throw new UnsupportedOperationException("setFetchDirection is not supported")

  def getFetchDirection: Int =
    throw new UnsupportedOperationException("getFetchDirection is not supported")

  def updateRef(columnIndex: Int, x: Ref): Unit =
    throw new UnsupportedOperationException("updateRef is not supported")

  def updateRef(columnLabel: String, x: Ref): Unit =
    throw new UnsupportedOperationException("updateRef is not supported")

  def getAsciiStream(columnIndex: Int): InputStream =
    throw new UnsupportedOperationException("getAsciiStream is not supported")

  def getAsciiStream(columnLabel: String): InputStream =
    throw new UnsupportedOperationException("getAsciiStream is not supported")

  def getShort(columnIndex: Int): Short =
    current(columnIndex).getLongValue.toShort

  def getShort(columnLabel: String): Short =
    current(columnNameIndexMap(columnLabel)).getLongValue.toShort

  def getObject(columnIndex: Int): AnyRef =
    current(columnIndex).getValue

  def getObject(columnLabel: String): AnyRef =
    current(columnNameIndexMap(columnLabel)).getValue

  def getObject(columnIndex: Int, map: util.Map[String, Class[_]]): AnyRef =
    throw new UnsupportedOperationException("getObject is not supported")

  def getObject(columnLabel: String, map: util.Map[String, Class[_]]): AnyRef =
    throw new UnsupportedOperationException("getObject is not supported")

  def getObject[T](columnIndex: Int, `type`: Class[T]): T =
    throw new UnsupportedOperationException("getObject is not supported")

  def getObject[T](columnLabel: String, `type`: Class[T]): T =
    throw new UnsupportedOperationException("getObject is not supported")

  def updateShort(columnIndex: Int, x: Short): Unit =
    throw new UnsupportedOperationException("updateShort is not supported")

  def updateShort(columnLabel: String, x: Short): Unit =
    throw new UnsupportedOperationException("updateShort is not supported")

  def getNCharacterStream(columnIndex: Int): Reader =
    throw new UnsupportedOperationException("getNCharacterStream is not supported")

  def getNCharacterStream(columnLabel: String): Reader =
    throw new UnsupportedOperationException("getNCharacterStream is not supported")

  // do nothing
  def close(): Unit = ()

  def relative(rows: Int): Boolean =
    throw new UnsupportedOperationException("relative is not supported")

  def updateInt(columnIndex: Int, x: Int): Unit =
    throw new UnsupportedOperationException("updateInt is not supported")

  def updateInt(columnLabel: String, x: Int): Unit =
    throw new UnsupportedOperationException("updateInt is not supported")

  def wasNull(): Boolean =
    throw new UnsupportedOperationException("wasNull is not supported")

  def rowUpdated(): Boolean =
    throw new UnsupportedOperationException("rowUpdated is not supported")

  def getRef(columnIndex: Int): Ref =
    throw new UnsupportedOperationException("getRef is not supported")

  def getRef(columnLabel: String): Ref =
    throw new UnsupportedOperationException("getRef is not supported")

  def updateLong(columnIndex: Int, x: Long): Unit =
    throw new UnsupportedOperationException("updateLong is not supported")

  def updateLong(columnLabel: String, x: Long): Unit =
    throw new UnsupportedOperationException("updateLong is not supported")

  def moveToCurrentRow(): Unit =
    throw new UnsupportedOperationException("moveToCurrentRow is not supported")

  // always true
  def isClosed: Boolean = true

  def updateClob(columnIndex: Int, x: Clob): Unit =
    throw new UnsupportedOperationException("updateClob is not supported")

  def updateClob(columnLabel: String, x: Clob): Unit =
    throw new UnsupportedOperationException("updateClob is not supported")

  def updateClob(columnIndex: Int, reader: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("updateClob is not supported")

  def updateClob(columnLabel: String, reader: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("updateClob is not supported")

  def updateClob(columnIndex: Int, reader: Reader): Unit =
    throw new UnsupportedOperationException("updateClob is not supported")

  def updateClob(columnLabel: String, reader: Reader): Unit =
    throw new UnsupportedOperationException("updateClob is not supported")

  def findColumn(columnLabel: String): Int =
    throw new UnsupportedOperationException("findColumn is not supported")

  def getWarnings: SQLWarning =
    throw new UnsupportedOperationException("getWarnings is not supported")

  def getDate(columnIndex: Int): Date =
    sqlDateFromStringValue(current(columnIndex).getStringValue)

  def getDate(columnLabel: String): Date =
    sqlDateFromStringValue(current(columnNameIndexMap(columnLabel)).getStringValue)

  def getDate(columnIndex: Int, cal: Calendar): Date =
    sqlDateFromStringValue(current(columnIndex).getStringValue)

  def getDate(columnLabel: String, cal: Calendar): Date =
    sqlDateFromStringValue(current(columnNameIndexMap(columnLabel)).getStringValue)

  def getCursorName: String =
    throw new UnsupportedOperationException("getCursorName is not supported")

  def updateNull(columnIndex: Int): Unit =
    throw new UnsupportedOperationException("updateNull is not supported")

  def updateNull(columnLabel: String): Unit =
    throw new UnsupportedOperationException("updateNull is not supported")

  def getStatement: Statement =
    throw new UnsupportedOperationException("getStatement is not supported")

  def cancelRowUpdates(): Unit =
    throw new UnsupportedOperationException("cancelRowUpdates is not supported")

  def getSQLXML(columnIndex: Int): SQLXML =
    throw new UnsupportedOperationException("getSQLXML is not supported")

  def getSQLXML(columnLabel: String): SQLXML =
    throw new UnsupportedOperationException("getSQLXML is not supported")

  def getUnicodeStream(columnIndex: Int): InputStream =
    throw new UnsupportedOperationException("getUnicodeStream is not supported")

  def getUnicodeStream(columnLabel: String): InputStream =
    throw new UnsupportedOperationException("getUnicodeStream is not supported")

  def getInt(columnIndex: Int): Int =
    current(columnIndex).getLongValue.toInt

  def getInt(columnLabel: String): Int =
    current(columnNameIndexMap(columnLabel)).getLongValue.toInt

  def updateTime(columnIndex: Int, x: Time): Unit =
    throw new UnsupportedOperationException("updateTime is not supported")

  def updateTime(columnLabel: String, x: Time): Unit =
    throw new UnsupportedOperationException("updateTime is not supported")

  def setFetchSize(rows: Int): Unit =
    throw new UnsupportedOperationException("setFetchSize is not supported")

  def previous(): Boolean =
    throw new UnsupportedOperationException("previous is not supported")

  def updateAsciiStream(columnIndex: Int, x: InputStream, length: Int): Unit =
    throw new UnsupportedOperationException("updateAsciiStream is not supported")

  def updateAsciiStream(columnLabel: String, x: InputStream, length: Int): Unit =
    throw new UnsupportedOperationException("updateAsciiStream is not supported")

  def updateAsciiStream(columnIndex: Int, x: InputStream, length: Long): Unit =
    throw new UnsupportedOperationException("updateAsciiStream is not supported")

  def updateAsciiStream(columnLabel: String, x: InputStream, length: Long): Unit =
    throw new UnsupportedOperationException("updateAsciiStream is not supported")

  def updateAsciiStream(columnIndex: Int, x: InputStream): Unit =
    throw new UnsupportedOperationException("updateAsciiStream is not supported")

  def updateAsciiStream(columnLabel: String, x: InputStream): Unit =
    throw new UnsupportedOperationException("updateAsciiStream is not supported")

  def rowDeleted(): Boolean =
    throw new UnsupportedOperationException("rowDeleted is not supported")

  def getBlob(columnIndex: Int): Blob =
    throw new UnsupportedOperationException("getBlob is not supported")

  def getBlob(columnLabel: String): Blob =
    throw new UnsupportedOperationException("getBlob is not supported")

  def first(): Boolean =
    throw new UnsupportedOperationException("first is not supported")

  def getBytes(columnIndex: Int): Array[Byte] =
    current(columnIndex).getBytesValue

  def getBytes(columnLabel: String): Array[Byte] =
    current(columnNameIndexMap(columnLabel)).getBytesValue

  def updateBytes(columnIndex: Int, x: Array[Byte]): Unit =
    throw new UnsupportedOperationException("updateBytes is not supported")

  def updateBytes(columnLabel: String, x: Array[Byte]): Unit =
    throw new UnsupportedOperationException("updateBytes is not supported")

  def updateSQLXML(columnIndex: Int, xmlObject: SQLXML): Unit =
    throw new UnsupportedOperationException("updateSQLXML is not supported")

  def updateSQLXML(columnLabel: String, xmlObject: SQLXML): Unit =
    throw new UnsupportedOperationException("updateSQLXML is not supported")

  def getString(columnIndex: Int): String =
    current(columnIndex).getStringValue

  def getString(columnLabel: String): String =
    current(columnNameIndexMap(columnLabel)).getStringValue

  def unwrap[T](iface: Class[T]): T =
    throw new UnsupportedOperationException("unwrap is not supported")

  def isWrapperFor(iface: Class[_]): Boolean =
    throw new UnsupportedOperationException("isWrapperFor is not supported")
}
