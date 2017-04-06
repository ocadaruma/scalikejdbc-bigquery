name := "scalikejdbc-bigquery"

organization := "com.mayreh"

licenses += (("Apache-2.0", url("https://raw.githubusercontent.com/ocadaruma/scalikejdbc-bigquery/master/LICENSE")))

version := "0.0.4-SNAPSHOT"

publishMavenStyle := true

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

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
