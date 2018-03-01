package greedy.mochila;

public class Knapsack {

	private double[] profit;
	private double[] weight;
	private double maxWeight;

	public Knapsack(int n, double maxWeight) {
		profit = new double[n];
		weight = new double[n];
		this.maxWeight = maxWeight;
	
		for (int i = 0; i < n; i++) {
			profit[i] = (int) Math.round(Math.random() * 96 + 44);
			weight[i] = (int) Math.round(Math.random() * 89 + 15);
		}
	}
	
	public Knapsack(int n) {
		profit = new double[n];
		weight = new double[n];
			
		double sumWeight = 0;
		for (int i = 0; i < n; i++) {
			profit[i] = (int) Math.round(Math.random() * 96 + 44);
			weight[i] = (int) Math.round(Math.random() * 89 + 15);
			sumWeight += this.weight[i];			
		}
		this.maxWeight = 0.1*sumWeight;
	}
	
	public int size(){
		return this.profit.length;
	}
	
	public double getProfit(int i){
		return this.profit[i];
	}
	public double getWeight(int i){
		return this.weight[i];
	}
	public void setProfit(int i, double profit) {
		this.profit[i] = profit;		
	}
	
	public void setWeight(int i, double profit) {
		this.weight[i] = profit;		
	}
	
	public double getMaxWeight(){
		return this.maxWeight;
	}
}
