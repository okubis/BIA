package gene;

import java.io.Serializable;
import java.util.Random;

public class Connection extends Gene implements Serializable {
	private int start;
	private int end;
	private double weight;
	private boolean enabled;

	private boolean isRecurrent;
	
	
	public Connection(int start, int end, int history, double weight, boolean enabled) {
		this.start = start;
		this.end = end;
		this.mark = history;
		this.weight = weight;
		this.enabled = enabled;
		this.isRecurrent = false;
	}
	
	public Connection(int start, int end, int history, double weight) {
		this(start, end, history, weight, false);
	}

	public Connection(int start, int end, int history) {
		this(start, end, history, 1d);
	}

	public void updateWeight(double update){
		this.weight += update;
	}
	
	public void updateWeightByGaussianNoise(Random rand){
		updateWeight(3*rand.nextGaussian());
	}
	
	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public double getWeight() {
		return weight;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void enable(){
		this.setEnabled(true);
	}
	
	public void disable(){
		this.setEnabled(false);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isRecurrent() {
		return isRecurrent;
	}

	public void setRecurrent(boolean isRecurrent) {
		this.isRecurrent = isRecurrent;
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

	@Override
	public Gene clone() {
		return new Connection(start, end, mark, weight, enabled);
	}

	@Override
	public String toString() {
		return "Connection [ start = " + start + " , end = " + end + " , weight = "
				+ weight + " , enabled = " + enabled + " , isRecurrent = "
				+ isRecurrent +  " , mark = " + mark + " ]";
	}
	
	
	
}
