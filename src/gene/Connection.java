package gene;

import java.util.Random;

public class Connection extends Gene{
	private Node start;
	private Node end;
	private double weight;
	private boolean enabled;
	
	
	public Connection(Node start, Node end, int history, double weight, boolean enabled) {
		this.start = start;
		this.end = end;
		this.mark = history;
		this.weight = weight;
		this.enabled = enabled;
	}
	
	public Connection(Node start, Node end, int history, double weight) {
		this(start, end, history, weight, false);
	}

	public Connection(Node start, Node end, int history) {
		this(start, end, history, 1d);
	}

	public void updateWeight(double update){
		this.weight += update;
	}
	
	public void updateWeightByGaussianNoise(Random rand){
		updateWeight(rand.nextGaussian());
	}
	
	public Node getStart() {
		return start;
	}

	public Node getEnd() {
		return end;
	}

	public double getWeight() {
		return weight;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isNode() {
		return false;
	}

	@Override
	public boolean isConnection() {
		return true;
	}

	@Override
	public void mutate(Random rand) {
		updateWeightByGaussianNoise(rand);
		
	}

	
}
