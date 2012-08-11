package framboos.algorithm;

import framboos.OutPin;
import framboos.algorithm.util.Timer;

public class ChangeOnButton extends NineLedsTwoButtonsAlgorithm {

	@Override
	public void lightLeds(OutPin[] pins) {
		for (int i = 0; i < pins.length; i++) {
			pins[i].setValue(i % 2 == 0);
		}
		while (true) {
			// no changes except on button pressed
			Timer.pause();
		}
	}

	@Override
	public void handleButton1Pressed() {
		for (OutPin pin : getLeds()) {
			pin.setValue(!pin.getValue());
		}
	}
}
