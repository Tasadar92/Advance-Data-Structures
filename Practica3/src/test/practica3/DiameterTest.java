package test.practica3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import material.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.LinkedBinaryTree;
import practica3.Diameter;

public class DiameterTest {
	
	private BinaryTree<Integer> t1;
	private Position<Integer> v1;
	private Position<Integer> v2;

	@Before
	public void setUp() throws Exception {
		t1 = new LinkedBinaryTree<>();
		Position<Integer> uno = t1.addRoot(1);
		Position<Integer> dos = t1.insertLeft(uno, 2);
		Position<Integer> tres = t1.insertRight(uno, 3);
		Position<Integer> cuatro = t1.insertRight(dos, 4);
		Position<Integer> cinco = t1.insertLeft(tres, 5);
		Position<Integer> seis = t1.insertRight(tres, 6);
		Position<Integer> siete = t1.insertLeft(cinco, 7);
		Position<Integer> ocho = t1.insertRight(cinco, 8);
		v1 = cuatro;
		v2 = siete;
	}

	@Test
	public void testDiameter() {
		Diameter diameter = new Diameter();
		int d = diameter.evalDiameter(t1, v1, v2);
		assertEquals(6, d);
	}

}
