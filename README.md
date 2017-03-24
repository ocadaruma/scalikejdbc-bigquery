# scalikejdbc-bigquery

[![Build Status](https://travis-ci.org/ocadaruma/scalikejdbc-bigquery.svg?branch=master)](https://travis-ci.org/ocadaruma/scalikejdbc-bigquery)

ScalikeJDBC extension for Google BigQuery

## Prerequisites

- Java 8
- Scala 2.11.x
- ScalikeJDBC 2.4.x or newer

## Installation

```scala
libraryDependencies ++= Seq(
  "com.mayreh" %% "scalikejdbc-bigquery" % "0.0.1",
  "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion // specify scalikejdbc version you want. 
)
```

## Usage

```scala
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.bigquery.{BigQueryOptions, DatasetId}

// instantiate BigQuery service and DatsetId
val credentials = GoogleCredentials.fromStream(new FileInputStream("/path/to/key.json"))
val bigQuery = BigQueryOptions.newBuilder()
  .setCredentials(credentials)
  .setProjectId("your-gcp-project-id")
  .build()
  .getService
  
val datasetId = DatasetId.of("your-gcp-project-id", "your-dataset")

// build query by QueryDSL then execute
val executor = new QueryExecutor(bigQuery, QueryConfig())

val response = bq {
  selectFrom(User in dataset as u)
    .where.eq(u.id, 42)
}.map(User(_)).single.run(executor)

response.result // => Option[User]
```

See also [integration test](https://github.com/ocadaruma/scalikejdbc-bigquery/blob/master/src/it/scala/scalikejdbc/bigquery/QueryDSLIntegration.scala).

## Current status

This project is still in an early stage. Newer releases can include breaking changes.

Be careful using in production.
