package framboos.algorithm;

import framboos.OutPin;
import framboos.algorithm.util.Timer;

public class RandomOne extends NineLedsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		int previous = 0;
		while (true) {
			int current = (int)(Math.random() * pins.length);
			pins[previous].setValue(false);
			pins[current].setValue(true);
			previous = current;
			Timer.pause();
		}
	}

}
