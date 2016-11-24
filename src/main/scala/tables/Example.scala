package tables

import tables.Tables._
import tables.Tables.profile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps


object Example{

  def main(args: Array[String]) {
    db()
  }

  def db()={
    // connection info for a pre-populated throw-away, in-memory db for this demo, which is freshly initialized on every run
//    val url = "jdbc:h2:file:e:/db/db;INIT=runscript from 'src/main/resources/create.sql'"
//    val db = Database.forURL(url, driver = "org.h2.Driver")
//
//    // Using generated code. Our Build.sbt makes sure they are generated before compilation.
//    val q = Companies.join(Computers).on(_.id === _.manufacturerId)
//      .map { case (co, cp) => (co.name, cp.name) }
//
////    Await.result(db.run(q.result).map { result =>
////      println(result.groupBy { case (co, cp) => co }
////        .mapValues(_.map { case (co, cp) => cp })
////        .mkString("\n")
////      )
////    }, 60 seconds)
//
//    val query = Companies
//    Await.result(
//      db.run(query.result).map{result =>
//        println(result)
//      },10 seconds
//    )
  }
}