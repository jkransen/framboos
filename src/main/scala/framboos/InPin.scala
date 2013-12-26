package framboos;

class InPin(pinNumber: Int) extends GpioPin(pinNumber, In) {
	def this(pinName: String) {
		super(getPinNumber(pinName), Direction.IN, true);
	}

}
