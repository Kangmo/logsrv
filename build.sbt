name := """logsrv"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "com.newrelic.agent.java" % "newrelic-api" % "3.5.0",
  "com.logentries" % "logentries-appender" % "1.1.21",
  jdbc,
  anorm,
  cache,
  ws
)
