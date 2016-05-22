package gene;

import java.util.Random;

public class InputNode extends Node {

	public static final int INPUTS_COUNT = 3;
	private static double sigmoidParam;
	
	public InputNode(double sigmoidParam) {
		this.sigmoidParam = sigmoidParam;
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

}
