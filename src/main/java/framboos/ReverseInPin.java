package framboos;

public class ReverseInPin extends InPin {

	public ReverseInPin(int pinNumber) {
		super(pinNumber);
	}

	@Override
	public boolean getValue() {
		return !super.getValue();
	}
}
