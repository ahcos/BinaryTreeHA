package de.uni_koeln.info.java.binarytree;
public class BTreeNode {

	BTreeNode left;
	BTreeNode right;
	int value;
	

	public BTreeNode(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
