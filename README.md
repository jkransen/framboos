Java GPIO access
----------------

Framboos is a small Java wrapper around the GPIO driver under /sys/class/gpio. The program that uses it must be run as root in order to access these files. It supports in and out pins. It does not support PWM, SPI, I2C. If you need any of that, use [Pi4J](http://pi4j.com) instead, which includes native libraries. This is meant to be a pure Java implementation that can be added as a simple dependency to any Java project.

Java code example to use an input pin:

    InPin button = new InPin(8);
    boolean isButtonPressed = button.getValue();
    button.close();
  
Java code example to use an output pin:

    OutPin led = new Outpin(0);
    led.setValue(true);
    led.close();

Scala code example to use an input pin:

    val button = InPin(8)
    val isButtonPressed = button.value
    button.close
  
Scala code example to use an output pin:

    val led = Outpin(0)
    led.setValue(true)
    led.close

It is important to close pins that are created, so that they are released for other programs.

Extensions for Akka and asynchronous programming
------------------------------------------------

Instead of creating and polling an input pin yourself, you can create an Observable that will run a piece of code any time the pin value changes. 

Sample scala code to observe an input pin:

    val inPin = ObservableInPin(0)
    val subscription = inPin.subscribe(newValue => println(s"New value $newValue")

In an Akka application, you can create Actors that handle output based on received messages, or that send messages 
themselves on changed input. 

TODO show Akka samples, for now, take a look at WireUp

Pin assignment
--------------

When passing an INT to a constructor it will default to the wiringPi layout. 
When passing a STRING representation (i.e. GPIO2_1 for BeagleBone) it will direct address the pinout, bypassing wiringPi.

GPIO pins in the numbering used by wiringPi: 


|   |   |GND|   |   | 1 |   | 4 | 5 |   | 6 | 10|   |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
|   | 8 | 9 | 7 |   | 0 | 2 | 3 |   |   |   |   |   |

The same diagram as seen when the Pi is held upside down:

|   |   |   |   |   | 3 | 2 | 0 |   | 7 | 9 | 8 |   |
|---|---|---|---|---|---|---|---|---|---|---|---|---|
|   | 10| 6 |   | 5 | 4 |   | 1 |   |   |GND|   |   |

For simplicity sake, I left out the assignments of the other pins.

Provided sample code
--------------------

Next to the wrapper classes, I made some classes that make connected LEDs  light up in different 
patterns. For this to work, you need to connect the LEDs like I did. I use the Starter Kit from 
SK Pang, which contains a couple of LEDs, 2 buttons and 10 wires. At first I used 9 LEDs minus the 
number of buttons used, as the Starter Kit has limited wires, but later I decided to wire all 9 LEDs 
with custom wires, so there is no need to rewire when switching algorithms.

For the Nine LED algorithms, connect pins 0 to 7 in that order to the LEDs, and 10 as ninth.
Add button 1 to pin 8, and button 2 to pin 9. 

Usage
-----

If you want to control the pins directly from your own code, simply run:

    mvn install

This will create a file target/framboos-X.Y.Z.jar

To run one of the patterns, run the application itself:

    java -jar target/framboos-X.Y.Z.jar

This will pick a random pattern to light up the LEDs. You can also specify the pattern you want to run, like this:

    java -jar target/framboos-X.Y.Z.jar caterpillar

For a complete list of available pattern algorithms, examine this file:

    META-INF/services/framboos.algorithm.Algorithm

Wiring notes
------------

Please do use the resistors that come with the Starter Kit, or your own resistors if you don't use 
that kit. Connect them serially, which means like this: Connect one leg of the resistor to the -
(minus) bottom line of the breadboard, where you also connected the ground. Connect the other side
of the resistor with the short leg of the LED. Connect the long leg of the LED to whatever port you
connect it to.
