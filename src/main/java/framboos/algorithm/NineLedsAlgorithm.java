package framboos.algorithm;

import framboos.OutPin;

public abstract class NineLedsAlgorithm implements Algorithm {
	
	private final OutPin[] pins = new OutPin[9];

	public void setUp() {
		for (int i = 0; i <= 8; i++) {
			pins[i] = new OutPin(i);
		}
	}

	public void tearDown() {
		for (OutPin pin : pins) {
			pin.close();
		}
	}
	
	public void execute() {
		lightLeds(pins);
	}
	
	public abstract void lightLeds(OutPin[] pins);
}
