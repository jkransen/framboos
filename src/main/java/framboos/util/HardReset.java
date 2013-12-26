package framboos.util;

import java.io.FileOutputStream;
import java.io.IOException;

import framboos.GpioPin;

public class HardReset {
	static final int [] mappedPins = GpioPin.mappedPins();
	
	public static boolean hardResetPin(int pinNumber) {
		if ((pinNumber < 0) || (pinNumber > 16))
			return false;
		try {
			pinNumber = mappedPins[pinNumber];
			String valueString = Integer.toString(pinNumber);
			FileOutputStream fos = new FileOutputStream(GpioPin.unexportPath());
			fos.write(valueString.getBytes());
			fos.close();
		} catch (IOException e) {			
			return false;				
		}
		return true;
	}
}
