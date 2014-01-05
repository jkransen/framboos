Java GPIO access
----------------

Framboos is a small Java wrapper around the default GPIO driver on Linux boards like Raspberry Pi 
and BeagleBoard. It does not depend on any additional native libraries. The program that uses it 
must be run as root in order to access the driver files under /sys/class/gpio/. It supports input 
and output pins, as well as serial (UART) communication. It does not support PWM, SPI or I2C. If 
you need any of that, use [Pi4J](http://pi4j.com) instead, which includes native code and the 
wiringPi library. This is meant to be a pure Java (Scala) implementation that can be added as a 
simple Maven dependency to any Java project.

Java code example to use an input pin:

    import framboos.InPin;
    InPin button = new InPin(8);
    boolean isButtonPressed = button.getValue();
    button.close();
  
Java code example to use an output pin:

    import framboos.OutPin;
    OutPin led = new Outpin(0);
    led.setValue(true);
    led.close();

Scala code example to use an input pin:

    import framboos.InPin
    val button = InPin(8)
    val isButtonPressed = button.value
    button.close
  
Scala code example to use an output pin:

    import framboos.OutPin
    val led = Outpin(0)
    led.setValue(true)
    led.close

It is important to close pins that are created, so that they are released for other programs.

Extensions for Akka and asynchronous programming
------------------------------------------------

Instead of creating and polling an input pin yourself, you can create an Observer that will run 
a function any time the pin value changes. 

Sample scala code to observe an input pin:

    import framboos.async.ObservableInPin
    val inPin = ObservableInPin(8)
    inPin.subscribe(newValue => println(s"New value $newValue")

In an Akka application, you can create Actors that create GPIO/UART output based on received 
Akka messages, or that send Akka messages themselves on changed GPIO/UART input. 

WireUp is a provided sample Akka actor, that shows what the InPinActor, OutPinActor and 
SerialPortActor can do:

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
        case ReceiveMessage(message: String) => {
            outPin0 ! NewValue(true)
            serialPort ! SendMessage(s"Received your message: $message\n")
        }
    }

InPinActor will send NewValue messages, containing a boolean of the new value. SerialPortActor 
will send ReceiveMessage messages for every line of text received on the serial port (which here 
is /dev/ttyAMA0, the default serial Rx/Tx pins on the GPIO header of the Raspberry Pi). 

OutPin will accept NewValue(boolean) messages, and set the output pin accordingly. SerialPortActor 
acccepts SendMessage messages as well, and will send the containing String over the serial line. 

Wired up like this, incoming serial input will be sent back with a prefix, and make the LED light 
up. Pressing the button will light the LED as well, and send a text over the serial line. Releasing 
the button will make the LED go off (even if it was triggered by incoming serial text).

Pin assignment
--------------

When passing an integer to a constructor it will default to the wiringPi layout. When passing a 
String representation (i.e. "GPIO2_1" for BeagleBone) it will directly address the pin, bypassing 
the wiringPi numbering. Note that this library does not depend on or use wiringPi under the hood, 
but by default it uses its numbering scheme instead of e.g. the native Broadcom numbers in the 
case of Raspberry Pi.

GPIO pins in the numbering used by wiringPi: 


|   |   |GND|   |   | 1 |   | 4 | 5 |   | 6 | 10|   |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
|   | 8 | 9 | 7 |   | 0 | 2 | 3 |   |   |   |   |   |

The same diagram as seen when the Pi is held upside down, with the GPIO headers towards you:

|   |   |   |   |   | 3 | 2 | 0 |   | 7 | 9 | 8 |   |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
|   | 10| 6 |   | 5 | 4 |   | 1 |   |   |GND|   |   |

For simplicity sake, the assignments of the other pins are left out.

Provided sample code
--------------------

Next to the wrapper classes, I made some classes that make connected LEDs  light up in different 
patterns. For this to work, you need to connect the LEDs like I did. I use the Starter Kit from 
SK Pang, which contains a couple of LEDs, 2 buttons and 10 wires. At first I used 9 LEDs minus 
the number of buttons used, as the Starter Kit has limited wires, but later I decided to wire all 
9 LEDs with custom wires, so there is no need to rewire when switching algorithms.

For the Nine LED algorithms, connect pins 0 to 7 in that order to the LEDs, and 10 as ninth.
Add button 1 to pin 8, and button 2 to pin 9. These pins have pull-up resistors to allow simple 
buttons that short-circuit when pressed.

Usage
-----

To control the pins directly from your own code, simply add this dependency to your pom.xml:

    <dependency>
        <groupId>framboos</groupId>
        <artifactId>framboos</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>

And run this from the root of the framboos project:

    mvn install

This will create a file target/framboos-X.Y.Z.jar and install it into your local repository.

To run one of the patterns, run the application itself inside the jar:

    java -jar target/framboos-X.Y.Z.jar

This will pick a random pattern to light up the LEDs. You can also specify the pattern you want 
to run, like this:

    java -jar target/framboos-X.Y.Z.jar caterpillar

For a complete list of available pattern algorithms, examine this file:

    META-INF/services/framboos.algorithm.Algorithm

Wiring notes
------------

Please do use the resistors that come with the Starter Kit (or your own resistors if you don't 
use that kit) when connecting LEDs, or the current will fry them. Connect them serially, which 
means like this: connect one leg of the resistor to the - (minus) bottom line of the breadboard, 
where you also connected the ground of the Raspberry Pi. Connect the other side of the resistor 
with the short leg of the LED. Connect the long leg of the LED to whatever port you
connect it to.
