package actor

import akka.actor.{Actor, ActorLogging}
import tables.Tables.TaskRow

/**
  * Created by tongtao.zhu on 2016/11/24.
  */
class ClientActor extends Actor with ActorLogging{
  override def receive = {
    case task: TaskRow => {

    }
  }
}
