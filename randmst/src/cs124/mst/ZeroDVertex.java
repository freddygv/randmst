package cs124.mst;

import java.util.Random;

public class ZeroDVertex extends Vertex {

	public ZeroDVertex(int index) {
		super(index);
		dimension = 0;
	}

	@Override
	public String toString() {
		return "Vertex #" + index + ":No dimension";
	}

	@Override
	public double generateWeightBasedOnDimension(Vertex otherVertex) {

		if (otherVertex.dimension != 0) {
			System.out.println("Illegal operation");
		}

		Random uniform = new Random();
		uniform.setSeed(System.currentTimeMillis());
		return uniform.nextDouble();
	}

}
