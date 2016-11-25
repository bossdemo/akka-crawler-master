name := "sbt-crawler"

version := "1.0"

scalaVersion := "2.11.8"

val slickVersion = "3.1.1"

lazy val mainProject = Project(
  id="slick-codegen-example",
  base=file("."),
  settings = Defaults.coreDefaultSettings ++ Seq(
    libraryDependencies ++= List(
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-codegen" % slickVersion,
      "com.h2database" % "h2" % "1.4.191",
      "com.typesafe.akka" %% "akka-cluster-tools" % "2.4.1",
      "com.typesafe.akka" %% "akka-http-core" % "10.0.0",
      "com.typesafe.akka" %% "akka-http" % "10.0.0",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.0",
      "com.typesafe.akka" % "akka-http-core-experimental_2.11" % "2.0.4",
      "de.heikoseeberger" % "akka-http-json4s_2.11" % "1.10.1",
      "org.json4s" % "json4s-native_2.11" % "3.5.0",
      "org.json4s" % "json4s-ext_2.10" % "3.5.0",
      "ch.qos.logback" % "logback-classic" % "1.1.7",
      "com.typesafe.akka" %% "akka-slf4j" % "2.4.14"
    ),
    slick <<= slickCodeGenTask, // register manual sbt command
    sourceGenerators in Compile <+= slickCodeGenTask // register automatic code generation on every compile, remove for only manual use
  )
)

// code generation task
lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = (dir / "slick").getPath // place generated files in sbt's managed sources folder
val url = "jdbc:h2:file:e:/db/crawler;INIT=runscript from 'src/main/resources/task.sql'" // connection info for a pre-populated throw-away, in-memory db for this demo, which is freshly initialized on every run
val jdbcDriver = "org.h2.Driver"
  val slickDriver = "slick.driver.H2Driver"
  val pkg = "demo"
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg), s.log))
  val fname = outputDir + "/demo/Tables.scala"
  Seq(file(fname))
}