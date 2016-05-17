package gene;

public class HiddenNode extends Node {

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

}
