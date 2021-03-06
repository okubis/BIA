package gene;

import java.io.Serializable;
import java.util.Random;

public class HiddenNode extends Node implements Serializable {

	private double sigmoidParam;
	private double bias;
	
	public HiddenNode(double sigmoidParam, int mark) {
		this.sigmoidParam = sigmoidParam;
		this.mark = mark;
		this.bias = 0;
	}
	
	public HiddenNode(double sigmoidParam, int mark, double bias) {
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
		return 1/(1 + Math.pow(Math.E, -sigmoidParam*(sum + bias) ));
	}

	@Override
	public void mutate(Random rand) {
//		sigmoidParam += rand.nextGaussian();
		bias += 3*rand.nextGaussian();
		
	}

	@Override
	public Node clone() {
		return new HiddenNode(sigmoidParam, mark);
	}

	@Override
	public String toString() {
		return "HiddenNode [ sigmoidParam = " + sigmoidParam + " , bias = " + bias
				+ " , mark = " + mark + " ]";
	}



}
