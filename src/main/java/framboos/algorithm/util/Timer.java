package framboos.algorithm.util;

public class Timer {

	public static void pause() {
		pause(100);  // default .1 sec
	}
	
	public static void pause(int ms) {
		try {
			Thread.sleep(Math.max(0, ms));
		} catch (InterruptedException e) {
		}
	}
}
