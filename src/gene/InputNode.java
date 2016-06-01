package gene;

import java.io.Serializable;
import java.util.Random;

public class InputNode extends Node implements Serializable {

	public static final int INPUTS_COUNT = 9;
	private double sigmoidParam;
	private double bias;
	
	public InputNode(double sigmoidParam, int mark) {
		this.sigmoidParam = sigmoidParam;
		this.mark = mark;
		this.bias = 0;
	}
	
	public InputNode(double sigmoidParam, int mark, double bias) {
		this.sigmoidParam = sigmoidParam;
		this.mark = mark;
		this.bias = bias;
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
		return 1/(1 + Math.pow(Math.E, -sigmoidParam*(sum + bias) ));
	}

	@Override
	public void mutate(Random rand) {
//		sigmoidParam += rand.nextGaussian();
		bias += 3*rand.nextGaussian();
		
	}

	@Override
	public Node clone() {
		return new InputNode(sigmoidParam, mark);
	}

	public double getSigmoidParam() {
		return sigmoidParam;
	}

	@Override
	public String toString() {
		return "InputNode [ sigmoidParam = " + sigmoidParam + " , bias = " + bias
				+ " , mark = " + mark + " ]";
	}
	


}
