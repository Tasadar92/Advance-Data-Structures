package test.practica1;

import static org.junit.Assert.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import practica2.LCRSTree;
import material.Position;
import material.tree.Tree;


public class LCRSTreeTest {
	
	private LCRSTree<Integer> tree;
	private Position<Integer>[] pos;

	/*
	 *         0
	 *      /  |  \
	 *     1   2   3
	 *    /       / \
	 *   4       5   6
	 */
	@Before
	public void setUp() throws Exception {
		tree = new LCRSTree<>();
		pos = (Position<Integer>[]) new Position[7];
		pos[0] = tree.addRoot(0);
		pos[1] = tree.add(1, pos[0]);
		pos[2] = tree.add(2, pos[0]);
		pos[3] = tree.add(3, pos[0]);
		pos[4] = tree.add(4, pos[1]);
		pos[5] = tree.add(5, pos[3]);
		pos[6] = tree.add(6, pos[3]);
	}

	@Test
	public void testIsEmpty() {
		assertFalse(tree.isEmpty());
		LCRSTree<Integer> t = new LCRSTree<>();
		assertTrue(t.isEmpty());
	}

	@Test
	public void testIsInternal() {
		for (int i = 0; i < pos.length; i++) {
			if (i == 2 || i == 4 || i == 5 || i == 6) {
				assertFalse(tree.isInternal(pos[i]));
			} else {
				assertTrue(tree.isInternal(pos[i]));
			}
		}
	}

	@Test
	public void testIsLeaf() {
		for (int i = 0; i < pos.length; i++) {
			if (i == 2 || i == 4 || i == 5 || i == 6) {
				assertTrue(tree.isLeaf(pos[i]));
			} else {
				assertFalse(tree.isLeaf(pos[i]));
			}
		}
	}

	@Test
	public void testIsRoot() {
		assertTrue(tree.isRoot(pos[0]));
		for (int i = 1; i < pos.length; i++) {
			assertFalse(tree.isRoot(pos[i]));
		}
	}

	@Test
	public void testRoot() {
		assertEquals(pos[0], tree.root());
		for (int i = 1; i < pos.length; i++) {
			assertNotEquals(pos[i], tree.root());
		}
	}
	
	@Test(expected=RuntimeException.class)
	public void testRootExc() {
		LCRSTree<Integer> t = new LCRSTree<>();
		assertEquals(pos[0], t.root());
	}

	@Test
	public void testParent() {
		assertEquals(pos[0], tree.parent(pos[1]));
		assertEquals(pos[0], tree.parent(pos[2]));
		assertEquals(pos[0], tree.parent(pos[3]));
		assertEquals(pos[1], tree.parent(pos[4]));
		assertEquals(pos[3], tree.parent(pos[5]));
		assertEquals(pos[3], tree.parent(pos[6]));
	}
	
	@Test(expected=RuntimeException.class)
	public void testParentExc() {
		tree.parent(pos[0]);
	}

	@Test
	public void testChildren() {
		Deque<Position<Integer>> expected = new ArrayDeque<>();
		expected.addLast(pos[1]);
		expected.addLast(pos[2]);
		expected.addLast(pos[3]);
		for (Position<Integer> c : tree.children(pos[0])) {
			Position<Integer> next = expected.removeFirst();
			assertEquals(next, c);			
		}
		assertTrue(expected.isEmpty());
		expected.addLast(pos[4]);
		for (Position<Integer> c : tree.children(pos[1])) {
			Position<Integer> next = expected.removeFirst();
			assertEquals(next, c);			
		}
		assertTrue(expected.isEmpty());
		expected.addLast(pos[5]);
		expected.addLast(pos[6]);
		for (Position<Integer> c : tree.children(pos[3])) {
			Position<Integer> next = expected.removeFirst();
			assertEquals(next, c);			
		}
		assertTrue(expected.isEmpty());
	}

	@Test
	public void testIterator() {
		Deque<Position<Integer>> expected = new ArrayDeque<>();
		expected.addLast(pos[0]);
		expected.addLast(pos[1]);
		expected.addLast(pos[2]);
		expected.addLast(pos[3]);
		expected.addLast(pos[4]);
		expected.addLast(pos[5]);
		expected.addLast(pos[6]);
		for (Position<Integer> itNode : tree) {
			Position<Integer> next = expected.removeFirst();
			assertEquals(next, itNode);
		}
		assertTrue(expected.isEmpty());
	}

	@Test
	public void testReplace() {
		tree.replace(pos[3], 1);
		assertEquals(1, (int) pos[3].getElement());
	}

	@Test
	public void testAddRoot() {
		LCRSTree<Integer> t = new LCRSTree<>();
		Position<Integer> p = t.addRoot(9);
		assertEquals(p, t.root());
	}
	
	@Test(expected=RuntimeException.class)
	public void testAddRootExc() {
		Position<Integer> p = tree.addRoot(9);
		assertEquals(p, tree.root());
	}

	@Test
	public void testSwapElements() {
		tree.swapElements(pos[4], pos[0]);
		assertEquals(0, (int) pos[4].getElement());
		assertEquals(4, (int) pos[0].getElement());
	}

	@Test
	public void testAddEPositionOfEInt() {
		Position<Integer> pos10 = tree.add(10, pos[3], 1);
		Deque<Position<Integer>> expected = new ArrayDeque<>();
		expected.addLast(pos[5]);
		expected.addLast(pos10);
		expected.addLast(pos[6]);
		for (Position<Integer> c : tree.children(pos[3])) {
			Position<Integer> next = expected.removeFirst();
			assertEquals(next, c);
		}
		assertTrue(expected.isEmpty());
	}

	@Test
	public void testRemove() {
		tree.remove(pos[3]);
		Deque<Position<Integer>> expected = new ArrayDeque<>();
		expected.addLast(pos[1]);
		expected.addLast(pos[2]);
		for (Position<Integer> c : tree.children(pos[0])) {
			Position<Integer> next = expected.removeFirst();
			assertEquals(next, c);
		}
		assertTrue(expected.isEmpty());
	}

	@Test
	public void testSubTree() {
		LCRSTree<Integer> subTree = (LCRSTree<Integer>) tree.subTree(pos[3]);
		Deque<Position<Integer>> expected = new ArrayDeque<>();
		expected.addLast(pos[3]);
		expected.addLast(pos[5]);
		expected.addLast(pos[6]);
		for (Position<Integer> c : subTree) {
			Position<Integer> next = expected.removeFirst();
			assertEquals(next, c);
		}
		assertTrue(expected.isEmpty());
		assertEquals(pos[3], subTree.root());
		expected.addLast(pos[1]);
		expected.addLast(pos[2]);
		for (Position<Integer> c : tree.children(pos[0])) {
			Position<Integer> next = expected.removeFirst();
			assertEquals(next, c);
		}
		assertTrue(expected.isEmpty());
	}

	@Test
	public void testAttach() {
		LCRSTree<Integer> aux = new LCRSTree<>();
		Position<Integer> pos10 = aux.addRoot(10);
		Position<Integer> pos11 = aux.add(11, pos10);
		Position<Integer> pos12 = aux.add(12, pos10);
		tree.attach(pos[1], aux);
		Deque<Position<Integer>> expected = new ArrayDeque<>();
		expected.addLast(pos[4]);
		expected.addLast(pos10);
		for (Position<Integer> c : tree.children(pos[1])) {
			Position<Integer> next = expected.removeFirst();
			assertEquals(next, c);
		}
		assertTrue(expected.isEmpty()); 
	}

}
