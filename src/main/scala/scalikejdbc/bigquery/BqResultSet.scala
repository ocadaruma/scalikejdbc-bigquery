package scalikejdbc.bigquery

import java.io.{Reader, InputStream}
import java.math.BigDecimal
import java.net.URL
import java.sql.{Array => SqlArray, _}
import java.util
import java.util.Calendar

class BqResultSet extends ResultSet {

  def getType: Int = ???

  def isBeforeFirst: Boolean = ???

  def next(): Boolean = ???

  def updateString(columnIndex: Int, x: String): Unit = ???

  def updateString(columnLabel: String, x: String): Unit = ???

  def getTimestamp(columnIndex: Int): Timestamp = ???

  def getTimestamp(columnLabel: String): Timestamp = ???

  def getTimestamp(columnIndex: Int, cal: Calendar): Timestamp = ???

  def getTimestamp(columnLabel: String, cal: Calendar): Timestamp = ???

  def updateNString(columnIndex: Int, nString: String): Unit = ???

  def updateNString(columnLabel: String, nString: String): Unit = ???

  def clearWarnings(): Unit = ???

  def updateTimestamp(columnIndex: Int, x: Timestamp): Unit = ???

  def updateTimestamp(columnLabel: String, x: Timestamp): Unit = ???

  def updateByte(columnIndex: Int, x: Byte): Unit = ???

  def updateByte(columnLabel: String, x: Byte): Unit = ???

  def updateBigDecimal(columnIndex: Int, x: BigDecimal): Unit = ???

  def updateBigDecimal(columnLabel: String, x: BigDecimal): Unit = ???

  def updateDouble(columnIndex: Int, x: Double): Unit = ???

  def updateDouble(columnLabel: String, x: Double): Unit = ???

  def updateDate(columnIndex: Int, x: Date): Unit = ???

  def updateDate(columnLabel: String, x: Date): Unit = ???

  def isAfterLast: Boolean = ???

  def updateBoolean(columnIndex: Int, x: Boolean): Unit = ???

  def updateBoolean(columnLabel: String, x: Boolean): Unit = ???

  def getBinaryStream(columnIndex: Int): InputStream = ???

  def getBinaryStream(columnLabel: String): InputStream = ???

  def beforeFirst(): Unit = ???

  def updateNCharacterStream(columnIndex: Int, x: Reader, length: Long): Unit = ???

  def updateNCharacterStream(columnLabel: String, reader: Reader, length: Long): Unit = ???

  def updateNCharacterStream(columnIndex: Int, x: Reader): Unit = ???

  def updateNCharacterStream(columnLabel: String, reader: Reader): Unit = ???

  def updateNClob(columnIndex: Int, nClob: NClob): Unit = ???

  def updateNClob(columnLabel: String, nClob: NClob): Unit = ???

  def updateNClob(columnIndex: Int, reader: Reader, length: Long): Unit = ???

  def updateNClob(columnLabel: String, reader: Reader, length: Long): Unit = ???

  def updateNClob(columnIndex: Int, reader: Reader): Unit = ???

  def updateNClob(columnLabel: String, reader: Reader): Unit = ???

  def last(): Boolean = ???

  def isLast: Boolean = ???

  def getNClob(columnIndex: Int): NClob = ???

  def getNClob(columnLabel: String): NClob = ???

  def getCharacterStream(columnIndex: Int): Reader = ???

  def getCharacterStream(columnLabel: String): Reader = ???

  def updateArray(columnIndex: Int, x: SqlArray): Unit = ???

  def updateArray(columnLabel: String, x: SqlArray): Unit = ???

  def updateBlob(columnIndex: Int, x: Blob): Unit = ???

  def updateBlob(columnLabel: String, x: Blob): Unit = ???

  def updateBlob(columnIndex: Int, inputStream: InputStream, length: Long): Unit = ???

  def updateBlob(columnLabel: String, inputStream: InputStream, length: Long): Unit = ???

  def updateBlob(columnIndex: Int, inputStream: InputStream): Unit = ???

  def updateBlob(columnLabel: String, inputStream: InputStream): Unit = ???

  def getDouble(columnIndex: Int): Double = ???

  def getDouble(columnLabel: String): Double = ???

  def getArray(columnIndex: Int): SqlArray = ???

  def getArray(columnLabel: String): SqlArray = ???

  def isFirst: Boolean = ???

