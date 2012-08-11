package framboos.algorithm;

import framboos.OutPin;
import framboos.algorithm.util.Timer;

public class AllOn extends NineLedsTwoButtonsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		for (OutPin pin : pins) {
			pin.setValue(true);
		}
		
		while (true) {
			// no changes except on button pressed
			Timer.pause();
		}
	}
}
