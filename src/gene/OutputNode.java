package gene;

import java.io.Serializable;
import java.util.Random;

public class OutputNode extends Node implements Serializable {

	public static final int OUTPUTS_COUNT = 3;
	private double sigmoidParam;
	private double bias;
	
	
	
	public OutputNode(double sigmoidParam, int mark) {
		this.sigmoidParam = sigmoidParam;
		this.mark = mark;
		this.bias = 0;
	}
	
	public OutputNode(double sigmoidParam, int mark, double bias) {
		this.sigmoidParam = sigmoidParam;
		this.mark = mark;
		this.bias = bias;
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
		return 20d/(1 + Math.pow(Math.E, -sigmoidParam*(sum + bias) )) - 10d;
	}

	@Override
	public void mutate(Random rand) {
//		sigmoidParam += rand.nextGaussian();
		bias += 5*rand.nextGaussian();
		
	}

	@Override
	public Node clone() {
		return new OutputNode(sigmoidParam, mark);
	}

	@Override
	public String toString() {
		return "OutputNode [ sigmoidParam = " + sigmoidParam + " , bias = " + bias
				+ " , mark = " + mark + " ]";
	}


	
}
