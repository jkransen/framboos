package framboos.algorithm;

import framboos.OutPin;
import framboos.algorithm.util.Timer;

public class GoRight extends NineLedsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		int i = 0;

		while (true) {
			for (int j = 0; j < pins.length; j++) {
				OutPin pin = pins[j];
				pin.setValue(j % 3 == i);
			}
			i = (i + 1) % 3;
			Timer.pause();
		}
	}
}
