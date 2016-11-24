package bean

import tables.Tables._
import tables.Tables.profile.api._
import scala.concurrent.duration._
import scala.concurrent._
import ExecutionContext.Implicits.global

/**
  * Created by tongtao.zhu on 2016/11/23.
  */

trait CrawlerTaskRepository{
  def create(crawlerTask: TaskRow)
  def find(crawlerTask: TaskRow)
  def update(crawlerTask: TaskRow)
  def delete(crawlerTask: TaskRow)
  def findByPrior(dbName:String): Option[TaskRow]
}

class H2CrawlerTaskRepository extends CrawlerTaskRepository{
  override def create(crawlerTask: TaskRow): Unit  = {
    val query = Task.map(_.url === crawlerTask.url).length
    val  db = H2ConnectionPool.getConnection(crawlerTask.source)

    val count = Await.result(
      db.run(query.result),10 seconds
    )
    println(count)
    if(count>0) return

    //TaskRow(id: Int, url: String, source: String, `type`: String, status: String, prior: String)
    val insert = sqlu"INSERT INTO task(url, source,type,status,prior) VALUES (${crawlerTask.url},${crawlerTask.source},${crawlerTask.`type`},${crawlerTask.status},${crawlerTask.prior})"
    Await.result(
      db.run(insert).map{ res =>
        res match{
          case 1 => println("all good")
          case 0 => println("couldn't add record for some reason")
        }
      }.recover{
        case e:Exception =>println("Caught exception: "+e.getMessage)
      },10 seconds)
  }

  override def update(crawlerTask: TaskRow): Unit = {
    val  db = H2ConnectionPool.getConnection(crawlerTask.source)
    val query = for {task <- Task if task.url === crawlerTask.url} yield task.status
    Await.result(db.run(query.update(crawlerTask.status)).map{res =>
      println(res)
    },10 seconds)
  }

  override def delete(crawlerTask: TaskRow): Unit = {

  }

  override def find(crawlerTask: TaskRow): Unit = {

  }

  override def findByPrior(dbName:String): Option[TaskRow] = {
    val  db = H2ConnectionPool.getConnection(dbName)
    val query = Task.filter(_.prior === "0").sortBy(_.prior.desc)
//    Await.result(db.run(query),10 seconds)
    None
  }
}