name := "trueml-backend-scala"

version := "1.0"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.2.4",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.4",
  "org.sangria-graphql" %% "sangria" % "2.0.0",
  "org.sangria-graphql" %% "sangria-spray-json" % "1.0.1",
  "com.typesafe.akka" %% "akka-testkit" % "2.6.10" % Test,
  "org.scalatest" %% "scalatest" % "3.2.2" % Test
)
