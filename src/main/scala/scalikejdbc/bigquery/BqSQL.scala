package scalikejdbc.bigquery

import scalikejdbc._

class BqSQL(val statement: SQLSyntax) extends ExtractorBuilder with OneToManyExtractorBuilder
