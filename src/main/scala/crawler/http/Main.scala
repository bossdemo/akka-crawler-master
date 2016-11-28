package crawler.tables.http

import akka.http.scaladsl.Http

import scala.util.{Failure, Success}

/**
  * Created by tongtao.zhu on 2016/11/23.
  */
object Main extends ApiRoutes{

  def main(args: Array[String]) {
    startServer(5000)
  }

  def startServer(port: Int): Unit ={
    val api = routes
    val bindingFuture = Http().bindAndHandle(handler = api, interface = "localhost", port = port) map { binding =>
      println(s"REST interface bound to ${binding.localAddress}") } recover { case ex =>
      println(s"REST interface could not bind to $host:$port", ex.getMessage)
    }
    bindingFuture.onComplete {
      case Success(binding) =>
        println(binding)
      case Failure(e) =>
        println(e)
    }
  }
}