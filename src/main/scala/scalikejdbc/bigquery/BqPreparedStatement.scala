package scalikejdbc.bigquery

import java.io.{InputStream, Reader}
import java.math.BigDecimal
import java.net.URL
import java.sql.{Array => SqlArray, _}
import java.util.Calendar

import scala.collection.concurrent.TrieMap
import scala.collection.mutable

/**
 * A PreparedStatement implementation that just holds parameters and indices.
 */
class BqPreparedStatement extends PreparedStatement {

  private[this] val parameterMap: mutable.Map[Int, BqParameter] = TrieMap.empty

  def parameters: Map[Int, BqParameter] = parameterMap.toMap

  def setByte(parameterIndex: Int, x: Byte): Unit =
    parameterMap(parameterIndex) = BqParameter.Int64(x)

  def getParameterMetaData: ParameterMetaData = ???

  def setRef(parameterIndex: Int, x: Ref): Unit =
    throw new UnsupportedOperationException("setRef is not supported")

  def clearParameters(): Unit = ???

  def setBytes(parameterIndex: Int, x: Array[Byte]): Unit =
    parameterMap(parameterIndex) = BqParameter.Bytes(x)

  def setBinaryStream(parameterIndex: Int, x: InputStream, length: Int): Unit =
    throw new UnsupportedOperationException("setBinaryStream is not supported")

  def setBinaryStream(parameterIndex: Int, x: InputStream, length: Long): Unit =
    throw new UnsupportedOperationException("setBinaryStream is not supported")

  def setBinaryStream(parameterIndex: Int, x: InputStream): Unit =
    throw new UnsupportedOperationException("setBinaryStream is not supported")

  def setAsciiStream(parameterIndex: Int, x: InputStream, length: Int): Unit =
    throw new UnsupportedOperationException("setAsciiStream is not supported")

  def setAsciiStream(parameterIndex: Int, x: InputStream, length: Long): Unit =
    throw new UnsupportedOperationException("setAsciiStream is not supported")

  def setAsciiStream(parameterIndex: Int, x: InputStream): Unit =
    throw new UnsupportedOperationException("setAsciiStream is not supported")

  def setObject(parameterIndex: Int, x: scala.Any, targetSqlType: Int): Unit =
    throw new UnsupportedOperationException("setObject is not supported")

  def setObject(parameterIndex: Int, x: scala.Any): Unit =
    throw new UnsupportedOperationException("setObject is not supported")

  def setObject(parameterIndex: Int, x: scala.Any, targetSqlType: Int, scaleOrLength: Int): Unit =
    throw new UnsupportedOperationException("setObject is not supported")

  def setDate(parameterIndex: Int, x: Date): Unit = ???

  def setDate(parameterIndex: Int, x: Date, cal: Calendar): Unit = ???

  def setTimestamp(parameterIndex: Int, x: Timestamp): Unit = ???

  def setTimestamp(parameterIndex: Int, x: Timestamp, cal: Calendar): Unit = ???

  def setUnicodeStream(parameterIndex: Int, x: InputStream, length: Int): Unit =
    throw new UnsupportedOperationException("setUnicodeStream is not supported")

  def getMetaData: ResultSetMetaData = ???

  def setBlob(parameterIndex: Int, x: Blob): Unit =
    throw new UnsupportedOperationException("setBlob is not supported")

  def setBlob(parameterIndex: Int, inputStream: InputStream, length: Long): Unit =
    throw new UnsupportedOperationException("setBlob is not supported")

  def setBlob(parameterIndex: Int, inputStream: InputStream): Unit =
    throw new UnsupportedOperationException("setBlob is not supported")

  def addBatch(): Unit =
    throw new UnsupportedOperationException("addBatch is not supported")

  def execute(): Boolean =
    throw new UnsupportedOperationException("execute is not supported")

  def executeQuery(): ResultSet =
    throw new UnsupportedOperationException("executeQuery is not supported")

  def setNClob(parameterIndex: Int, value: NClob): Unit =
    throw new UnsupportedOperationException("setNClob is not supported")

