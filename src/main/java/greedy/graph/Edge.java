package greedy.graph;

public class Edge {

	private int sourcevertex;
	private int destinationvertex;
	private double weight;
	
	public Edge(int origin, int destination, double w){
		this.sourcevertex = origin;
		this.destinationvertex = destination;
		this.weight = w;
	}

	public int getSourcevertex() {
		return sourcevertex;
	}
	
	public int getDestinationvertex() {
		return destinationvertex;
	}
	
	public double getWeight() {
		return weight;
	}
}
