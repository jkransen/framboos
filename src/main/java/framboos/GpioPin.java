package framboos;

import static framboos.FilePaths.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.*;

public class GpioPin {
	// REV 1:
	// private static final int [] mappedPins = {17, 18, 21, 22, 23, 24, 25, 4, 0, 1, 8, 7, 10, 9, 11, 14, 15};
	// REV 2:
	private static final int [] mappedPins = {17, 18, 27, 22, 23, 24, 25, 4, 2, 3, 8, 7, 10, 9, 11, 14, 15};
	public static Pattern pinPattern = Pattern.compile("(gpio)([0-9])_([0-9]*)");
	public static Pattern pinPatternAlt = Pattern.compile("([0-9]*)");

	
	public static int[] getMappedPins() {
		return mappedPins;
	}
	
	protected final int pinNumber;
	
	protected boolean isClosing = false;
	
	public GpioPin(int pinNumber, Direction direction) {
		this.pinNumber = mappedPins[pinNumber];
		writeFile(getExportPath(), Integer.toString(this.pinNumber));
		writeFile(getDirectionPath(this.pinNumber), direction.getValue());
	}

	public GpioPin(int pinNumber, Direction direction, boolean direct) {
		/* Allow Override of the mapping for people to directly drive the GPIOs in the numbering they want */
		if(!direct) {
			this.pinNumber = mappedPins[pinNumber];
		} else {
			this.pinNumber = pinNumber;
		}
		
		writeFile(getExportPath(), Integer.toString(this.pinNumber));
		writeFile(getDirectionPath(this.pinNumber), direction.getValue());
	}
	
	public boolean getValue() {
		if (isClosing) {
			return false;
		}
		try {
			FileInputStream fis = new FileInputStream(getValuePath(pinNumber));
			boolean value = (fis.read() == '1');
			fis.close();
			return value;
		} catch (IOException e) {
			if(e.getMessage().contains("Permission Denied")) {
				throw new RuntimeException("Permission denied to GPIO file: " + e.getMessage());
			}
			throw new RuntimeException("Could not read from GPIO file: " + e.getMessage());
		}
	}
	
	public void close() {
		isClosing = true;
		writeFile(getUnexportPath(), Integer.toString(pinNumber));
	}
	
	protected void writeFile(String fileName, String value) {
		try {
			// check for permission
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(value.getBytes());
			fos.close();
		} catch (IOException e) {
			if(e.getMessage().contains("Permission denied")) {
				throw new RuntimeException("Permission denied to GPIO file: " + e.getMessage());
			} else if (e.getMessage().contains("busy")) {
				System.out.println("GPIO is already exported, continuing");
				return;
			}
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

        public static int getPinNumber(String pinName) {

                int pinNumber = -1;
                Matcher matcher = pinPattern.matcher(pinName);
                if(matcher.find()) {
                        pinNumber = Integer.parseInt(matcher.group(2))*32 + Integer.parseInt(matcher.group(3));
                } else {
                        matcher = pinPatternAlt.matcher(pinName);
                        if(matcher.find()) {
                                pinNumber = Integer.parseInt(matcher.group(1));
                        } else {
                                System.out.println("Could not match " + pinName + ". As a valid gpio pinout number");
                                System.exit(1);
                        }

                }
                return pinNumber;

        }

}
