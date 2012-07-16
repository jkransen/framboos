package framboos.algorithm;

import framboos.OutPin;

public class Caterpillar extends NineLedsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		boolean wasOne = false;

		while (true) {
			for (OutPin pin : pins) {
				pin.setValue(!wasOne);
				pause();
			}
			wasOne = !wasOne;
		}
	}
}
