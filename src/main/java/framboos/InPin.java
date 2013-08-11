package framboos;

public class InPin extends GpioPin {
		
	public InPin(int pinNumber) {
		super(pinNumber, Direction.IN);
	}

	public InPin(String pinName) {
		super(getPinNumber(pinName), Direction.IN, true);
	}

}
