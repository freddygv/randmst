package cs124.mst;

public class ThreeDVertex extends TwoDVertex {

	protected Double z;

	public ThreeDVertex(int index, double x, double y, double z) {
		super(index, x, y);
		this.z = z;
		dimension = 3;
	}

	@Override
	public double generateWeightBasedOnDimension(Vertex otherVertex) {

		if (otherVertex.dimension != 3) {
			System.out.println("Illegal operation");
		}

		ThreeDVertex v = (ThreeDVertex) otherVertex;

		Double xs = Math.pow((x - v.getX()), 2);
		Double ys = Math.pow((y - v.getY()), 2);
		Double zs = Math.pow((z - v.getZ()), 2);

		return Math.sqrt(xs + ys + zs);
	}

	public Double getZ() {
		return z;
	}

	@Override
	public String toString() {
		return "Vertex #" + index + ": (" + x + ", " + y + ", " + z + ")";
	}

}
