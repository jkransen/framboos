package framboos.algorithm;

import framboos.OutPin;
import framboos.algorithm.util.Timer;

public class RandomAll extends NineLedsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		while (true) {
			for (OutPin pin : pins) {
				pin.setValue(Math.random() < 0.5);
			}
			Timer.pause();
		}
	}

}
