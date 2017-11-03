package cs124.mst;

public class FourDVertex extends ThreeDVertex {
	private Double w;

	public FourDVertex(int index, double x, double y, double z, double w) {
		super(index, x, y, z);
		this.w = w;
		dimension = 4;
	}

	@Override
	public double generateWeightBasedOnDimension(Vertex otherVertex) {

		if (otherVertex.dimension != 4) {
			System.out.println("Illegal operation");
		}

		FourDVertex v = (FourDVertex) otherVertex;

		Double xs = Math.pow((x - v.getX()), 2);
		Double ys = Math.pow((y - v.getY()), 2);
		Double zs = Math.pow((z - v.getZ()), 2);
		Double ws = Math.pow((w - v.getW()), 2);

		return Math.sqrt(xs + ys + zs + ws);
	}

	public Double getW() {
		return w;
	}

	@Override
	public String toString() {
		return "Vertex #" + index + ": (" + x + ", " + y + ", " + z + ", " + w + ")";
	}

}
