package actor

import akka.actor.{Actor, ActorLogging}
import bean.H2CrawlerTaskRepository
import service.DBService
import tables.Tables.TaskRow

/**
  * Created by tongtao.zhu on 2016/11/23.
  */
class TaskDBActor extends Actor with ActorLogging{
  val dbService = new DBService()
  val h2 = new H2CrawlerTaskRepository()
  dbService.crawlerTaskRepository = h2


  override def receive = {
    case task: TaskRow => {
      dbService.create(task)
    }
    case url: String => {
      Thread.sleep(1000)
      dbService.create(TaskRow(url,"test","test","test","test"))
    }
  }
}
