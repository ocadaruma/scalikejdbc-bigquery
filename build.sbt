name := "scalikejdbc-bigquery"

organization := "com.mayreh"

licenses += (("Apache-2.0", url("https://raw.githubusercontent.com/ocadaruma/scalikejdbc-bigquery/master/LICENSE")))

version := "0.1.5-SNAPSHOT"

publishMavenStyle := true

val scala212 = "2.12.10"

scalaVersion := scala212

crossScalaVersions := Seq("2.11.12", scala212, "2.13.1")

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

configs(IntegrationTest)

inConfig(IntegrationTest)(Defaults.itSettings)

val scalikejdbcVersion = "3.4.1"
val googleCloudVersion = "1.108.1"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion % "provided,it,test",

  "com.google.cloud" % "google-cloud-bigquery" % googleCloudVersion % "provided,it,test",

  "org.scalatest" %% "scalatest" % "3.1.1" % "it,test",
  "org.scalamock" %% "scalamock" % "4.4.0" % "it,test"
)
