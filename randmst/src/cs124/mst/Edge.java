package cs124.mst;

public class Edge implements Comparable<Edge> {
	int src, dest;
	double weight;

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Edge(int s, int d, double w) {
		src = s;
		dest = d;
		weight = w;
	}

	public int compareTo(Edge compareEdge) {
		double diff = this.weight - compareEdge.weight;
		if (diff < 0) {
			return -1;
		} else if (diff > 0) {
			return 1;
		} else {
			return 0;
		}
	}
}
