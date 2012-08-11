package framboos.algorithm;

import framboos.InPin;
import framboos.OutPin;
import framboos.algorithm.util.Timer;

public abstract class NineLedsTwoButtonsAlgorithm implements Algorithm {
	
	private final OutPin[] leds = new OutPin[9];
	private InPin button1;
	private InPin button2;
	private boolean isClosed = false;
	
	private boolean button1WasPressed = false;
	private boolean button2WasPressed = false;

	public void setUp() {
		for (int i = 0; i <= 7; i++) {
			leds[i] = new OutPin(i);
		}
		leds[8] = new OutPin(10);
		button1 = new InPin(8);
		button2 = new InPin(9);
		new Thread() {
			public void run() {
				while (!isClosed) {
					checkButtons();
					Timer.pause(50);
				}
			}
		}.start();
	}
	
	public void tearDown() {
		for (OutPin pin : leds) {
			pin.close();
		}
		button1.close();
		button2.close();
		isClosed = true;
	}
	
	public void execute() {
		lightLeds(leds);
	}
	
	private void checkButtons() {
		boolean isButton1Pressed = button1.getValue();
		boolean isButton2Pressed = button2.getValue();
		if ((isButton1Pressed && !button1WasPressed)) {
			handleButton1Pressed();
		}
		if ((isButton2Pressed && !button2WasPressed)) {
			handleButton2Pressed();
		}
		button1WasPressed = isButton1Pressed;
		button2WasPressed = isButton2Pressed;
	}
	
	public OutPin[] getLeds() {
		return leds;
	}
	
	public abstract void lightLeds(OutPin[] pins);
	
	public void handleButton1Pressed() {
		// default no action
	}

	public void handleButton2Pressed() {
		// default no action
	}
}
