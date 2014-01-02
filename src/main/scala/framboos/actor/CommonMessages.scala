package framboos.actor

import akka.actor._

object CommonMessages {

  trait Incoming
  
  /** listener actor to be added */
  case class AddListener(listener: ActorRef)
  /** listener actor to be removed */
  case class RemoveListener(listener: ActorRef)

  trait Outgoing

  case class NewValue(value: Boolean)
}
