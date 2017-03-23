name := "scalikejdbc-bigquery"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

configs(IntegrationTest)

inConfig(IntegrationTest)(Defaults.itSettings)

val scalikejdbcVersion = "3.0.0-RC3"
val googleCloudVersion = "0.9.3-beta"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion % "provided,it,test",

  "com.google.cloud" % "google-cloud-bigquery" % googleCloudVersion % "compile",

  "org.scalatest" %% "scalatest" % "3.0.1" % "it,test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % "it,test"
)
