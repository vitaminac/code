package greedy.graph;

import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {
	@Override
	public int compare(Edge edge1, Edge edge2) {
		if (edge1.getWeight() < edge2.getWeight())
			return -1;
		if (edge1.getWeight() > edge2.getWeight())
			return 1;
		return 0;
	}
}
