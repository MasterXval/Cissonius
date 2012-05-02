import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "Cissonius"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "com.google.code.morphia" % "morphia" % "0.99",
      "org.mongodb" % "mongo-java-driver" % "2.7.3",
      "com.mongodb.casbah" %% "casbah" % "2.1.5-1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      resolvers += "Maven repository" at "http://morphia.googlecode.com/svn/mavenrepo/",
      resolvers += "Scala Tools Repository" at "http://scala-tools.org/repo-releases/"
    )

}
