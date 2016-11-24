package http

import actor.{ClientActor, TaskDBActor}
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.server._
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import serializers.MyResource
import tables.Tables.TaskRow

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by tongtao.zhu on 2016/11/8.
  */
trait ApiRoutes extends MyResource{
  val host = "127.0.0.1"
  val port = "5001"

  implicit val system = ActorSystem("crawler-master")
  implicit val materializer = ActorMaterializer()

  implicit val taskDBRef = system.actorOf(Props[TaskDBActor],name = "taskDBRef")

  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  def routes: Route = pathPrefix("crawler") {
    path("ping") {
      post{
        entity(as[TaskRow]) { task =>
          complete{
            val future = Future{taskDBRef ? task}
            future onComplete {
              case Success(success) => {
                //完成之后，发送消息通知client可以去url转发了
                val clientActorRef = system.actorOf(Props[ClientActor], name = "clientActor")
                clientActorRef ! task
              }
              case Failure(failure) =>
            }
            task
          }
        }
      }
    }~
    path("test"){
      get{
        parameter('url.as[String]){ url =>
          complete {
            val future = Future{taskDBRef ? url}
            future onComplete {
              case Success(success) => println(111)
              case Failure(failure) =>
            }
            url
          }
        }
      }
    }
  }
}
