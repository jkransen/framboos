package framboos.algorithm;

import framboos.OutPin;

public class ButtonChangeDirection extends EightLedsOneButtonAlgorithm {
	
	int previous = 0;
	boolean goesUp = true;

	@Override
	public void lightLeds(OutPin[] pins) {
		while (true) {
			int current = goesUp ? previous + 1 : previous - 1;
			if (current < 0) {
				current += pins.length;
			}
			if (current >= pins.length) {
				current -= pins.length;
			}
			pins[previous].setValue(false);
			pins[current].setValue(true);
			previous = current;
			pause();
		}
	}

	@Override
	public void handleButtonPressed(OutPin[] pins) {
		goesUp = !goesUp;
	}
}
