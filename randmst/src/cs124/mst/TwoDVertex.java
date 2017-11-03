package cs124.mst;

public class TwoDVertex extends Vertex {

	protected Double x;
	protected Double y;

	public TwoDVertex(int index, double x, double y) {
		super(index);
		this.x = x;
		this.y = y;
		dimension = 2;
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	@Override
	public double generateWeightBasedOnDimension(Vertex otherVertex) {

		if (otherVertex.dimension != 2) {
			System.out.println("Illegal operation");
		}

		TwoDVertex v = (TwoDVertex) otherVertex;

		Double xs = Math.pow((x - v.getX()), 2);
		Double ys = Math.pow((y - v.getY()), 2);
		return Math.sqrt(xs + ys);
	}

	@Override
	public String toString() {
		return "Vertex #" + index + ": (" + x + ", " + y + ")";
	}

}
