package framboos.actor

import akka.actor._

sealed trait Incoming
/** Sender of the message is listener to be added */
case class AddListener(listener: ActorRef) extends Incoming
/** Sender of the message is listener to be removed */
case class RemoveListener(listener: ActorRef) extends Incoming

/** Can be used for Incoming and Outgoing */
case class NewValue(value: Boolean)
