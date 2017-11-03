package cs124.mst;

public class RandMST {

	static final int PRIMS_METHOD = 0;
	static final int KRUKSAL_METHOD = 1;

	static int MST_METHOD = KRUKSAL_METHOD;
	static boolean IS_DEBUG_ENABLED = false;

	public static void main(String[] args) {

		if (args.length != 4) {
			System.out.println("Usage: randmst 0 numpoints numtrials dimension");
			System.exit(1);
		}

		int flag = Integer.parseInt(args[0]);
		int num_vertices = Integer.parseInt(args[1]);
		int num_trials = Integer.parseInt(args[2]);
		int dimensions = Integer.parseInt(args[3]);

		if (flag == 1) {
			IS_DEBUG_ENABLED = true;
		}

		if (IS_DEBUG_ENABLED) {
			System.out.println("INPUT : num_vertices num_trials dimensions");
			System.out.println(num_vertices + " " + num_trials + " " + dimensions);
		}

		RandMST me = new RandMST();
		me.run(num_vertices, num_trials, dimensions);
	}

	public void run(int num_vertices, int num_trials, int dimensions) {

		double mstWeights = 0;
		long startTime = 0;
		long time = 0;

		for (int i = 1; i <= num_trials; i++) {

			double newWeight = 0;

			if (IS_DEBUG_ENABLED) {
				System.out.println("\n#####Start Trial #" + i + " ######\n");
				System.out.println("Start Building Graph");
				startTime = System.currentTimeMillis();
			}
			Graph g = new Graph(num_vertices, dimensions);
			if (IS_DEBUG_ENABLED) {
				time = System.currentTimeMillis() - startTime;
				System.out.println("Finished building graph in " + timeToString(time));
				startTime = System.currentTimeMillis();
				System.out.println("Start Running MST");
			}
			if (MST_METHOD == KRUKSAL_METHOD) {
				newWeight = g.getKruksalMSTWeight();
			} else {
				newWeight = g.getPrimsMSTWeight(num_vertices);
			}

			if (IS_DEBUG_ENABLED) {
				time = System.currentTimeMillis() - startTime;
				System.out.println("MST weight (of all edges) = " + newWeight);
				System.out.println("Finished Running MST in " + timeToString(time));
			}

			mstWeights += newWeight;
		}

		double avgMSTWeight = mstWeights / num_trials;

		// Print expected output
		if (IS_DEBUG_ENABLED) {
			System.out.println("\nEXPECTED OUTPUT : average numpoints numtrials dimension");
		}
		System.out.println(+avgMSTWeight + " " + num_vertices + " " + num_trials + " " + dimensions);
	}

	public String timeToString(long time) {

		if (time / (1000 * 60 * 60) != 0) {
			return (time / (1000 * 60 * 60)) + " hours, " + (time % (1000 * 60 * 60)) + " minutes.";
		} else if (time / (1000 * 60) != 0) {
			return (time / (1000 * 60)) + " minutes, " + (time % (1000 * 60)) + " seconds.";
		} else if (time / 1000 != 0) {
			return time / 1000 + " seconds.";
		} else {
			return time + " miliseconds.";
		}

	}

}
