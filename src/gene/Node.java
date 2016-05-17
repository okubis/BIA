package gene;

public abstract class Node extends Gene {
	public abstract boolean isInput();
	public abstract boolean isOutput();
	public abstract boolean isHidden();
	
	@Override
	public boolean isNode() {
		return true;
	}

	@Override
	public boolean isConnection() {
		return false;
	}

}
