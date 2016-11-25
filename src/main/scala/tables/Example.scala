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
    val url = "jdbc:h2:file:e:/db/crawler;INIT=runscript from 'src/main/resources/task.sql'"
    //jdbc:h2:file:e:/db/crawler;INIT=runscript from 'src/main/resources/task.sql'
    val db = Database.forURL(url, driver = "org.h2.Driver")


//    val crawlerTask = TaskRow("www.amap.com","crawler","detail","0","12")
//    val insert = sqlu"INSERT INTO task(url, source,type,status,prior) VALUES (${crawlerTask.url},${crawlerTask.source},${crawlerTask.`type`},${crawlerTask.status},${crawlerTask.prior})"
//    Await.result(
//      db.run(insert).map{ res =>
//        res match{
//          case 1 => println("all good")
//          case 0 => println("couldn't add record for some reason")
//        }
//      }.recover{
//        case e:Exception =>println("Caught exception: "+e.getMessage)
//      },10 seconds)

    // Using generated code. Our Build.sbt makes sure they are generated before compilation.
    val q = Task.filter(_.status === "0").sortBy(_.prior.desc)

    val task = Await.result(db.run(q.result), 60 seconds)
    println(task)
  }
}