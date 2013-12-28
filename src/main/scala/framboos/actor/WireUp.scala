package framboos.actor

import akka.actor._

class WireUp extends Actor {

  val inPin8 = context.actorOf(Props(new InPinActor(8)), name = "inPin8")

  inPin8 ! AddListener(self)

  val outPin0 = context.actorOf(Props(new OutPinActor(0)), name = "outPin0")

  def receive: Receive = {
    case NewValue(value: Boolean) => {
      outPin0 ! NewValue(value)
    }
  }
}