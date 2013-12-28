package framboos;
import GpioPin._

class ReverseInPin(pinNumber: Int, isDirect: Boolean) extends InPin(pinNumber, isDirect) {

  def this(pinNumber: Int) = this(mappedPins(pinNumber), isDirect = false)
  def this(pinName: String) = this(getPinNumber(pinName), isDirect = true)

  override def value = !super.value
}

object ReverseInPin {
  def apply(pinNumber: Int) = new ReverseInPin(pinNumber)
}