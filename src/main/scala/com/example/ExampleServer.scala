package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

object ExampleServer extends ExampleRoutes with App {
  implicit val system = ActorSystem("ExampleServer")
  import system.dispatcher
  implicit val materializer = ActorMaterializer()

  // settings about bind host/port
  // could be read from application.conf (via system.settings):
  val interface = "0.0.0.0"
  val port = 8081

 // def main(args: Array[String]): Unit = {
    // Start the Akka HTTP server!
    // Using the mixed-in testRoutes (we could mix in more routes here)
    val bindingFuture = Http().bindAndHandle(testRoutes, interface, port)

    // Wait until user happy with the test-run and shut down the server afterwards.
    println(s"Server online at  http://$interface:$port/\nPress RETURN to stop...")
    Console.readLine()
    bindingFuture.flatMap(_.unbind()).onComplete(_ ⇒ system.shutdown())
  //}
}
