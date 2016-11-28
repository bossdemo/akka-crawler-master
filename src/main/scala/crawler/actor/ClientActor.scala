package crawler.actor

import akka.actor.{Actor, ActorLogging}
import crawler.service.DBService
import crawler.tables.Tables._

/**
  * Created by tongtao.zhu on 2016/11/24.
  */
class ClientActor extends Actor with ActorLogging{
  val dbService = DBService.dbService

  override def receive = {
    case task: TaskRow => {

    }
    case dbName: String =>{
      log.info("开始分配task...")
      val tasks = dbService.findByPrior(dbName)
      log.info(s"${tasks.length}")
    }
  }
}
