/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.practica1;

import material.Position;
import material.tree.narytree.LinkedTree;
import practica2.LeafIterator;
import practica2.TreeSizeCalculator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jvelez
 */
public class LeafIteratorTest {

    /*
     *        A
     *      / | \
     *     B  C  D
     *        |   \
     *        E    F
     */
    @Test
    public void testNext() {
    		LinkedTree<String> t = new LinkedTree<>();
        Position<String> a = t.addRoot("A");
        Position<String> b = t.add("B", a);
        Position<String> c = t.add("C", a);
        Position<String> d = t.add("D", a);
        Position<String> e = t.add("E", c);
        Position<String> f = t.add("F", d);
        String expected = "BEF";
        LeafIterator<String> leaf = new LeafIterator<>(t);
        String actual = "";
        while (leaf.hasNext()) {
        		actual += leaf.next().getElement();
        }
        assertEquals(expected, actual);
    }
    
}
