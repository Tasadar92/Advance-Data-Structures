package tests.ordereddictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import junit.framework.TestCase;
import material.ordereddictionary.AVLOrderedDict;
import material.ordereddictionary.Entry;
import material.ordereddictionary.OrderedDictionary;

public class AVLOrderedDictTest extends TestCase {

    AVLOrderedDict<String, Integer> d = new AVLOrderedDict<>();

    public void setDict() {
        d.insert("Angel", 9151592);
        d.insert("Angel", 9151591);
        d.insert("Jose", 9100000);

        d.insert("Pedro", 9151592);
        d.insert("Manuel", 9151591);
        d.insert("Rosa", 9100000);

        d.insert("Andres", 9151592);
        d.insert("Mario", 9151591);
        d.insert("Carmen", 9100000);

        d.insert("Isabel", 9151592);
        d.insert("Monica", 9151591);
        d.insert("Jesus", 9100000);
    }

    public void testSize() {
        AVLOrderedDict<String, Integer> dict = new AVLOrderedDict<>();
        assertEquals(dict.size(), 0);
        dict.insert("Angel", 9151592);
        assertEquals(dict.size(), 1);
        dict.insert("Angel", 9151591);
        assertEquals(dict.size(), 2);
        dict.insert("Jose", 9100000);
        assertEquals(dict.size(), 3);
    }

    public void testIsEmpty() {
        AVLOrderedDict<String, Integer> dict = new AVLOrderedDict<>();
        assertEquals(dict.isEmpty(), true);
        dict.insert("Angel", 9151592);
        assertEquals(dict.isEmpty(), false);
    }

    public void testFind() {
        AVLOrderedDict<String, Integer> dict = new AVLOrderedDict<>();
        dict.insert("Angel", 9151592);
        dict.insert("Angel", 9151591);
        dict.insert("Jose", 9100000);
        Entry<String, Integer> contacto = dict.find("Angel");
        assertEquals(contacto.getValue().intValue(), 9151592);
    }

    public void testFindAll() {
        AVLOrderedDict<String, Integer> dict = new AVLOrderedDict<>();
        int[] telefono = {9151592, 9151591, 9151593};
        dict.insert("Angel", telefono[0]);
        dict.insert("Angel", telefono[1]);
        dict.insert("Jose", telefono[2]);
        TreeSet<Integer> cjtoTelefonos = new TreeSet<Integer>();
        for (int cont = 0; cont < 3; cont++) {
            cjtoTelefonos.add(telefono[cont]);
        }

        Iterable<Entry<String, Integer>> it = dict.findAll("Angel");
        for (Entry<String, Integer> contacto : it) {
            assertEquals(cjtoTelefonos.contains(contacto.getValue()), true);
        }
    }

    public void testInsert() {
        AVLOrderedDict<Integer, Integer> dict = new AVLOrderedDict<>();
        for (int cont = 0; cont < 1000; cont++) {
            dict.insert((int) (Math.random() * 1000), cont);
        }
    }

    public void testRemove() {
        AVLOrderedDict<String, Integer> dict = new AVLOrderedDict<>();
        int[] telefono = {9151592, 9151591, 9151593};
        dict.insert("Angel", telefono[0]);
        dict.insert("Angel", telefono[1]);
        dict.insert("Jose", telefono[2]);
        Entry<String, Integer> e1 = dict.find("Jose");
        dict.remove(e1);
        Entry<String, Integer> f1 = dict.find("Jose");
        assertEquals(f1, null);
        assertEquals(dict.size(), 2);
        Entry<String, Integer> e2 = dict.find("Angel");
        dict.remove(e2);
        assertEquals(dict.size(), 1);
        Entry<String, Integer> e3 = dict.find("Angel");
        dict.remove(e3);
        assertEquals(dict.size(), 0);
    }

    public void testRemoveUno() {
        AVLOrderedDict<String, Integer> dict = new AVLOrderedDict<>();
        dict.insert("Angel", 9151592);
        Entry<String, Integer> e = dict.find("Angel");
        dict.remove(e);
        Entry<String, Integer> f = dict.find("Angel");
        assertEquals(f, null);
    }