  def setNClob(parameterIndex: Int, reader: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("setNClob is not supported")

  def setNClob(parameterIndex: Int, reader: Reader): Unit =
    throw new UnsupportedOperationException("setNClob is not supported")

  def setArray(parameterIndex: Int, x: SqlArray): Unit =
    throw new UnsupportedOperationException("setArray is not supported")

  def setNCharacterStream(parameterIndex: Int, value: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("setNCharacterStream is not supported")

  def setNCharacterStream(parameterIndex: Int, value: Reader): Unit =
    throw new UnsupportedOperationException("setNCharacterStream is not supported")

  def setURL(parameterIndex: Int, x: URL): Unit =
    throw new UnsupportedOperationException("setURL is not supported")

  def setRowId(parameterIndex: Int, x: RowId): Unit =
    throw new UnsupportedOperationException("setRowId is not supported")

  def setSQLXML(parameterIndex: Int, xmlObject: SQLXML): Unit =
    throw new UnsupportedOperationException("setSQLXML is not supported")

  def setString(parameterIndex: Int, x: String): Unit =
    parameterMap(parameterIndex) = BqParameter.String(x)

  def setFloat(parameterIndex: Int, x: Float): Unit =
    parameterMap(parameterIndex) = BqParameter.Float64(x)

  def setNString(parameterIndex: Int, value: String): Unit =
    throw new UnsupportedOperationException("setNString is not supported")

  def setBoolean(parameterIndex: Int, x: Boolean): Unit =
    parameterMap(parameterIndex) = BqParameter.Bool(x)

  def setDouble(parameterIndex: Int, x: Double): Unit =
    parameterMap(parameterIndex) = BqParameter.Float64(x)

  def setBigDecimal(parameterIndex: Int, x: BigDecimal): Unit =
    parameterMap(parameterIndex) = BqParameter.String(x.toPlainString)

  def executeUpdate(): Int =
    throw new UnsupportedOperationException("executeUpdate is not supported")

  def setTime(parameterIndex: Int, x: Time): Unit = ???

  def setTime(parameterIndex: Int, x: Time, cal: Calendar): Unit = ???

  def setShort(parameterIndex: Int, x: Short): Unit =
    parameterMap(parameterIndex) = BqParameter.Int64(x)

  def setLong(parameterIndex: Int, x: Long): Unit =
    parameterMap(parameterIndex) = BqParameter.Int64(x)

  def setCharacterStream(parameterIndex: Int, reader: Reader, length: Int): Unit =
    throw new UnsupportedOperationException("setCharacterStream is not supported")

  def setCharacterStream(parameterIndex: Int, reader: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("setCharacterStream is not supported")

  def setCharacterStream(parameterIndex: Int, reader: Reader): Unit =
    throw new UnsupportedOperationException("setCharacterStream is not supported")

  def setClob(parameterIndex: Int, x: Clob): Unit =
    throw new UnsupportedOperationException("setClob is not supported")

  def setClob(parameterIndex: Int, reader: Reader, length: Long): Unit =
    throw new UnsupportedOperationException("setClob is not supported")

  def setClob(parameterIndex: Int, reader: Reader): Unit =
    throw new UnsupportedOperationException("setClob is not supported")

  def setNull(parameterIndex: Int, sqlType: Int): Unit = ???

  def setNull(parameterIndex: Int, sqlType: Int, typeName: String): Unit = ???

  def setInt(parameterIndex: Int, x: Int): Unit =
    parameterMap(parameterIndex) = BqParameter.Int64(x)

  def setMaxFieldSize(max: Int): Unit =
    throw new UnsupportedOperationException("setMaxFieldSize is not supported")

  def getMoreResults: Boolean =
    throw new UnsupportedOperationException("getMoreResults is not supported")

  def getMoreResults(current: Int): Boolean =
    throw new UnsupportedOperationException("getMoreResults is not supported")

  def clearWarnings(): Unit = ???

  def getGeneratedKeys: ResultSet =
    throw new UnsupportedOperationException("getGeneratedKeys is not supported")

  def closeOnCompletion(): Unit =
    throw new UnsupportedOperationException("closeOnCompletion is not supported")

  def cancel(): Unit =
    throw new UnsupportedOperationException("cancel is not supported")

  def getResultSet: ResultSet =
    throw new UnsupportedOperationException("getResultSet is not supported")

  def setPoolable(poolable: Boolean): Unit =
    throw new UnsupportedOperationException("setPoolable is not supported")

  def isPoolable: Boolean =
    throw new UnsupportedOperationException("isPoolable is not supported")

  def setCursorName(name: String): Unit =
    throw new UnsupportedOperationException("setCursorName is not supported")

  def getUpdateCount: Int =
    throw new UnsupportedOperationException("getUpdateCount is not supported")

  def addBatch(sql: String): Unit =
    throw new UnsupportedOperationException("addBatch is not supported")

  def getMaxRows: Int =
    throw new UnsupportedOperationException("getMaxRows is not supported")

  def execute(sql: String): Boolean =
    throw new UnsupportedOperationException("execute is not supported")

  def execute(sql: String, autoGeneratedKeys: Int): Boolean =
    throw new UnsupportedOperationException("execute is not supported")

  def execute(sql: String, columnIndexes: Array[Int]): Boolean =
    throw new UnsupportedOperationException("execute is not supported")

  def execute(sql: String, columnNames: Array[String]): Boolean =
    throw new UnsupportedOperationException("execute is not supported")

  def executeQuery(sql: String): ResultSet =
    throw new UnsupportedOperationException("executeQuery is not supported")

  def getResultSetType: Int =
    throw new UnsupportedOperationException("getResultSetType is not supported")

  def setMaxRows(max: Int): Unit =
    throw new UnsupportedOperationException("setMaxRows is not supported")

  def getFetchSize: Int =
    throw new UnsupportedOperationException("getFetchSize is not supported")

  def getResultSetHoldability: Int =
    throw new UnsupportedOperationException("getResultSetHoldability is not supported")

  def setFetchDirection(direction: Int): Unit =
    throw new UnsupportedOperationException("setFetchDirection is not supported")

  def getFetchDirection: Int =
    throw new UnsupportedOperationException("getFetchDirection is not supported")

  def getResultSetConcurrency: Int =
    throw new UnsupportedOperationException("getResultSetConcurrency is not supported")

  def clearBatch(): Unit =
    throw new UnsupportedOperationException("clearBatch is not supported")

  def close(): Unit =
    throw new UnsupportedOperationException("close is not supported")

  def isClosed: Boolean =
    throw new UnsupportedOperationException("isClosed is not supported")

  def executeUpdate(sql: String): Int =
    throw new UnsupportedOperationException("executeUpdate is not supported")

  def executeUpdate(sql: String, autoGeneratedKeys: Int): Int =
    throw new UnsupportedOperationException("executeUpdate is not supported")

  def executeUpdate(sql: String, columnIndexes: Array[Int]): Int =
    throw new UnsupportedOperationException("executeUpdate is not supported")

  def executeUpdate(sql: String, columnNames: Array[String]): Int =
    throw new UnsupportedOperationException("executeUpdate is not supported")

  def getQueryTimeout: Int =
    throw new UnsupportedOperationException("getQueryTimeout is not supported")

  def getWarnings: SQLWarning =
    throw new UnsupportedOperationException("getWarnings is not supported")

  def setFetchSize(rows: Int): Unit =
    throw new UnsupportedOperationException("setFetchSize is not supported")

  def setQueryTimeout(seconds: Int): Unit =
    throw new UnsupportedOperationException("setQueryTimeout is not supported")

  def executeBatch(): Array[Int] =
    throw new UnsupportedOperationException("executeBatch is not supported")

  def setEscapeProcessing(enable: Boolean): Unit =
    throw new UnsupportedOperationException("setEscapeProcessing is not supported")

  def getConnection: Connection =
    throw new UnsupportedOperationException("getConnection is not supported")

  def getMaxFieldSize: Int =
    throw new UnsupportedOperationException("getMaxFieldSize is not supported")

  def isCloseOnCompletion: Boolean =
    throw new UnsupportedOperationException("isCloseOnCompletion is not supported")

  def unwrap[T](iface: Class[T]): T =
    throw new UnsupportedOperationException("unwrap is not supported")

  def isWrapperFor(iface: Class[_]): Boolean =
    throw new UnsupportedOperationException("isWrapperFor is not supported")
}
