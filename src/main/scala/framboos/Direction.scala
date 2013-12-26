package framboos;

public class FilePaths {

	private static final String GPIO_PATH = "/sys/class/gpio";
	private static final String EXPORT_PATH = GPIO_PATH + "/export";
	private static final String UNEXPORT_PATH = GPIO_PATH + "/unexport";
	private static final String DEVICE_PATH = GPIO_PATH + "/gpio%d";
	private static final String DIRECTION_PATH = DEVICE_PATH + "/direction";
	private static final String VALUE_PATH = DEVICE_PATH + "/value";
	
	public static String getGpioPath() {
		return GPIO_PATH;
	}

	public static String getExportPath() {
		return EXPORT_PATH;
	}
	
	public static String getUnexportPath() {
		return UNEXPORT_PATH;
	}
	
	public static String getDirectionPath(int pinNumber) {
		return String.format(DIRECTION_PATH, pinNumber);
	}
	
	public static String getValuePath(int pinNumber) {
		return String.format(VALUE_PATH, pinNumber);
	}
}
