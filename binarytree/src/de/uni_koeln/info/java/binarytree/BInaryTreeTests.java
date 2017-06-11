package de.uni_koeln.info.java.binarytree;


import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BInaryTreeTests {
	
	private BTree bTree;

	@Before
	public void setup() {
		int[] input  = { 6, 9, 4, 13, 17, 1, 10, 5, 7, 3 };
		bTree = new BTree(input);
//		 bTree.print();
	}
	
	
	/**
	 * Siehe: {@link BTree#insert(int)}
	 */
	@Test
	public void insert() {
		Assert.assertNotNull(bTree);
		Assert.assertFalse(bTree.insert(10));
	
		Assert.assertFalse(bTree.insert(4));

		Assert.assertTrue(bTree.insert(-1));
		
	}
	
	
	/**
	 * Siehe: {@link BTree#contians(int)}
	 */
	@Test
	public void contains() {
		Assert.assertNotNull(bTree);
		Assert.assertTrue(bTree.contains(17));
		Assert.assertTrue(bTree.contains(5));
		Assert.assertTrue(bTree.contains(3));
		Assert.assertFalse(bTree.contains(-1));
	}
	
	/**
	 * Siehe: {@link BTree#asSortedList} 
	 */
	@Test
	public void asSortedList() {
		Assert.assertNotNull(bTree);
		List<Integer> list = bTree.asSortedList();
	//  [ 1 | 3 | 4 | 5 | 6 | 7 | 9 | 10 | 13 | 17]
		Assert.assertNotNull(list);
		Assert.assertEquals(10, list.size());
		Assert.assertEquals(new Integer(1), list.get(0));
		Assert.assertEquals(new Integer(17), list.get(9));
	}
	
	/**
	 * Siehe: {@link BTree#elementsSmallerThan(int)} 
	 */
	@Test
	public void elementsSmallerThan() {
		Assert.assertNotNull(bTree);
		List<Integer> list = bTree.elementsSmallerThan(6);
	//  [ 1 | 3 | 4 | 5 ]
		Assert.assertNotNull(list);
		Assert.assertEquals(4, list.size());
		Assert.assertEquals(new Integer(1), list.get(0));
		Assert.assertEquals(new Integer(5), list.get(3));
		Assert.assertFalse(list.contains(6));
	}

}
