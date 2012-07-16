package framboos;

public class ReverseInPin extends GpioPin {

	public ReverseInPin(int pinNumber) {
		super(pinNumber, Direction.IN);
	}

	@Override
	public boolean getValue() {
		return !super.getValue();
	}
}
