package framboos.actor

import akka.actor._
import framboos._
import scala.concurrent._
import scala.async.Async.{ async, await }
import ExecutionContext.Implicits.global
import purejavacomm._
import java.io._
import CommonMessages._

object SerialPortActor {
  def props(portName: String): Props = Props(new SerialPortActor(portName))

  /** Message to be sent over serial connection */
  case class SendMessage(message: String) extends Incoming

  /** Message received over serial connection */
  case class ReceiveMessage(message: String) extends Outgoing
}

class SerialPortActor(portName: String) extends Actor with ActorLogging {

  import SerialPortActor._

  var listeners = Set.empty[ActorRef]

  def receive = connecting

  connect
  
  def connect = async {
    findPort(portName) match {
      case Some(port) => {
        log.info(s"Port found: $portName")
        val in = new BufferedReader(new InputStreamReader(port.getInputStream))

        port.addEventListener(new SerialPortEventListener {
          def serialEvent(event: SerialPortEvent) {
            if (event.getEventType == SerialPortEvent.DATA_AVAILABLE) {
              log.debug("New serial input")
              while (in.ready) {
                val nextLine = in.readLine
                log.debug(s"Receiving message: $nextLine")
                listeners foreach { _ ! ReceiveMessage(nextLine) }
              }
            }
          }
        })

        val out = new BufferedWriter(new OutputStreamWriter(port.getOutputStream))
        out.write("Hello from SerialPortActor\n")
        out.flush
        context.become(connected(in, out), true)
      }
      case None => {
        log.error(s"Could not find port $portName")
      }
    }
  }

  def findPort(portName: String): Option[SerialPort] = {
    import java.util._
    def findPort0(ports: Enumeration[CommPortIdentifier]): Option[SerialPort] = {
      if (ports.hasMoreElements) {
        val nextPortId: CommPortIdentifier = ports.nextElement
        if (nextPortId.getName().equalsIgnoreCase(portName)) {
          Some(openPort(nextPortId))
        } else {
          findPort0(ports)
        }
      } else {
        None
      }
    }
    findPort0(CommPortIdentifier.getPortIdentifiers.asInstanceOf[Enumeration[CommPortIdentifier]])
  }

  def openPort(portId: CommPortIdentifier): SerialPort = {
    val port: SerialPort = portId.open("SerialPortActor", 1000).asInstanceOf[SerialPort]
    port.notifyOnDataAvailable(true)
    port.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN + SerialPort.FLOWCONTROL_XONXOFF_OUT)
    port
  }

  import SerialPortActor._

  val connecting: Receive = {
    case AddListener(listener: ActorRef) => {
      listeners = listeners + listener
    }
    case RemoveListener(listener: ActorRef) => {
      listeners = listeners - listener
    }
    case SendMessage(message: String) => async {
      log.warning(s"Not connected, could not deliver message: $message")
    }
  }

  def connected(in: BufferedReader, out: BufferedWriter): Receive = {
    case AddListener(listener: ActorRef) => {
      listeners = listeners + listener
    }
    case RemoveListener(listener: ActorRef) => {
      listeners = listeners - listener
    }
    case SendMessage(message: String) => async {
      log.debug(s"Sending message: $message")
      out.write(message + '\n')
      out.flush
    }
  }
}
