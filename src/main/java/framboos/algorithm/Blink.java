package framboos.algorithm;

import framboos.OutPin;
import framboos.algorithm.util.Timer;

public class Blink extends NineLedsTwoButtonsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		boolean isOne = false;
		
		while (true) {
			for (OutPin pin : pins) {
				pin.setValue(isOne);
			}
			isOne = !isOne;
			Timer.pause();
		}
	}
}
