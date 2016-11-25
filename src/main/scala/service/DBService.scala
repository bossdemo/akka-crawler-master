package service

import bean.{H2CrawlerTaskRepository, CrawlerTaskRepository}
import de.heikoseeberger.akkahttpjson4s.Json4sSupport.ShouldWritePretty.{True, False}
import org.slf4j.LoggerFactory
import tables.Tables.TaskRow

/**
  * Created by tongtao.zhu on 2016/11/23.
  * 负责将task存储到数据库
  */
class DBService {
  val log = LoggerFactory.getLogger(DBService.getClass)
  var crawlerTaskRepository: CrawlerTaskRepository = _

  def create(crawlerTask: TaskRow): Boolean = {
    //在插入之前先查找是否已经存在于表中
    val tasks = crawlerTaskRepository.find(crawlerTask)
    if (tasks.length >0){
      log.info(s"已经存在${crawlerTask.url}任务")
      return false
    }
    crawlerTaskRepository.create(crawlerTask)
    true
  }

  def find(crawlerTask: TaskRow) = {

  }

  def findByPrior(dbName: String): Vector[TaskRow] = {
    crawlerTaskRepository.findByPrior(dbName)
  }

  def update(crawlerTask: TaskRow) = {
    crawlerTaskRepository.update(crawlerTask)
  }
}

object DBService{
  val dbService = new DBService()
  val h2 = new H2CrawlerTaskRepository()
  dbService.crawlerTaskRepository = h2
}
