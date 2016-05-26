package gene;

import java.util.Random;

public class HiddenNode extends Node {

	private double sigmoidParam;
	
	public HiddenNode(double sigmoidParam, int mark) {
		this.sigmoidParam = sigmoidParam;
		this.mark = mark;
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

	@Override
	public double function(double sum) {
		// TODO Auto-generated method stub
		return 1/(1 + Math.pow(Math.E, -sigmoidParam*sum));
	}

	public double getSigmoidParam() {
		return sigmoidParam;
	}

	@Override
	public void mutate(Random rand) {
		this.sigmoidParam += rand.nextGaussian();
	}

	@Override
	public Node clone() {
		// TODO Auto-generated method stub
		return new HiddenNode(sigmoidParam, mark);
	}

}
