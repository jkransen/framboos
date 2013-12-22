package framboos;

public class ReverseInPin extends InPin {

	public ReverseInPin(int pinNumber) {
		super(pinNumber);
	}

	public ReverseInPin(String pinName) {
		super(pinName);
	}

	@Override
	public boolean getValue() {
		return !super.getValue();
	}
}
