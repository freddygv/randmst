package cs124.mst;

public class MinHeap {
	protected Vertex[] heap;
	protected int[] indices;

	public MinHeap(Vertex[] vertices) {
		heap = vertices;
		indices = new int[vertices.length];
		buildHeap();

	}

	public void buildHeap() {
		for (int i = 0; i < heap.length; i++) {
			indices[i] = i;
		}

		for (int i = heap.length / 2; i > 0; i--) {
			minHeapify(i);
		}
	}

	public void minHeapify(int root_index) {
		int leftChild = findLeftChild(root_index);
		int rightChild = findRightChild(root_index);

		if (heap.length > leftChild && heap[root_index].getWeight() > heap[leftChild].getWeight()) {
			swapVertices(root_index, leftChild);
			minHeapify(leftChild);

		} else if (heap.length > rightChild && heap[root_index].getWeight() > heap[rightChild].getWeight()) {
			swapVertices(root_index, rightChild);
			minHeapify(rightChild);
		}
	}

	private void swapVertices(int leftIndex, int rightIndex) {
		Vertex left_vertex = heap[leftIndex];
		Vertex right_vertex = heap[rightIndex];

		int leftPosition = indices[left_vertex.getIndex()];
		int rightPosition = indices[right_vertex.getIndex()];

		indices[left_vertex.getIndex()] = rightPosition;
		indices[right_vertex.getIndex()] = leftPosition;

		heap[leftIndex] = right_vertex;
		heap[rightIndex] = left_vertex;
	}

	public Vertex extractMin() {
		return heap[0];
	}

	public Double getWeight(int vertex) {
		return heap[indices[vertex]].getWeight();
	}

	public void updateWeight(int vertex, double weight) {
		heap[indices[vertex]].setWeight(weight);
		minHeapify(0);
	}

	private int findLeftChild(int parent_index) {
		return 2 * parent_index;
	}

	private int findRightChild(int parent_index) {
		return (2 * parent_index) + 1;
	}

	public void printHeap() {
		System.out.println("Printing Heap:");
		for (int i = 0; i < heap.length; i++) {
			System.out.printf(heap[i].toString() + " ");

		}
		System.out.println("");
		for (int i = 0; i < heap.length; i++) {
			System.out.printf(indices[i] + " ");

		}

		System.out.println("");
	}
}
