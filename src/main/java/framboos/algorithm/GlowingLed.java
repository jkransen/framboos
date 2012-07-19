package framboos.algorithm;

import framboos.PwmPin;
import framboos.algorithm.util.Timer;

public class GlowingLed implements Algorithm {
	
	PwmPin led;

	public void setUp() {
		led = new PwmPin(1);

	}

	public void execute() {
		while (true) {
			for (int i=0; i < 1024; i+=16) {
				led.setValue(i);
				Timer.pause(10);
			}

			for (int i=1023; i >= 0; i-=16) {
				led.setValue(i);
				Timer.pause(10);
			}
			
//			Timer.pause(500);
		}
	}

	public void tearDown() {
		led.close();
	}

}
