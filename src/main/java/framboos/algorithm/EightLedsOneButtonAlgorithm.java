package framboos.algorithm;

import framboos.OutPin;
import framboos.ReverseInPin;

public abstract class EightLedsOneButtonAlgorithm implements Algorithm {
	
	private final OutPin[] pins = new OutPin[8];
	private ReverseInPin button;
	private boolean isClosed = false;
	private boolean buttonWasPressed = false;

	public void setUp() {
		for (int i = 0; i <= 7; i++) {
			pins[i] = new OutPin(i);
		}
		button = new ReverseInPin(8);
		new Thread() {
			public void run() {
				while (!isClosed) {
					checkButton();
					pause();
				}
			}
		}.start();
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
		button.close();
		isClosed = true;
	}
	
	public void execute() {
		lightLeds(pins);
	}
	
	private void checkButton() {
		boolean isButtonPressed = button.getValue();
		if (isButtonPressed && !buttonWasPressed) {
			handleButtonPressed(pins);
		}
		buttonWasPressed = isButtonPressed;
	}
	
	public abstract void lightLeds(OutPin[] pins);
	
	public abstract void handleButtonPressed(OutPin[] pins);
}
