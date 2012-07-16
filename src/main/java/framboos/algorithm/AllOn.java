package framboos.algorithm;

import framboos.OutPin;

public class AllOn extends NineLedsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		for (OutPin pin : pins) {
			pin.setValue(true);
		}
		
		while (true) {
		}
	}

}
