package cs124.mst;

import java.util.Random;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Graph {

	int num_vertices;
	int dimensions;
	Vertex[] vertices;
	double[][] adjMatrix;
	static final int ADJ_MATRIX = 0;
	static final int ADJ_LIST = 1;
	static final double THRESHHOLD = 0.5 ; // This is function of num_vertices and dimension
	static final int LARGE_SIZE_N = 16384 ;

	static int GRAPH_TYPE = ADJ_LIST;

	public Graph(int num_vertices, int dimensions) {

		this.num_vertices = num_vertices;
		this.dimensions = dimensions;
		Random uniform = new Random();
		uniform.setSeed(System.currentTimeMillis()); // Setting a different seed
														// for each loop

		vertices = new Vertex[num_vertices];

		Vertex v = null;

		// Loop below initializes ArrayList of vertices
		for (int l = 0; l < num_vertices; l++) {

			v = generateVertex(l, dimensions, uniform);
			vertices[l] = v;
		}

		if (GRAPH_TYPE == ADJ_MATRIX) {
			generateAdjMatrix(dimensions, num_vertices, uniform, vertices);
		} else {
			generateAdjList(dimensions, num_vertices, uniform, vertices);
		}
	}

	protected void generateAdjList(int dimensions, int num_vertices, Random uniform, Vertex[] vertices) {

		// Loop below randomly generates distance between all vertices and store
		// to adjList of corresponding vertex
		for (int i = 0; i < num_vertices; i++) {

			Vertex vi = vertices[i];

			for (int j = i + 1; j < num_vertices; j++) {
				Vertex vj = vertices[j];
				double weight = vi.generateWeightBasedOnDimension(vj);
				if (num_vertices <  LARGE_SIZE_N || filterEdge(weight)) {
					Edge edge = new Edge(vi.getIndex(), vj.getIndex(), weight);
					vi.addEdgeToAdjList(edge);
				}
			}
		}
	}

	// function f(n) to filter edges exceeding threshold
	protected boolean filterEdge(double weight) {
		if (weight > THRESHHOLD) {
			return false;
		} else {
			return true;
		}
	}

	protected void generateAdjMatrix(int dimensions, int num_vertices, Random uniform, Vertex[] vertices) {

		adjMatrix = new double[num_vertices][num_vertices];

		// Loop below randomly generates distance between all vertices and store
		// to adj_matrix
		for (int i = 0; i < num_vertices; i++) {

			Vertex vi = vertices[i];
			adjMatrix[i][i] = 0.0;

			for (int j = i + 1; j < num_vertices; j++) {
				Vertex vj = vertices[j];
				adjMatrix[i][j] = vi.generateWeightBasedOnDimension(vj);
				adjMatrix[j][i] = adjMatrix[i][j];
			}
		}
	}

	protected Vertex generateVertex(int index, int dimensions, Random uniform) {

		Vertex v = null;
		switch (dimensions) {
		case 0: {
			v = new ZeroDVertex(index);
			break;
		}
		case 2: {
			v = new TwoDVertex(index, uniform.nextDouble(), uniform.nextDouble());
			break;
		}
		case 3: {
			v = new ThreeDVertex(index, uniform.nextDouble(), uniform.nextDouble(), uniform.nextDouble());
			break;
		}
		case 4: {
			v = new FourDVertex(index, uniform.nextDouble(), uniform.nextDouble(), uniform.nextDouble(),
					uniform.nextDouble());
			break;

		}
		default: {
			System.out.println("Dimension not supported. Exiting");
			System.exit(0);
		}
		}
		return v;
	}

	public double getKruksalMSTWeight() {

		Edge[] result = KruksalMST();
		double totalWeight = 0;
		double minWeight = Double.MAX_VALUE;
		double maxWeight = 0;

		for (Edge edge : result) {
			double edgeWeight = edge.getWeight();

			if (minWeight > edgeWeight) {
				minWeight = edgeWeight;
			}
			if (maxWeight < edgeWeight) {
				maxWeight = edgeWeight;
			}
			totalWeight += edgeWeight;
		}

		if (RandMST.IS_DEBUG_ENABLED) {
			System.out.println("MST weight range : min = " + minWeight + " , max = " + maxWeight);
		}

		return totalWeight;
	}

	public Edge[] KruksalMST() {

		Edge[] result = new Edge[num_vertices - 1];

		// Build edges
		ArrayList<Edge> edges = new ArrayList<Edge>();

		if (GRAPH_TYPE == ADJ_MATRIX) {
			for (int i = 0; i < num_vertices; i++) {
				for (int j = i + 1; j < num_vertices; j++) {
					edges.add(new Edge(i, j, adjMatrix[i][j]));
				}
			}
		} else {
			for (int i = 0; i < num_vertices; i++) {
				Vertex vertex = vertices[i];
				ArrayList<Edge> adjList = vertex.getAdjList();
				for (Edge edge : adjList) {
					edges.add(edge);
				}
			}

		}

		// Sort edges (by weight) to pick edges in non-decreasing order
		Collections.sort(edges);

		// Allocate memory for creating num_vertices subsets (Initially 1 set
		// per vertex)
		Subset subsets[] = new Subset[num_vertices];
		for (int i = 0; i < num_vertices; i++) {
			subsets[i] = new Subset(i);
		}

		int edgesInResult = 0;
		Iterator<Edge> edgeIterator = edges.iterator();

		try {
			while (edgesInResult < num_vertices - 1) {
				// Pick the next smallest edge from edges sorted by weight
				Edge nextEdge = edgeIterator.next();

				int srcSet = find(subsets, nextEdge.src);
				int destSet = find(subsets, nextEdge.dest);

				// Add this edge to result if this edge does't cause cycle
				if (srcSet != destSet) {
					result[edgesInResult] = nextEdge;
					edgesInResult++;
					Union(subsets, srcSet, destSet);
				}
				// Else discard this edge
			}
		} catch (NoSuchElementException e) {
			System.out.println("It seems you discarded more weights than you should have during filtering. No MST found. Aborting.");
			System.exit(1);
		}

		return result;

	}

	// A utility function to find set of an element i
	// (uses path compression technique)
	int find(Subset subsets[], int i) {
		// find root and make root as parent of i (path compression)
		if (subsets[i].parent != i)
			subsets[i].parent = find(subsets, subsets[i].parent);

		return subsets[i].parent;
	}

	// A function that does union of two sets of x and y
	// (uses union by rank)
	void Union(Subset subsets[], int x, int y) {
		int xroot = find(subsets, x);
		int yroot = find(subsets, y);

		// Attach smaller rank tree under root of high rank tree
		// (Union by Rank)
		if (subsets[xroot].rank < subsets[yroot].rank)
			subsets[xroot].parent = yroot;
		else if (subsets[xroot].rank > subsets[yroot].rank)
			subsets[yroot].parent = xroot;

		// If ranks are same, then make one as root and increment
		// its rank by one
		else {
			subsets[yroot].parent = xroot;
			subsets[xroot].rank++;
		}
	}

	public double getPrimsMSTWeight(int num_vertices) {
		// printAdjMatrix();

		int[] prev = new int[num_vertices]; // Storing the visited origin of
											// lowest cost

		// Initialize weights of vertices based on distance from origin
		for (int i = 0; i < num_vertices; i++) {
			prev[i] = 0;
			vertices[i].setWeight(adjMatrix[0][i]);

		}

		MinHeap heap = new MinHeap(vertices);

		Edge[] result = new Edge[num_vertices - 1];
		List<Integer> verticesVisited = new ArrayList<>();
		verticesVisited.add(0);

		Double runningWeight = new Double(0);

		Vertex v;
		int vi; // index for v
		int e = 0; // index for edges visited

		while (verticesVisited.size() < num_vertices) {
			v = heap.extractMin(); // Pulling first value from heap and getting
									// the vertex index
			vi = v.getIndex();

			// Loop extracting min from heap until min extracted hasn't been
			// reached
			while (verticesVisited.contains(vi)) {
				heap.updateWeight(vi, new Double(10)); // Bump visited value
														// down heap

				v = heap.extractMin(); // Get a new min and index
				vi = v.getIndex();

			}

			verticesVisited.add(vi);
			result[e] = new Edge(prev[vi], vi, v.getWeight());
			e++;

			runningWeight += v.getWeight();

			heap.updateWeight(vi, new Double(10));

			double iw;

			// Looping through vertices reachable from v.
			for (int i = 0; i < num_vertices; i++) {
				if (!verticesVisited.contains(i)) {
					iw = heap.getWeight(i);

					// If weight of vertex w is lower from v than from prev[w],
					// update weight
					if (iw > adjMatrix[vi][i]) {
						heap.updateWeight(i, adjMatrix[vi][i]);
						prev[i] = vi;
					}

				} else {
					continue;

				}
			}
		}

		return runningWeight;
	}
}
