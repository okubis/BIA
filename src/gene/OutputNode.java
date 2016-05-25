package gene;

import java.util.Random;

public class OutputNode extends Node {

	public static final int OUTPUTS_COUNT = 3;
	private double sigmoidParam;
	
	
	
	public OutputNode(double sigmoidParam, int index) {
		this.sigmoidParam = sigmoidParam;
		this.index = index;
	}

	@Override
	public boolean isInput() {
		return false;
	}

	@Override
	public boolean isOutput() {
		return true;
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public double function(double sum) {
		// TODO Auto-generated method stub
		return 1/(1 + Math.pow(Math.E, -sigmoidParam*sum));
	}

	@Override
	public void mutate(Random rand) {
		this.sigmoidParam += rand.nextGaussian();
	}

	@Override
	public Node clone() {
		// TODO Auto-generated method stub
		return new OutputNode(sigmoidParam, index);
	}
	
}
