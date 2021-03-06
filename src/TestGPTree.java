
import java.util.*;
import java.text.*;

public class TestGPTree {
	static int maxDepth = 5;
	static Random rand = new Random();

	public static void crossover(GPTree t1, GPTree t2, Random rand) {
		NodePairPlus pair1 = t1.randomParentAndChild(rand);
		NodePairPlus pair2 = t2.randomParentAndChild(rand);
		pair1.parent.changeChild(pair1.child, pair2.child);
		pair2.parent.changeChild(pair2.child, pair1.child);
	}

	public static void main(String[] args) {
		DataSet ds = new DataSet(
				"C:/Users/Adithya/workspace/CrossoverForStudents/Data2.dat");
		// DataSet ds = new DataSet("Data2a.dat");
		Node[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
		OperatorFactory o = new OperatorFactory(ops);
		TerminalFactory t = new TerminalFactory(ds.numIndepVars());
		GPTree tree1 = new GPTree(o, t, 2, rand);
		GPTree tree2 = new GPTree(o, t, 2, rand);
		System.out.println(tree1);
		System.out.println(tree2);
		System.out.println();
		crossover(tree1, tree2, rand);
		System.out.println(tree1);
		System.out.println(tree2);
	}
}
