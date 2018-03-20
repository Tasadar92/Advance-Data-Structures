package tests.practica5;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import material.Position;
import material.ordereddictionary.AVLOrderedDict;
import material.ordereddictionary.Entry;
import material.ordereddictionary.OrderedDictionary;
import material.tree.binarysearchtree.AVLTree;
import material.tree.binarysearchtree.BinarySearchTree;
import material.tree.binarysearchtree.LinkedBinarySearchTree;
import material.tree.binarysearchtree.RBTree;

public class TestBSTree {
	
	private RBTree<String> tree;
	private Position<String>[] elems;

	@Before
	public void setUp() throws Exception {
		elems = (Position<String>[]) new Position[10];
		tree = new RBTree<>();
		elems[2] = tree.insert("c");
		elems[9] = tree.insert("j");
		elems[6] = tree.insert("g");
		elems[3] = tree.insert("d");
		elems[4] = tree.insert("e");
		elems[1] = tree.insert("b");
		elems[5] = tree.insert("f");
		elems[0] = tree.insert("a");		
		elems[7] = tree.insert("h");
		elems[8] = tree.insert("i");
	}

	@Test
	public void testFirst() {
		assertEquals(elems[0], tree.first());
	}
	
	@Test
	public void testLast() {
		assertEquals(elems[9], tree.last());
	}
	
	@Test
	public void testPredecessors() {
		Set<Position<String>> expected = new HashSet<>();
		for (int i=0;i<5;i++) {
			expected.add(elems[i]);
		}
		for (Position<String> pos : tree.predecessors(elems[4])) {
			assertTrue(expected.contains(pos));
			expected.remove(pos);
		}
		assertTrue(expected.isEmpty());
	}
	
	@Test
	public void testSuccessors() {
		Set<Position<String>> expected = new HashSet<>();
		for (int i=4;i<elems.length;i++) {
			expected.add(elems[i]);
		}
		for (Position<String> pos : tree.successors(elems[4])) {
			assertTrue(expected.contains(pos));
			expected.remove(pos);
		}
		assertTrue(expected.isEmpty());
	}

}
