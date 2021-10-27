import Dependencies._
import scalapb.compiler.Version.{grpcJavaVersion, scalapbVersion, protobufVersion}

ThisBuild / scalaVersion     := "2.12.11"
ThisBuild / version          := "0"
ThisBuild / organization     := "com.some"
ThisBuild / organizationName := "com"

resolvers ++= Seq(
  "jitpack" at "https://jitpack.io",
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "confluent" at "https://packages.confluent.io/maven/"
)

scalacOptions ++= Seq("-target:jvm-1.8")

val circeVersion = "0.12.3"
lazy val root = (project in file("."))
  .settings(
    name := "FeatureServer",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.1.2" % "test",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "redis.clients" % "jedis" % "3.1.0",
      "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
      "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
      "org.json4s" %% "json4s-jackson" % "3.7.0-M2",
      "ch.hsr" % "geohash" % "1.4.0",
      "org.scalanlp" %% "breeze" % "0.13",
      "org.apache.kafka" % "kafka-clients" % "2.1.1",
      "com.sksamuel.avro4s" %% "avro4s-core" % "3.1.1",
      "io.confluent" % "kafka-avro-serializer" % "5.5.0",
      "io.etcd" % "jetcd-core" % "0.5.3",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
      "org.tensorflow" % "tensorflow" % "1.15.0",
      "com.github.pureconfig" %% "pureconfig" % "0.14.0"
    ))

assemblyJarName in assembly := s"${name.value}_2.12.jar"
Compile/mainClass := Some("com.taqu.server.feature.FeatureServer")
test in assembly := {}
//scalacOptions in Compile ++= Seq("-Xprint-types", "-Xprint:typer")
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
assemblyMergeStrategy in assembly := {
  case path if path.contains("META-INF/services") => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
