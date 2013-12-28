package framboos.actor

import akka.actor._
import framboos._

class OutPinActor(pinNumber: Int) extends Actor {
  
  val outPin = OutPin(pinNumber)
  
  def receive: Receive = {
    case NewValue(value) => {
      outPin.setValue(value)
    }
  }
}