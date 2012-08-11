package framboos.algorithm;

import framboos.OutPin;
import framboos.algorithm.util.Timer;

public class ButtonChangeDirection extends NineLedsTwoButtonsAlgorithm {
	
	int previous = 0;
	boolean goesUp = true;

	@Override
	public void lightLeds(OutPin[] leds) {
		while (true) {
			int current = goesUp ? previous + 1 : previous - 1;
			if (current < 0) {
				current += leds.length;
			}
			if (current >= leds.length) {
				current -= leds.length;
			}
			leds[previous].setValue(false);
			leds[current].setValue(true);
			previous = current;
			Timer.pause();
		}
	}

	@Override
	public void handleButton1Pressed() {
		goesUp = !goesUp;
	}
}
