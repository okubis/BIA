package gene;

import java.util.Random;

public class InputNode extends Node {

	public static final int INPUTS_COUNT = 9;
	private double sigmoidParam;
	private double bias;
	
	public InputNode(double sigmoidParam, int mark) {
		this.sigmoidParam = sigmoidParam;
		this.mark = mark;
		this.bias = 0;
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
		return 1/(1 + Math.pow(Math.E, -sigmoidParam*(sum + bias) ));
	}

	@Override
	public void mutate(Random rand) {
//		sigmoidParam += rand.nextGaussian();
		bias += rand.nextGaussian();
		
	}

	@Override
	public Node clone() {
		// TODO Auto-generated method stub
		return new InputNode(sigmoidParam, mark);
	}

	public double getSigmoidParam() {
		return sigmoidParam;
	}
	
	@Override
	public String toString() {
		return "InputNode [sigmoidParam=" + sigmoidParam + ", mark=" + mark
				+ "]";
	}

}
