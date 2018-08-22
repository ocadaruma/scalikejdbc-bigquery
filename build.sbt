name := "scalikejdbc-bigquery"

organization := "com.mayreh"

licenses += (("Apache-2.0", url("https://raw.githubusercontent.com/ocadaruma/scalikejdbc-bigquery/master/LICENSE")))

version := "0.0.9-SNAPSHOT"

publishMavenStyle := true

val scala211 = "2.11.8"

scalaVersion := scala211

crossScalaVersions := Seq(scala211, "2.12.6")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

configs(IntegrationTest)

inConfig(IntegrationTest)(Defaults.itSettings)

val scalikejdbcVersion = "3.0.0"
val googleCloudVersion = "0.30.0-beta"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion % "provided,it,test",

  "com.google.cloud" % "google-cloud-bigquery" % googleCloudVersion % "provided,it,test",

  "org.scalatest" %% "scalatest" % "3.0.1" % "it,test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % "it,test"
)
