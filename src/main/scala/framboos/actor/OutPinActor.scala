package framboos.actor

import akka.actor._
import framboos._
import CommonMessages._

object OutPinActor {
  def props(pinNumber: Int): Props = Props(new OutPinActor(pinNumber))
}

class OutPinActor(pinNumber: Int) extends Actor {
  
  val outPin = OutPin(pinNumber)
  
  def receive: Receive = {
    case NewValue(value) => {
      outPin.setValue(value)
    }
  }
}
