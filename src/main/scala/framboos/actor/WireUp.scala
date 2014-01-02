package framboos.actor

import akka.actor._
import SerialPortActor._
import CommonMessages._

class WireUp extends Actor {

  val inPin8 = context.actorOf(Props(new InPinActor(8)), name = "inPin8")

  inPin8 ! AddListener(self)

  val outPin0 = context.actorOf(Props(new OutPinActor(0)), name = "outPin0")

  val serialPort = context.actorOf(Props(new SerialPortActor("ttyAMA0")), name = "serialPort")
  
  serialPort ! AddListener(self)

  def receive: Receive = {
    case NewValue(value: Boolean) => {
      outPin0 ! NewValue(value)
      val pressed = if (value) "pressed" else "released"
      serialPort ! SendMessage(s"Button $pressed at ${System.currentTimeMillis}\n")
    }
    case SerialPortActor.ReceiveMessage(message: String) => {
      outPin0 ! NewValue(true)
      serialPort ! SendMessage(s"Received your message: $message\n")
    }
  }
}
