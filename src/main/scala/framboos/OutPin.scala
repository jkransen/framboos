package framboos;

import GpioPin._

class OutPin(pinNumber: Int, isDirect: Boolean) extends GpioPin(pinNumber, Out, isDirect) {
  def this(pinNumber: Int) = this(mappedPins(pinNumber), isDirect = false)
  def this(pinName: String) = this(getPinNumber(pinName), isDirect = true)

  // initially set pin to off
  setValue(false)

  def setValue(isOne: Boolean) {
    if (!isClosing) {
      writeFile(valuePath(pinNumber), if (isOne) "1" else "0")
    }
  }

  override def close {
    setValue(false)
    super.close
  }
}

object OutPin {
  def apply(pinNumber: Int) = new OutPin(pinNumber)
}
