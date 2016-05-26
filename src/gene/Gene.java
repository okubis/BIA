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
}
