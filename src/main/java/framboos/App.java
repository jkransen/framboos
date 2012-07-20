package framboos;

import static framboos.FilePaths.*;

import java.io.File;
import java.util.ServiceLoader;

import framboos.algorithm.Algorithm;


/**
 * The main class
 * 
 */
public class App {

	private final Algorithm algorithm;

	public App(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void start() {
		System.out.println("Starting Framboos with algorithm "+algorithm.getClass().getSimpleName());
		algorithm.setUp();
		algorithm.execute();
	}

	public void stop() {
		System.out.println("\nGoodbye\n");
		algorithm.tearDown();
	}

	public static void main(String[] args) {
		checkWiredPi();
		Algorithm algorithm = args.length > 0 ? getAlgorithm(args[0]) : pickAlgorithm(); 
		final App app = new App(algorithm);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				app.stop();
			}
		});
		app.start();
	}

	private static void checkWiredPi() {
		if (!new File(getGpioPath()).exists()) {
			throw new RuntimeException("WiredPi does not seem to be installed.");
		}
	}

	/**
	 * silly random picker, improve later
	 * @return
	 */
	private static Algorithm pickAlgorithm() {
		Algorithm chosen = null;
		while (chosen == null) {
			for (Algorithm candidate : ServiceLoader.load(Algorithm.class)) {
				if (Math.random() < 0.001) {
					chosen = candidate;
				}
			}
		}
		return chosen;
	}

	private static Algorithm getAlgorithm(String name) {
		try {
			Class<?> matchingClass = Class.forName(name);
			if (matchingClass.isAssignableFrom(Algorithm.class)) {
				try {
					Algorithm algorithm = matchingClass.asSubclass(Algorithm.class).newInstance();
					return algorithm;
				} catch (Exception e) {
					throw new RuntimeException("Failed to instantiate algorithm class "+name);
				}
			}
			else {
				throw new RuntimeException("Class "+name+" is not a valid framboos.algorithm.Algorithm");
			}
		} catch (ClassNotFoundException e) {
			// Failed, now try service lookup
		}
		for (Algorithm candidate : ServiceLoader.load(Algorithm.class)) {
			if (candidate.getClass().getSimpleName().toLowerCase().matches(".*"+name.toLowerCase()+".*")) {
				return candidate;
			}
		}
		throw new RuntimeException("No algorithm found for name "+name);
	}
}
