package tests.ordereddictionary;

import java.util.TreeSet;
import junit.framework.TestCase;
import material.ordereddictionary.BSTOrderedDict;
import material.ordereddictionary.Entry;

public class BSTOrderedDictTest extends TestCase {
	
	public void testSize() {
		BSTOrderedDict <String,Integer> dict = new BSTOrderedDict <>();
		assertEquals(dict.size(),0);
		dict.insert("Angel", 9151592);
		assertEquals(dict.size(),1);
		dict.insert("Angel", 9151591);
		assertEquals(dict.size(),2);
		dict.insert("Jose",  9100000);	
		assertEquals(dict.size(),3);
	}

	
	public void testIsEmpty() {
		BSTOrderedDict <String,Integer> dict = new BSTOrderedDict <>();
		assertEquals(dict.isEmpty(),true);
		dict.insert("Angel", 9151592);
		assertEquals(dict.isEmpty(),false);
	}

	
	public void testFind() {
		BSTOrderedDict <String,Integer> dict = new BSTOrderedDict <>();
		dict.insert("Angel", 9151592);
		dict.insert("Angel", 9151591);
		dict.insert("Jose",  9100000);
		Entry <String,Integer> contacto = dict.find("Angel");
		assertEquals(contacto.getValue().intValue(),9151592);		
	}

	
	public void testFindAll() {
		BSTOrderedDict <String,Integer> dict = new BSTOrderedDict <>();
		int [] telefono = {9151592,9151591,9151593};		
		dict.insert("Angel", telefono[0]);
		dict.insert("Angel", telefono[1]);
		dict.insert("Jose",  telefono[2]);
		TreeSet <Integer> cjtoTelefonos = new TreeSet <Integer>();
		for (int cont = 0; cont < 3; cont++)
			cjtoTelefonos.add(telefono[cont]);
		
		Iterable<Entry <String,Integer>> it = dict.findAll("Angel");
		for (Entry <String,Integer> contacto : it) {
			assertEquals(cjtoTelefonos.contains(contacto.getValue()),true);		
		}
	}

	
	public void testInsert() {
		BSTOrderedDict <Integer,Integer> dict = new BSTOrderedDict <>();
		for (int cont = 0; cont < 1000; cont++) {
			dict.insert((int)(Math.random()*1000), cont);
		}
	}

	
	public void testRemove() {
		BSTOrderedDict <String,Integer> dict = new BSTOrderedDict <>();
		int [] telefono = {9151592,9151591,9151593};		
		dict.insert("Angel", telefono[0]);
		dict.insert("Angel", telefono[1]);
		dict.insert("Jose",  telefono[2]);
		Entry <String,Integer> e1 = dict.find("Jose");
		dict.remove(e1);
		Entry <String,Integer> f1 = dict.find("Jose");
		assertEquals(f1,null);		
		assertEquals(dict.size(),2);		
		Entry <String,Integer> e2 = dict.find("Angel");
		dict.remove(e2);
		assertEquals(dict.size(),1);		
		Entry <String,Integer> e3 = dict.find("Angel");
		dict.remove(e3);
		assertEquals(dict.size(),0);
	}

	
	public void testRemoveUno() {
		BSTOrderedDict <String,Integer> dict = new BSTOrderedDict <>();
		dict.insert("Angel", 9151592);
		Entry <String,Integer> e = dict.find("Angel");
		dict.remove(e);
		Entry <String,Integer> f = dict.find("Angel");
		assertEquals(f,null);		
	}

	
	public void testRemoveDos() {
		BSTOrderedDict <String,Integer> dict = new BSTOrderedDict <>();
		dict.insert("Jose", 9151590);
		dict.insert("Angel", 9151592);
		dict.insert("Angel", 9151591);
		Entry <String,Integer> e0 = dict.find("Jose");
		dict.remove(e0);
		Entry <String,Integer> e1 = dict.find("Angel");
		dict.remove(e1);
		Entry <String,Integer> e2 = dict.find("Angel");
		dict.remove(e2);

		Entry <String,Integer> f = dict.find("Angel");
		assertEquals(f,null);		
	}
	
	
}
