package framboos.algorithm;

import framboos.OutPin;
import framboos.algorithm.util.Timer;

public class BinaryCount extends NineLedsAlgorithm {

	public void lightLeds(OutPin[] pins) {
		int binaryNumber = 0;
		
		while (true) {
			for (int j = 0; j < pins.length; j++) {
				if ((binaryNumber & (1 << j)) > 0) {
					pins[j].setValue(true);
				} 
				else {
					pins[j].setValue(false);
				}
			}
			binaryNumber++;
			
			Timer.pause();
		}
	}
}
