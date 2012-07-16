package framboos.algorithm;

import framboos.OutPin;

public class ZigZag extends NineLedsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		boolean goUp = true;
		
		int previous = 0;
		int i = 0;
		
		while (true) {
			pins[previous].setValue(false);
			pins[i].setValue(true);
			
			if (i == pins.length - 1) {
				goUp = false;
			}
			
			if (i == 0) {
				goUp = true;
			}
			
			previous = i;
			i = goUp ? i + 1 : i - 1;
			
			pause();
		}
	}
}
