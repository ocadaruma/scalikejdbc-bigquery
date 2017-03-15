name := "scalikejdbc-bigquery"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

val scalikejdbcVersion = "2.4.2"
val googleCloudVersion = "0.9.3-beta"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion % "provided",
  "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion % "test",

  "com.google.cloud" % "google-cloud-bigquery" % googleCloudVersion % "provided",
  "com.google.cloud" % "google-cloud-bigquery" % googleCloudVersion % "test",

  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
