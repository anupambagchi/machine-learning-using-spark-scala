// give the user a nice default project!

lazy val root = (project in file(".")).

  settings(
    inThisBuild(List(
      organization := "com.anupambagchi",
      scalaVersion := "2.11.8"
    )),
    name := "machine-learning-using-spark",
    version := "1.0.0",

    sparkVersion := "2.3.1",
    sparkComponents := Seq(),

    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
    javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:+CMSClassUnloadingEnabled"),
    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    parallelExecution in Test := false,
    fork := true,

    coverageHighlighting := true,

    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-streaming" % "2.3.1" % "provided",
      "org.apache.spark" %% "spark-sql" % "2.3.1" % "provided",

      "org.scalatest" %% "scalatest" % "3.0.1" % "test",
      "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
      "com.holdenkarau" %% "spark-testing-base" % "2.3.1_0.10.0" % Test
    ),

    // uses compile classpath for the run task, including "provided" jar (cf http://stackoverflow.com/a/21803413/3827)
    run in Compile := Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run)).evaluated,

    scalacOptions ++= Seq("-deprecation", "-unchecked"),
    pomIncludeRepository := { x => false },

   resolvers ++= Seq(
      "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
      "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
      Resolver.sonatypeRepo("public")
    ),

    pomIncludeRepository := { x => false },

    // publish settings
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },

		assemblyMergeStrategy in assembly := {
			case PathList("org","aopalliance", xs @ _*) => MergeStrategy.last
			case PathList("javax", "inject", xs @ _*) => MergeStrategy.last
			case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
			case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
			case PathList("org", "apache", xs @ _*) => MergeStrategy.last
			case PathList("com", "google", xs @ _*) => MergeStrategy.last
			case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
			case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
			case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
			case "about.html" => MergeStrategy.rename
			case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
			case "META-INF/mailcap" => MergeStrategy.last
			case "META-INF/mimetypes.default" => MergeStrategy.last
			case "plugin.properties" => MergeStrategy.last
			case "log4j.properties" => MergeStrategy.last
			case x =>
				val oldStrategy = (assemblyMergeStrategy in assembly).value
				oldStrategy(x)
		}
  )