  def getURL(columnIndex: Int): URL = ???

  def getURL(columnLabel: String): URL = ???

  def updateRow(): Unit = ???

  def insertRow(): Unit = ???

  def getMetaData: ResultSetMetaData = ???

  def updateBinaryStream(columnIndex: Int, x: InputStream, length: Int): Unit = ???

  def updateBinaryStream(columnLabel: String, x: InputStream, length: Int): Unit = ???

  def updateBinaryStream(columnIndex: Int, x: InputStream, length: Long): Unit = ???

  def updateBinaryStream(columnLabel: String, x: InputStream, length: Long): Unit = ???

  def updateBinaryStream(columnIndex: Int, x: InputStream): Unit = ???

  def updateBinaryStream(columnLabel: String, x: InputStream): Unit = ???

  def absolute(row: Int): Boolean = ???

  def updateRowId(columnIndex: Int, x: RowId): Unit = ???

  def updateRowId(columnLabel: String, x: RowId): Unit = ???

  def getRowId(columnIndex: Int): RowId = ???

  def getRowId(columnLabel: String): RowId = ???

  def moveToInsertRow(): Unit = ???

  def rowInserted(): Boolean = ???

  def getFloat(columnIndex: Int): Float = ???

  def getFloat(columnLabel: String): Float = ???

  def getBigDecimal(columnIndex: Int, scale: Int): BigDecimal = ???

  def getBigDecimal(columnLabel: String, scale: Int): BigDecimal = ???

  def getBigDecimal(columnIndex: Int): BigDecimal = ???

  def getBigDecimal(columnLabel: String): BigDecimal = ???

  def getClob(columnIndex: Int): Clob = ???

  def getClob(columnLabel: String): Clob = ???

  def getRow: Int = ???

  def getLong(columnIndex: Int): Long = ???

  def getLong(columnLabel: String): Long = ???

  def getHoldability: Int = ???

  def updateFloat(columnIndex: Int, x: Float): Unit = ???

  def updateFloat(columnLabel: String, x: Float): Unit = ???

  def afterLast(): Unit = ???

  def refreshRow(): Unit = ???

  def getNString(columnIndex: Int): String = ???

  def getNString(columnLabel: String): String = ???

  def deleteRow(): Unit = ???

  def getConcurrency: Int = ???

  def updateObject(columnIndex: Int, x: scala.Any, scaleOrLength: Int): Unit = ???

  def updateObject(columnIndex: Int, x: scala.Any): Unit = ???

  def updateObject(columnLabel: String, x: scala.Any, scaleOrLength: Int): Unit = ???

  def updateObject(columnLabel: String, x: scala.Any): Unit = ???

  def getFetchSize: Int = ???

  def getTime(columnIndex: Int): Time = ???

  def getTime(columnLabel: String): Time = ???

  def getTime(columnIndex: Int, cal: Calendar): Time = ???

  def getTime(columnLabel: String, cal: Calendar): Time = ???

  def updateCharacterStream(columnIndex: Int, x: Reader, length: Int): Unit = ???

  def updateCharacterStream(columnLabel: String, reader: Reader, length: Int): Unit = ???

  def updateCharacterStream(columnIndex: Int, x: Reader, length: Long): Unit = ???

  def updateCharacterStream(columnLabel: String, reader: Reader, length: Long): Unit = ???

  def updateCharacterStream(columnIndex: Int, x: Reader): Unit = ???

  def updateCharacterStream(columnLabel: String, reader: Reader): Unit = ???

  def getByte(columnIndex: Int): Byte = ???

  def getByte(columnLabel: String): Byte = ???

  def getBoolean(columnIndex: Int): Boolean = ???

  def getBoolean(columnLabel: String): Boolean = ???

  def setFetchDirection(direction: Int): Unit = ???

  def getFetchDirection: Int = ???

  def updateRef(columnIndex: Int, x: Ref): Unit = ???

  def updateRef(columnLabel: String, x: Ref): Unit = ???

  def getAsciiStream(columnIndex: Int): InputStream = ???

  def getAsciiStream(columnLabel: String): InputStream = ???

  def getShort(columnIndex: Int): Short = ???

  def getShort(columnLabel: String): Short = ???

  def getObject(columnIndex: Int): AnyRef = ???

  def getObject(columnLabel: String): AnyRef = ???

