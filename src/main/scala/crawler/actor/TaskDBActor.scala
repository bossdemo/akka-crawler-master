package crawler.actor

import akka.actor.{Props, Actor, ActorLogging}
import crawler.service.DBService
import crawler.tables.Tables._

/**
  * Created by tongtao.zhu on 2016/11/23.
  */
class TaskDBActor extends Actor with ActorLogging{
  val dbService = DBService.dbService

  override def receive = {
    case task: TaskRow => {
      log.info(s"start store task：${task}")
      val flag = dbService.create(task)
      if (flag){
        //成功之后，创建一个actor，将task分发出去
        val clientActorRef = context.actorOf(Props[ClientActor])
        clientActorRef ! task.source
      }
    }
    case url: String => {
      Thread.sleep(1000)
      dbService.create(TaskRow(url,"test","test","0","10"))
    }
  }
}
