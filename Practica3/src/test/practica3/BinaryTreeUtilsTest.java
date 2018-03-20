package test.practica3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import material.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.LinkedBinaryTree;
import practica3.BinaryTreeUtils;

public class BinaryTreeUtilsTest {
	
	private Position<String> a;
	private Position<String> b;
	private Position<String> c;
	private Position<String> d;
	private Position<String> e;
	private Position<String> f;
	private BinaryTree<String> tree;
	private BinaryTreeUtils<String> utils;
	
	/*
	 *      A
	 *     / \
	 *    B   C
	 *   / \   \
	 *  D   E   F
	 */
	@Before
	public void setUp() throws Exception {
		tree = new LinkedBinaryTree<>();
		a = tree.addRoot("A");
		b = tree.insertLeft(a, "B");
		c = tree.insertRight(a, "C");
		d = tree.insertLeft(b, "D");
		e = tree.insertRight(b, "E");
		f = tree.insertRight(c, "F");
		utils = new BinaryTreeUtils<>(tree);
	}

	/*
	 *      A
	 *     / \
	 *    C   B
	 *   /   / \
	 *  F   E   D
	 */
	@Test
	public void testMirror() {
		String expected = "ACBFED";
		
		BinaryTree<String> mirrorTree = utils.mirror();
		String actual = "";
		for (Position<String> p : mirrorTree) {
			actual += p.getElement();
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testContains() {
		assertTrue(utils.contains("A"));
		assertFalse(utils.contains("Z"));
	}

	@Test
	public void testLevel() {
		assertEquals(1, utils.level(tree.root()));
		assertEquals(2, utils.level(b));
		assertEquals(3, utils.level(e));
	}

}
