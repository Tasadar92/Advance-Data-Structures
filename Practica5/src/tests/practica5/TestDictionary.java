package tests.practica5;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import material.Position;
import material.ordereddictionary.AVLOrderedDict;
import material.ordereddictionary.Entry;
import material.ordereddictionary.OrderedDictionary;

public class TestDictionary {
	
	private OrderedDictionary<Integer, String> dict;
	private Entry<Integer, String>[] entries;

	@Before
	public void setUp() throws Exception {
		entries = (Entry<Integer, String>[]) new Entry[10];
		dict = new AVLOrderedDict<>((k1, k2) -> k1-k2);
		entries[2] = dict.insert(2, "c");
		entries[9] = dict.insert(9, "j");
		entries[6] = dict.insert(6, "g");
		entries[3] = dict.insert(3, "d");
		entries[4] = dict.insert(4, "e");
		entries[1] = dict.insert(1, "b");
		entries[5] = dict.insert(5, "f");
		entries[0] = dict.insert(0, "a");		
		entries[7] = dict.insert(7, "h");
		entries[8] = dict.insert(8, "i");
	}

	@Test
	public void testFindRange() {
		Set<Entry<Integer, String>> expected = new HashSet<>();
		for (int i=5;i<=8;i++) {
			expected.add(entries[i]);
		}
		for (Entry<Integer, String> e : dict.findRange(5,  8)) {
			assertTrue(expected.contains(e));
			expected.remove(e);
		}
		assertTrue(expected.isEmpty());
	}

}
