package framboos.algorithm;

import framboos.OutPin;

public class Blink extends NineLedsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		boolean isOne = false;
		
		while (true) {
			for (OutPin pin : pins) {
				pin.setValue(isOne);
			}
			isOne = !isOne;
			pause();
		}
	}

}
