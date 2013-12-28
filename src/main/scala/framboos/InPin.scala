package framboos

import GpioPin._

class InPin(pinNumber: Int, isDirect: Boolean) extends GpioPin(pinNumber, In, isDirect) {
  def this(pinNumber: Int) = this(mappedPins(pinNumber), isDirect = false)
  def this(pinName: String) = this(getPinNumber(pinName), isDirect = true)
}

object InPin {
  def apply(pinNumber: Int) = new InPin(pinNumber)
}