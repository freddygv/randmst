package cs124.mst;

public class Subset {

	int parent;
	int rank;

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	Subset(int parent) {
		this.parent = parent;
		rank = 0;
	}

}
