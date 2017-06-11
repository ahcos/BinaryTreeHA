package de.uni_koeln.info.java.binarytree;
public class BTreeNode<T extends Comparable<T>> {

	BTreeNode<T> left;
	BTreeNode<T> right;
	T value;
	

	public BTreeNode(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

}
