package cs124.mst;

import java.util.ArrayList;

public abstract class Vertex {
	protected int index;
	protected int dimension;
	protected Double weight;
	protected ArrayList<Edge> adjList ;


	public Vertex(int index) {
		this.index = index;
		dimension = -1;
		weight = new Double(10);
		adjList = new ArrayList<Edge>() ;
	}

	public int getIndex() {
		return index;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public void addEdgeToAdjList(Edge edge) {
		adjList.add(edge) ;

	}

	public ArrayList<Edge> getAdjList() {
		return adjList;
	}

	public abstract double generateWeightBasedOnDimension(Vertex otherVertex);

}
