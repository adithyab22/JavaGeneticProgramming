
/**
 * Holds a "clip point". A clip point is the place in a GPTree where crossover
 * will occur.
 * 
 * @see GPTree
 * @see TestGPTree
 */

public class NodePairPlus {
	/** The node above the clip point. */
	public Node parent;
	/** The node below the clip point. */
	public Node child;
	/**
	 * the count of number of nodes so far tested in the process of searching
	 * for the clip point.
	 */
	int counter;
}
