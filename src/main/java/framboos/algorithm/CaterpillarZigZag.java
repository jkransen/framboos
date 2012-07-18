package framboos.algorithm;

import framboos.OutPin;

public class CaterpillarZigZag extends NineLedsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		boolean goesUp = true;
		boolean wasOne = false;

		while (true) {
			if (goesUp) {
				for (int i = 0; i < pins.length; i++) {
					pins[i].setValue(!wasOne);
					pause();
				}
				wasOne = !wasOne;
			}
			else {
				for (int i = pins.length - 1; i >= 0; i--) {
					pins[i].setValue(!wasOne);
					pause();
				}
				wasOne = !wasOne;
			}
			if (!wasOne) {
				goesUp = !goesUp;
			}
		}
	}
}
