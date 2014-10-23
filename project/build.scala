import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

import sbtassembly.Plugin._
import sbtassembly.Plugin.AssemblyKeys._

object GeekbookmarksBuild extends Build {
  val Organization = "me.lilacpenguin"
  val Name = "geek-bookmarks"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.1"
  val ScalatraVersion = "2.3.0"

  val myAssemblySettings = assemblySettings ++ Seq(
    // handle conflicts during assembly task
    mergeStrategy in assembly <<= (mergeStrategy in assembly) {
      (old) => {
        case "about.html" => MergeStrategy.first
        case x => old(x)
      }
    },

    // copy web resources to /webapp folder
    resourceGenerators in Compile <+= (resourceManaged, baseDirectory) map {
      (managedBase, base) =>
        val webappBase = base / "src" / "main" / "webapp"
        for {
          (from, to) <- webappBase ** "*" x rebase(webappBase, managedBase / "main" / "webapp")
        } yield {
          Sync.copy(from, to)
          to
        }
    }
  )

  lazy val project = Project (
    "geek-bookmarks",
    file("."),
    settings = ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ assemblySettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
        "com.novus" %% "salat" % "1.9.9",
        "org.mongodb" %% "casbah" % "2.7.2",
        "ch.qos.logback" % "logback-classic" % "1.1.1" % "runtime",
        "org.scalatra" %% "scalatra-json" % "2.3.0",
        "org.json4s"   %% "json4s-jackson" % "3.2.9",
        "org.json4s"   %% "json4s-ext"     % "3.2.9",
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime",
        "org.eclipse.jetty" % "jetty-webapp" % "9.1.3.v20140225" % "container;compile",
        "org.eclipse.jetty" % "jetty-plus" % "9.1.3.v20140225" % "container",
        "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar"))
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}
