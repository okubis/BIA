/**
 * 
 */
package gene;

import java.util.Random;

/**
 * @author Eda
 *
 */
public abstract class Gene {
	protected int mark;
	
	public int getMark(){
		return mark;
	}
	
	public abstract boolean isNode();
	public abstract boolean isConnection();
	public abstract void mutate(Random rand);
	public abstract Gene clone();
	public abstract String toString();
//	public abstract int hashCode();
//	public abstract boolean equals();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mark;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gene other = (Gene) obj;
		if (mark != other.mark)
			return false;
		return true;
	}
	
	
}
