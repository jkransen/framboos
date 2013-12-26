package framboos;

import GpioPin._
import Direction._

class OutPin(pinNumber: Int, isDirect: Boolean) extends GpioPin(pinNumber, Out, isDirect) {

	def this(pinName: String) = this(getPinNumber(pinName), true)
	
	def setValue(isOne: Boolean) {
		if (!isClosing) {
			writeFile(valuePath(pinNumber), if (isOne) "1" else "0");
		}
	}

	setValue(false)

	override def close {
		setValue(false)
		super.close
	}
}
