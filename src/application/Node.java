package application;

public class Node implements Comparable<Node> {

	//
	private int oldAsci = -500;

	public Node(int oldAsci, int freq) {
		super();
		this.oldAsci = oldAsci;
		this.freq = freq;
	}

	private int freq;
	private Node leftChild;
	private Node rightChild;
	private String dir = "";
	private String finalDir = "";
	private int rodLong;

	public int getRodLong() {
		return rodLong;
	}

	public void setRodLong(int rodLong) {
		this.rodLong = rodLong;
	}

	public String getFinalDir() {
		return finalDir;
	}

	public void setFinalDir(String finalDir) {
		this.finalDir = finalDir;
	}

	public Node(int oldAsci, int freq, Node leftChild, Node rightChild, String dir, String finalDir) {
		super();
		this.oldAsci = oldAsci;
		this.freq = freq;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.dir = dir;
		this.finalDir = finalDir;
	}

	public Node(int freq) {
		super();
		this.freq = freq;
	}

	public Node(int oldAsci, int freq, Node leftChild, Node rightChild, String dir) {
		super();
		this.oldAsci = oldAsci;
		this.freq = freq;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.dir = dir;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Node(int freq, Node leftChild, Node rightChild) {
		super();
		this.freq = freq;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	public Node() {
		super();
	}

	public Node(int oldAsci, int freq, Node leftChild, Node rightChild) {
		this.oldAsci = oldAsci;
		this.freq = freq;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	public int getOldAsci() {
		return oldAsci;
	}

	public void setOldAsci(int oldAsci) {
		this.oldAsci = oldAsci;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	public boolean isLeaf() {
		return this.leftChild == null && this.rightChild == null;
	}

	@Override
	public int compareTo(Node o) {
		int compareResult = Integer.compare(this.freq, o.freq);
		if (compareResult != 0) {
			return compareResult;
		}
		return Integer.compare(this.freq, o.freq);
	}
}
