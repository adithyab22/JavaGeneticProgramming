

public class Plus extends Binop {
	public Plus() {
	}

	public Plus(Node l, Node r) {
		super(l, r);
	}

	public double eval(double[] data) {
		return lChild.eval(data) + rChild.eval(data);
	}

	public String toString() {
		String s = new String();
		s += "(" + lChild.toString() + " + " + rChild.toString() + ")";
		return s;
	}

	public Node duplicate() {
		Plus alterEgo = new Plus();
		alterEgo.lChild = lChild.duplicate();
		alterEgo.rChild = rChild.duplicate();
		return alterEgo;
	}
}
