package gene;

public abstract class Node extends Gene {
	public abstract boolean isInput();
	public abstract boolean isOutput();
	public abstract boolean isHidden();
	public abstract double function(double sum);
	public abstract Node clone();
	protected int index;
	
	@Override
	public boolean isNode() {
		return true;
	}

	@Override
	public boolean isConnection() {
		return false;
	}
	
	public int getIndex(){
		return this.index;
	}

}