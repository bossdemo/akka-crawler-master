package crawler.store

import crawler.tables.Tables._
import org.slf4j.LoggerFactory
import scala.concurrent.duration._
import scala.concurrent._
import crawler.tables.Tables.profile.api._
import ExecutionContext.Implicits.global

/**
  * Created by tongtao.zhu on 2016/11/23.
  */

case class Task(url: String, source: String, `type`: String, status: String, prior: String)

trait CrawlerTaskRepository{
  def create(crawlerTask: TaskRow)
  def find(crawlerTask: TaskRow): Vector[TaskRow]
  def update(crawlerTask: TaskRow)
  def delete(crawlerTask: TaskRow)
  def findByPrior(dbName:String): Vector[TaskRow]
}

class H2CrawlerTaskRepository extends CrawlerTaskRepository{
  val log = LoggerFactory.getLogger("h2CrawlerTaskRepository")

  override def create(crawlerTask: TaskRow): Unit  = {
    val  db = H2ConnectionPool.getConnection(crawlerTask.source)

    val query = Task.filter(_.url === crawlerTask.url).sortBy(_.prior.desc).length
    val task = Await.result(db.run(query.result), 60 seconds)
    log.info(s"${crawlerTask.url}总数为${task}")
    if(task>0) return

    //TaskRow(id: Int, url: String, source: String, `type`: String, status: String, prior: String)
    val insert = sqlu"INSERT INTO task(url, source,type,status,prior) VALUES (${crawlerTask.url},${crawlerTask.source},${crawlerTask.`type`},${crawlerTask.status},${crawlerTask.prior})"
    Await.result(
      db.run(insert).map{ res =>
        res match{
          case 1 => log.info(s"url=${crawlerTask.url}存储成功")
          case 0 => log.error(s"url=${crawlerTask.url}存储失败")
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

  override def find(crawlerTask: TaskRow): Vector[TaskRow] = {
    val  db = H2ConnectionPool.getConnection(crawlerTask.source)
    val query = Task.filter(_.url === crawlerTask.url)
    Await.result(db.run(query.result),10 seconds).to
  }

  override def findByPrior(dbName:String): Vector[TaskRow] = {
    val  db = H2ConnectionPool.getConnection(dbName)
    val query = Task.filter(_.status === "0").sortBy(_.prior.desc).take(100)
    val tasks = Await.result(db.run(query.result), 60 seconds)
    log.info(s"有${tasks.length}个任务被分配")
    tasks.toVector
  }
}