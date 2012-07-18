package framboos.algorithm;

import framboos.InPin;
import framboos.OutPin;
import framboos.ReverseInPin;

public abstract class SevenLedsTwoButtonsAlgorithm implements Algorithm {
	
	private final OutPin[] pins = new OutPin[7];
	private InPin button1;
	private InPin button2;
	private boolean isClosed = false;
	
	private boolean button1WasPressed = false;
	private boolean button2WasPressed = false;

	public void setUp() {
		for (int i = 0; i <= 6; i++) {
			pins[i] = new OutPin(i);
		}
		button1 = new InPin(8);
		button2 = new InPin(9);
		new Thread() {
			public void run() {
				while (!isClosed) {
					checkButtons();
					pause(50);
				}
			}
		}.start();
	}
	
	protected void pause(int ms) {
		try {
			Thread.sleep(ms); // .1 sec
		} catch (InterruptedException e) {
		}
	}
	
	public void tearDown() {
		for (OutPin pin : pins) {
			pin.close();
		}
		button1.close();
		button2.close();
		isClosed = true;
	}
	
	public void execute() {
		execute(pins);
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
	
	public abstract void execute(OutPin[] pins);
	
	public abstract void handleButton1Pressed();

	public abstract void handleButton2Pressed();
}
