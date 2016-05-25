package gene;

import java.util.Random;

public class InputNode extends Node {

	public static final int INPUTS_COUNT = 9;
	private double sigmoidParam;
	
	public InputNode(double sigmoidParam, int index) {
		this.sigmoidParam = sigmoidParam;
		this.index = index;
	}
	
	@Override
	public boolean isInput() {
		return true;
	}

	@Override
	public boolean isOutput() {
		return false;
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
		sigmoidParam += rand.nextGaussian();
		
	}

	@Override
	public Node clone() {
		// TODO Auto-generated method stub
		return new InputNode(sigmoidParam, index);
	}

	public double getSigmoidParam() {
		return sigmoidParam;
	}

}
