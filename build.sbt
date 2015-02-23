name := """play-scala-reactivemongo-guice"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.5"

lazy val root = project.in(file(".")).enablePlugins(PlayScala)

dependencyOverrides ++= Set(
  "io.netty" % "netty" % "3.9.3.Final"
)

libraryDependencies ++= Seq(
  filters,
  "com.google.inject" % "guice" % "3.0",
  "javax.inject" % "javax.inject" % "1",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23",
  "org.mockito" % "mockito-core" % "1.10.8" % "test"
)
