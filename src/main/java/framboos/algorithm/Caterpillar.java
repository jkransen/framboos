package framboos.algorithm;

import framboos.OutPin;
import framboos.algorithm.util.Timer;

public class Caterpillar extends NineLedsTwoButtonsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		boolean wasOne = false;

		while (true) {
			for (OutPin pin : pins) {
				pin.setValue(!wasOne);
				Timer.pause();
			}
			wasOne = !wasOne;
		}
	}
}
