package io.vamp.bootstrap

import akka.actor.{ Actor, ActorSystem, Props }
import io.vamp.common.akka.{ Bootstrap, ActorBootstrap ⇒ ActorBootstrapService }
import io.vamp.common.spi.ClassProvider

import scala.concurrent.{ ExecutionContext, Future }

class ActorBootstrap extends Bootstrap {

  private implicit lazy val system = ActorSystem("vamp")

  private lazy val bootstrap = ClassProvider.all[ActorBootstrapService].toList

  override def start() = bootstrap.foreach(_.start)

  override def stop() = {
    implicit val executionContext: ExecutionContext = system.dispatcher
    Future.sequence(bootstrap.reverse.map(_.stop)).foreach(_ ⇒ system.terminate())
  }

  system.actorOf(Props(new Actor {
    def receive = {
      case "reload" ⇒ bootstrap.reverse.foreach(_.restart)
      case _        ⇒
    }
  }), "vamp")
}
