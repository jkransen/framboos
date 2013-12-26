package framboos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex._;

class GpioPin(pinNumber: Int, direction: Direction, isDirect: Boolean) {
    def this(pinNumber: Int, direction: Direction) = this(pinNumber, direction, false)

    	import GpioPin._

	// REV 1:
	// val mappedPins: Seq[Int] = Seq(17, 18, 21, 22, 23, 24, 25, 4, 0, 1, 8, 7, 10, 9, 11, 14, 15)
	// REV 2:
	val mappedPins: Seq[Int] = Seq(17, 18, 27, 22, 23, 24, 25, 4, 2, 3, 8, 7, 10, 9, 11, 14, 15)

	val mappedPinNumber = if (isDirect) pinNumber else mappedPins(pinNumber)

	var isClosing = false;

	writeFile(exportPath, Integer.toString(this.pinNumber));
	writeFile(directionPath(pinNumber), direction.value);

	def value: Boolean = {
		if (isClosing) {
			false;
		}
		try {
			val fis = new FileInputStream(valuePath(pinNumber));
			val value = (fis.read() == '1');
			fis.close();
			value;
		} catch {
		  case e: IOException => {
			if (e.getMessage().contains("Permission Denied")) {
				throw new RuntimeException("Permission denied to GPIO file: " + e.getMessage());
			}
			else {
				throw new RuntimeException("Could not read from GPIO file: " + e.getMessage());
			}
		  }
		}
	}

	def close {
		isClosing = true;
		writeFile(unexportPath, Integer.toString(pinNumber));
	}

	def writeFile(fileName: String, value: String) {
		try {
			// check for permission
			val fos = new FileOutputStream(fileName);
			fos.write(value.getBytes());
			fos.close();
		} catch {
		  case e: IOException => {
			if (e.getMessage().contains("Permission denied")) {
				throw new RuntimeException("Permission denied to GPIO file: "
						+ e.getMessage());
			} else if (e.getMessage().contains("busy")) {
				System.out.println("GPIO is already exported, continuing");
				return;
			}
			throw new RuntimeException("Could not write to GPIO file: "
					+ e.getMessage());
		  }
		}
	}
}

sealed trait Direction {
  val value: String
}
case class In extends Direction {
  val value = "in" 
}
case class Out extends Direction {
  val value = "out"
}

object GpioPin {
	val pinPattern: Pattern = Pattern.compile("(gpio)([0-9])_([0-9]*)", Pattern.CASE_INSENSITIVE)
	val pinPatternAlt: Pattern = Pattern.compile("(gpio)([0-9]*)", Pattern.CASE_INSENSITIVE)
	val pinPatternNumber: Pattern = Pattern.compile("([0-9]*)")

	val gpioPath = "/sys/class/gpio";
	val exportPath = gpioPath + "/export";
	val unexportPath = gpioPath + "/unexport";
	def devicePath(num: Int) = gpioPath + (s"/gpio%d", num);
	def directionPath(num: Int) = devicePath(num) + "/direction";
	def valuePath(num: Int) = devicePath(num) + "/value";
	
	def getPinNumber(pinName: String): Int = {
		val matcher = pinPattern.matcher(pinName);
		if (matcher.find) {
			Integer.parseInt(matcher.group(2)) * 32 + Integer.parseInt(matcher.group(3))
		} else {
			val matcherAlt = pinPatternAlt.matcher(pinName);
			if (matcherAlt.find) {
				Integer.parseInt(matcher.group(2))
			} else {
				val matcherAlt2 = pinPatternNumber.matcher(pinName)
				if (matcherAlt2.find) {
					Integer.parseInt(matcherAlt2.group(1))
				} else {
					throw new RuntimeException("Could not match " + pinName + ". As a valid gpio pinout number")
				}
			}
		}
	}
}
