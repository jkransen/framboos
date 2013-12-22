package framboos;

import static framboos.FilePaths.*;

public class PwmPin extends GpioPin {
	
	public PwmPin(int pinNumber) {
		super(pinNumber, Direction.PWM);
		setValue(0);
	}
	
	public PwmPin(String pinName) {
		super(getPinNumber(pinName), Direction.PWM, true);
		setValue(0);
	}
	
	/**
	 * Set the value in integer, from 0.0 (all off) to 1023 (all on)
	 * @param value
	 */
	public void setValue(int value) {
		if (value < 0 || value > 1023) {
			throw new RuntimeException("Invalid value for PWM: "+value);
		}
		if (!isClosing) {
			System.out.println("Writing to PWM: "+value);
			writeFile(getValuePath(pinNumber), Integer.toString(value));
		}
	}
	
	/**
	 * Set the value in float, from 0.0 (all off) to 1.0 (all on, like 1023)
	 * @param value
	 */
	public void setValue(float value) {
		setValue((int)(value * 1023));
	}
	
	/**
	 * We can use it as a regular out pin
	 * @param value
	 */
	public void setValue(boolean value) {
		setValue(value ? 1023 : 0);
	}
	
	@Override
	public void close() {
		setValue(false);
		super.close();
	}
}
