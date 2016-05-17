/**
 * 
 */
package gene;

/**
 * @author Eda
 *
 */
public abstract class Gene {
	private int mark;
	
	public int getMark(){
		return mark;
	}
	
	public abstract boolean isNode();
	public abstract boolean isConnection();
}
