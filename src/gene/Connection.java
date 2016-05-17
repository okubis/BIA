package gene;

import java.util.Random;

public class Connection extends Gene{
	private Node start;
	private Node end;
	private double weight;
	private boolean enabled;
	
	
	public Connection(Node start, Node end, double weight, boolean enabled) {
		this.start = start;
		this.end = end;
		this.weight = weight;
		this.enabled = enabled;
	}

	public void updateWeight(double update){
		this.weight += update;
	}
	
	public void updateWeightByGaussianNoise(Random rand){
		updateWeight(rand.nextGaussian());
	}
	
	@Override
	public boolean isNode() {
		return false;
	}

	@Override
	public boolean isConnection() {
		return true;
	}

	
}
