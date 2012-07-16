package framboos.algorithm;

import framboos.OutPin;

public abstract class NineLedsAlgorithm implements Algorithm {
	
	private final OutPin[] pins = new OutPin[9];

	public void setUp() {
		for (int i = 0; i <= 8; i++) {
			pins[i] = new OutPin(i);
		}
	}

	protected void pause() {
		try {
			Thread.sleep(100); // .1 sec
		} catch (InterruptedException e) {
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