    public void testRemoveDos() {
        AVLOrderedDict<String, Integer> dict = new AVLOrderedDict<>();
        dict.insert("Jose", 9151590);
        dict.insert("Angel", 9151592);
        dict.insert("Angel", 9151591);

        Entry<String, Integer> e0 = dict.find("Jose");
        dict.remove(e0);
        Entry<String, Integer> e1 = dict.find("Angel");
        dict.remove(e1);
        Entry<String, Integer> e2 = dict.find("Angel");
        dict.remove(e2);

        Entry<String, Integer> f = dict.find("Angel");
        assertEquals(null, f);
    }

    public void testRemove4() {
        /*
        AVLOrderedDict<String, Integer> dict = new AVLOrderedDict<>();
        int[] telefono = {9151592, 9151591, 9151593};
        Entry<String, Integer> e1 = dict.insert("Angel", telefono[0]);
        Entry<String, Integer> e2 = dict.insert("Angel", telefono[1]);
        Entry<String, Integer> e3 = dict.insert("Jose", telefono[2]);
        dict.remove(e1);
        assertEquals(dict.size(), 2);
        dict.remove(e2);
        assertEquals(dict.size(), 1);
        dict.remove(e3);
        assertEquals(dict.size(), 0);*/
    }

    public void testSizeNew() {

        OrderedDictionary<Integer, String> testDic = new AVLOrderedDict<Integer, String>();
        assertEquals(testDic.size(), 0);

        testDic.insert(1, "uno");
        assertEquals(testDic.size(), 1);

        testDic.insert(2, "dos");
        assertEquals(testDic.size(), 2);

        Entry<Integer, String> entry1 = testDic.find(1);
        Entry<Integer, String> entry2 = testDic.find(2);

        testDic.remove(entry1);
        testDic.remove(entry2);

        assertEquals(testDic.size(), 0);

    }

    public void testIsEmptyNew() {

        OrderedDictionary<Integer, String> testDic = new AVLOrderedDict<>();
        assertTrue(testDic.isEmpty());

        testDic.insert(1, "uno");
        assertFalse(testDic.isEmpty());

        testDic.insert(2, "dos");
        assertFalse(testDic.isEmpty());

        Entry<Integer, String> entry1 = testDic.find(1);
        Entry<Integer, String> entry2 = testDic.find(2);

        testDic.remove(entry1);
        testDic.remove(entry2);

        assertTrue(testDic.isEmpty());

    }

    public void testFindAllNew() {

        OrderedDictionary<Integer, String> testDic = new AVLOrderedDict<>();
        testDic.insert(1, "uno");
        testDic.insert(1, "casa");
        testDic.insert(1, "pera");

        Iterable<Entry<Integer, String>> foundEntries = testDic.findAll(1);

        List<Entry<Integer, String>> listEntries = this.iterableToList(foundEntries);

        assertEquals(listEntries.size(), 3);

    }

    public void testInsertNew() {
        OrderedDictionary<Integer, String> testDic = new AVLOrderedDict<>();

        testDic.insert(1, "uno");
        testDic.insert(2, "dos");
        testDic.insert(3, "tres");

        assertTrue(testDic.find(1).getValue().equalsIgnoreCase("uno"));
        assertTrue(testDic.find(2).getValue().equalsIgnoreCase("dos"));
        assertTrue(testDic.find(3).getValue().equalsIgnoreCase("tres"));
    }

    public void testRemoveNew() {
/*
        OrderedDictionary<Integer, String> testDic = new AVLOrderedDict<>();

        Entry<Integer, String> entry1 = testDic.insert(1, "uno");
        Entry<Integer, String> entry2 = testDic.insert(2, "dos");
        Entry<Integer, String> entry3 = testDic.insert(3, "tres");

        assertTrue(testDic.find(1).getValue().equalsIgnoreCase("uno"));
        assertTrue(testDic.find(2).getValue().equalsIgnoreCase("dos"));
        assertTrue(testDic.find(3).getValue().equalsIgnoreCase("tres"));

        testDic.remove(entry1);
        assertNull(testDic.find(1));
        testDic.remove(entry2);
        assertNull(testDic.find(2));
        testDic.remove(entry3);
        assertNull(testDic.find(3));
*/
    }

    private List<Entry<Integer, String>> iterableToList(Iterable<Entry<Integer, String>> entriesInRange) {
        List<Entry<Integer, String>> entriesList = new ArrayList<>();

        for (Entry<Integer, String> currentEntry : entriesInRange) {
            entriesList.add(currentEntry);
        }
        return entriesList;
    }

}
