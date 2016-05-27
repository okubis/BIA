package gene;

import java.util.Random;

public class HiddenNode extends Node {

	private double sigmoidParam;
	private double bias;
	
	public HiddenNode(double sigmoidParam, int mark) {
		this.sigmoidParam = sigmoidParam;
		this.mark = mark;
		this.bias = 0;
	}
	
	@Override
	public boolean isInput() {
		return false;
	}

	@Override
	public boolean isOutput() {
		return false;
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	public double getSigmoidParam() {
		return sigmoidParam;
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
		return new HiddenNode(sigmoidParam, mark);
	}

	@Override
	public String toString() {
		return "HiddenNode [sigmoidParam=" + sigmoidParam + ", bias=" + bias
				+ ", mark=" + mark + "]";
	}



}
