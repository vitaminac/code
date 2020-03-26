package problema2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Data {

	private double[] profit;
	private double[] weight;
	private double maxWeight;

	public Data(int n, double maxWeight) {
		profit = new double[n];
		weight = new double[n];
		this.maxWeight = maxWeight;
	
		for (int i = 0; i < n; i++) {
			profit[i] = (int) Math.round(Math.random() * 96 + 44);
			weight[i] = (int) Math.round(Math.random() * 89 + 15);
		}
	}
	
	public Data(int n) {
		profit = new double[n];
		weight = new double[n];
			
		double sumWeight = 0;
		for (int i = 0; i < n; i++) {
			profit[i] = (int) Math.round(Math.random() * 96 + 44);
			weight[i] = (int) Math.round(Math.random() * 89 + 15);
			sumWeight += this.weight[i];			
		}
		this.maxWeight = 0.5*sumWeight;
	}

	public Data(String filename) {
		try (BufferedReader bf = new BufferedReader(new FileReader(filename))) {
			int n = Integer.parseInt(bf.readLine().trim());
			maxWeight = Double.parseDouble(bf.readLine().trim());
			profit = new double[n];
			weight = new double[n];
			String[] tokens = bf.readLine().trim().split(" ");
            for (int i = 0; i < tokens.length; i++) {
                profit[i] = Double.parseDouble(tokens[i]);
            }
            tokens = bf.readLine().trim().split(" ");
            for (int i = 0; i < tokens.length; i++) {
                weight[i] = Double.parseDouble(tokens[i]);
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
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
