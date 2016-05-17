package gene;

public class OutputNode extends Node {

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

}
