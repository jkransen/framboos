package framboos;

import static framboos.FilePaths.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class GpioPin {

	private static final int [] mappedPins = {17, 18, 21, 22, 23, 24, 25, 4, 0, 1, 8, 7, 10, 9, 11, 14, 15};
	
	protected final int pinNumber;
	
	public GpioPin(int pinNumber, Direction direction) {
		this.pinNumber = mappedPins[pinNumber];
		writeFile(getExportPath(), Integer.toString(this.pinNumber));
		writeFile(getDirectionPath(this.pinNumber), direction.getValue());
	}
	
	public boolean getValue() {
		try {
			FileInputStream fis = new FileInputStream(getValuePath(pinNumber));
			boolean value = (fis.read() == '1');
			fis.close();
			return value;
		} catch (IOException e) {
			throw new RuntimeException("Could not read from GPIO file: " + e.getMessage());
		}
	}
	
	public void close() {
		writeFile(getUnexportPath(), Integer.toString(pinNumber));
	}
	
	protected void writeFile(String fileName, String value) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(value.getBytes());
			fos.close();
		} catch (IOException e) {
			throw new RuntimeException("Could not write to GPIO file: " + e.getMessage());
		}
	}
	
	public enum Direction {
		IN("in"), 
		OUT("out"),
		PWM("pwm");
		
		private String value;

		Direction(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
}
