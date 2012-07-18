package framboos.algorithm;

import framboos.OutPin;

public class Tennis extends SevenLedsTwoButtonsAlgorithm {
	
	int previous = 0;
	boolean goesUp = true;
	boolean isAtButton1 = false;
	boolean isAtButton2 = false;
	int points1 = 0;
	int points2 = 0;
	int delay = 200;

	@Override
	public void execute(OutPin[] pins) {
		while (true) {
			int current = goesUp ? previous + 1 : previous - 1;
			int numPins = pins.length;
			
			if (current < 0) {
				points2++;
				showScore();
				current += numPins;
			}
			if (current >= numPins) {
				points1++;
				showScore();
				current -= numPins;
			}
			pins[previous].setValue(false);
			pins[current].setValue(true);
			isAtButton1 = current == 0;
			isAtButton2 = current == numPins - 1;
			previous = current;
			pause(Math.max(80, delay--));
		}
	}
	
	private void showScore() {
		System.out.println(points1 +" - "+ points2);
	}

	@Override
	public void handleButton1Pressed() {
		if (!goesUp && isAtButton1) {
			goesUp = true;
		}
	}

	@Override
	public void handleButton2Pressed() {
		if (goesUp && isAtButton2) {
			goesUp = false;
		}
	}
}
