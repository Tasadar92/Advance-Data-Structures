package tests.ordereddictionary;

import java.util.TreeSet;
import junit.framework.TestCase;
import material.ordereddictionary.Entry;
import material.ordereddictionary.RBTOrderedDict;

public class RBTOrderedDictTest extends TestCase {
	
	public void testSize() {
		RBTOrderedDict <String,Integer> dict = new RBTOrderedDict <String,Integer>();
		assertEquals(dict.size(),0);
		dict.insert("Angel", 9151592);
		assertEquals(dict.size(),1);
		dict.insert("Angel", 9151591);
		assertEquals(dict.size(),2);
		dict.insert("Jose",  9100000);	
		assertEquals(dict.size(),3);
	}

	
	public void testIsEmpty() {
		RBTOrderedDict <String,Integer> dict = new RBTOrderedDict <String,Integer>();
		assertEquals(dict.isEmpty(),true);
		dict.insert("Angel", 9151592);
		assertEquals(dict.isEmpty(),false);
	}

	
	public void testFind() {
		RBTOrderedDict <String,Integer> dict = new RBTOrderedDict <String,Integer>();
		dict.insert("Angel", 9151592);
		dict.insert("Angel", 9151591);
		dict.insert("Jose",  9100000);
		Entry <String,Integer> contacto = dict.find("Angel");
		assertEquals(contacto.getValue().intValue(),9151591);		
	}

	
	public void testFindAll() {
		RBTOrderedDict <String,Integer> dict = new RBTOrderedDict <String,Integer>();
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
		RBTOrderedDict <Integer,Integer> dict = new RBTOrderedDict <Integer,Integer>();
		for (int cont = 0; cont < 1000; cont++) {
			dict.insert((int)(Math.random()*1000), cont);
		}
	}

	
	public void testRemove() {
		RBTOrderedDict <String,Integer> dict = new RBTOrderedDict <String,Integer>();
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
		RBTOrderedDict <String,Integer> dict = new RBTOrderedDict <>();
		dict.insert("Angel", 9151592);
		Entry <String,Integer> e = dict.find("Angel");
		dict.remove(e);
		Entry <String,Integer> f = dict.find("Angel");
		assertEquals(f,null);		
	}

	
	public void testRemoveDos() {
		RBTOrderedDict <String,Integer> dict = new RBTOrderedDict <String,Integer>();
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