  def getObject(columnIndex: Int, map: util.Map[String, Class[_]]): AnyRef = ???

  def getObject(columnLabel: String, map: util.Map[String, Class[_]]): AnyRef = ???

  def getObject[T](columnIndex: Int, `type`: Class[T]): T = ???

  def getObject[T](columnLabel: String, `type`: Class[T]): T = ???

  def updateShort(columnIndex: Int, x: Short): Unit = ???

  def updateShort(columnLabel: String, x: Short): Unit = ???

  def getNCharacterStream(columnIndex: Int): Reader = ???

  def getNCharacterStream(columnLabel: String): Reader = ???

  def close(): Unit = ???

  def relative(rows: Int): Boolean = ???

  def updateInt(columnIndex: Int, x: Int): Unit = ???

  def updateInt(columnLabel: String, x: Int): Unit = ???

  def wasNull(): Boolean = ???

  def rowUpdated(): Boolean = ???

  def getRef(columnIndex: Int): Ref = ???

  def getRef(columnLabel: String): Ref = ???

  def updateLong(columnIndex: Int, x: Long): Unit = ???

  def updateLong(columnLabel: String, x: Long): Unit = ???

  def moveToCurrentRow(): Unit = ???

  def isClosed: Boolean = ???

  def updateClob(columnIndex: Int, x: Clob): Unit = ???

  def updateClob(columnLabel: String, x: Clob): Unit = ???

  def updateClob(columnIndex: Int, reader: Reader, length: Long): Unit = ???

  def updateClob(columnLabel: String, reader: Reader, length: Long): Unit = ???

  def updateClob(columnIndex: Int, reader: Reader): Unit = ???

  def updateClob(columnLabel: String, reader: Reader): Unit = ???

  def findColumn(columnLabel: String): Int = ???

  def getWarnings: SQLWarning = ???

  def getDate(columnIndex: Int): Date = ???

  def getDate(columnLabel: String): Date = ???

  def getDate(columnIndex: Int, cal: Calendar): Date = ???

  def getDate(columnLabel: String, cal: Calendar): Date = ???

  def getCursorName: String = ???

  def updateNull(columnIndex: Int): Unit = ???

  def updateNull(columnLabel: String): Unit = ???

  def getStatement: Statement = ???

  def cancelRowUpdates(): Unit = ???

  def getSQLXML(columnIndex: Int): SQLXML = ???

  def getSQLXML(columnLabel: String): SQLXML = ???

  def getUnicodeStream(columnIndex: Int): InputStream = ???

  def getUnicodeStream(columnLabel: String): InputStream = ???

  def getInt(columnIndex: Int): Int = ???

  def getInt(columnLabel: String): Int = ???

  def updateTime(columnIndex: Int, x: Time): Unit = ???

  def updateTime(columnLabel: String, x: Time): Unit = ???

  def setFetchSize(rows: Int): Unit = ???

  def previous(): Boolean = ???

  def updateAsciiStream(columnIndex: Int, x: InputStream, length: Int): Unit = ???

  def updateAsciiStream(columnLabel: String, x: InputStream, length: Int): Unit = ???

  def updateAsciiStream(columnIndex: Int, x: InputStream, length: Long): Unit = ???

  def updateAsciiStream(columnLabel: String, x: InputStream, length: Long): Unit = ???

  def updateAsciiStream(columnIndex: Int, x: InputStream): Unit = ???

  def updateAsciiStream(columnLabel: String, x: InputStream): Unit = ???

  def rowDeleted(): Boolean = ???

  def getBlob(columnIndex: Int): Blob = ???

  def getBlob(columnLabel: String): Blob = ???

  def first(): Boolean = ???

  def getBytes(columnIndex: Int): Array[Byte] = ???

  def getBytes(columnLabel: String): Array[Byte] = ???

  def updateBytes(columnIndex: Int, x: Array[Byte]): Unit = ???

  def updateBytes(columnLabel: String, x: Array[Byte]): Unit = ???

  def updateSQLXML(columnIndex: Int, xmlObject: SQLXML): Unit = ???

  def updateSQLXML(columnLabel: String, xmlObject: SQLXML): Unit = ???

  def getString(columnIndex: Int): String = ???

  def getString(columnLabel: String): String = ???

  def unwrap[T](iface: Class[T]): T = ???

  def isWrapperFor(iface: Class[_]): Boolean = ???
}
