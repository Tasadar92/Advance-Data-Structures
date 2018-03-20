package material.maps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.lang.Iterable;







/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro
 * @param <K> The key
 * @param <V> The stored value
 * 
 * Modification made by danie
 * 
 */
public class HashTableMapSC<K, V> implements Map<K, V> {

    private class HashEntry<T, U> implements Entry<T, U> {
    	private U value;
    	private T key;

        public HashEntry(T k, U v) {
            this.key = k;
            this.value = v;
        }

        @Override
        public U getValue() {
            return value;
        }

        @Override
        public T getKey() {
            return key;
        }

        public U setValue(U val) {
        	U v = this.value;
            this.value = val;
            return v;
        }

        @Override
        public boolean equals(Object o) {
            if (o.getClass() != this.getClass()) {
                return false;
            }

            HashEntry<T, U> ent;
            try {
                ent = (HashEntry<T, U>) o;
            } catch (ClassCastException ex) {
                return false;
            }
            return (ent.getKey().equals(this.key)) && (ent.getValue().equals(this.value));
        }

        /**
         * Entry visualization.
         */
        @Override
        public String toString() {
        	return "(" + key + "," + value + ")";
        }
    }

    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {
    	private int pos;
    	private int row = -1;
        private ArrayList<HashEntry<T, U>>[] bucket;
        
        //Ejercicio 2.2
        public HashTableMapIterator(ArrayList<HashEntry<T, U>>[] map, int numElems) {
        	this.bucket = map;
            if (numElems == 0) {
                this.pos = bucket.length;
            } else {
            	this.pos = 0;
                goToNextElement();
            }
        }

        private void goToNextElement() {
        	final int n = bucket.length;
            
            while ((this.pos < n) && ((this.bucket[this.pos] == null))) {
                this.pos++;
            }
            
            //Iterator<HashEntry<T, U>> l = this.bucket[pos].iterator();
            
            if(this.row < this.bucket[pos].size()){
            	this.row++;
            }else{
            	this.row = -1;
            	this.pos++;
            }
        }

        @Override
        public boolean hasNext() {
        	return (this.pos < this.bucket.length);
        }

        @Override
        public Entry<T, U> next() {
        	if (hasNext()) {
        		int currentPos = this.pos;
        		int currentRow = this.row;
                goToNextElement();
                return (Entry<T, U>) this.bucket[currentPos].get(row);
            } else {
                throw new IllegalStateException("The map has not more elements");
            }
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {
    	public HashTableMapIterator<T, U> it;
    	
        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public T next() {
            return it.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return (it.next() == null);
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {
    	public HashTableMapIterator<T, U> it;
    	
        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public U next() {
            return it.next().getValue();
        }

        @Override
        public boolean hasNext() {
            return (it.next() == null);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

	private int scale;
	private int shift;
	private int prime;
	private int capacity;
	private int n;
	private ArrayList<HashEntry<K, V>>[] bucket;
	protected final Entry<K, V> AVAILABLE = new HashEntry<>(null, null);

    /**
     * Creates a hash table
     */
    public HashTableMapSC() {
    	this(109345121, 1000);
    }

    /**
     * Creates a hash table.
     *
     * @param cap initial capacity
     */
    public HashTableMapSC(int cap) {
    	this(109345121, cap);
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap) {
    	this.n = 0;
        this.prime = p;
        this.capacity = cap;
        this.bucket = (ArrayList<HashEntry<K, V>>[]) new ArrayList[capacity];
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return
     */
    protected int hashValue(K key) {
    	return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    /**
     * Returns the number of entries in the hash table.
     *
     * @return the size
     */
    @Override
    public int size() {
    	return n;
    }

    /**
     * Returns whether or not the table is empty.
     *
     * @return true if the size is 0
     */
    @Override
    public boolean isEmpty() {
    	return (n == 0);
    }

    /**
     * Returns the value associated with a key.
     *
     * @param key
     * @return value
     */
    @Override
    public V get(K key) throws IllegalStateException {
        int i = hashValue(key);
        if (bucket[i] == key) { 
        //Devuelve el elemento de la lista de los elementos usando find o recorrerla
        	ArrayList<HashEntry<K, V>> l = new ArrayList();
        	l = bucket[i];
        	for(HashEntry<K, V> e: l){
        		if(e.getKey() == key)
        			return e.getValue();
        	}
        }
        return null;
    }

    /**
     * Put a key-value pair in the map, replacing previous one if it exists.
     *
     * @param key
     * @param value
     * @return value
     */
    @Override
    public V put(K key, V value) throws IllegalStateException {
    	int i = hashValue(key);
        if (bucket[i] == key) {
        	ArrayList<HashEntry<K, V>> l = new ArrayList();
        	l = bucket[i];
        	for(HashEntry<K, V> e: l){
        		if(e.getValue() == value)
        			return (V) e.setValue(value);
        	}
            
        } else if (n >= capacity / 2) {
            rehash();
            i = hashValue(key);
        }
        ArrayList<HashEntry<K, V>> l = new ArrayList();
        l.add(new HashEntry<K, V>(key, value));
        bucket[i] = l; // crear arraylist y a�adirlo
        n++;
        return null;
    }

    /**
     * Removes the key-value pair with a specified key.
     *
     * @param key
     * @return
     */
    @Override
    public V remove(K key) throws IllegalStateException {
        int i = hashValue(key); // find this key first
        if (bucket[i] != key) {
            return null; // nothing to remove
        }
        V e = (V) ((Entry<K, V>) bucket[i]).getValue();
        bucket[i] = (ArrayList<HashEntry<K, V>>) AVAILABLE; // mark this slot as reactivated
        n--;
        return e;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
    	return new HashTableMapIterator<K, V>(this.bucket, this.n);
    }

    /**
     * Returns an iterable object containing all of the keys.
     *
     * @return
     */
    @Override
    public Iterable<K> keys() {
    	return new Iterable<K>() {
            public Iterator<K> iterator() {
                return new HashTableMapKeyIterator<K, V>(new HashTableMapIterator<K, V>(bucket, n));
            }
    	};
    }

    /**
     * Returns an iterable object containing all of the values.
     *
     * @return
     */
    @Override
    public Iterable<V> values() {
    	return new Iterable<V>() {
            public Iterator<V> iterator() {
                return new HashTableMapValueIterator<K, V>(new HashTableMapIterator<K, V>(bucket, n));
            }
        };
    }

    /**
     * Returns an iterable object containing all of the entries.
     *
     * @return
     */
    @Override
    public Iterable<Entry<K, V>> entries() {
    	return new Iterable<Entry<K, V>>() { //Rellena el iterable
            public Iterator<Entry<K, V>> iterator() {
                return new HashTableMapIterator<K, V>(bucket, n);
            }
        };
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected void checkKey(K k) {
    	if (k == null) {
            throw new IllegalStateException("Invalid key: null.");
        }
    }
    
    protected void rehash() {
        capacity = 2 * capacity;
        ArrayList<HashEntry<K, V>>[] old = bucket;
        // new bucket is twice as big
        bucket = (ArrayList<HashEntry<K, V>>[]) new ArrayList[capacity]; 
        java.util.Random rand = new java.util.Random();
        // new hash scaling factor
        scale = rand.nextInt(prime - 1) + 1;
        // new hash shifting factor
        shift = rand.nextInt(prime);
        for (ArrayList<HashEntry<K, V>> e : old) {
            if ((e != null) && (e != AVAILABLE)) { // a valid entry
                int j = hashValue(((Entry<K, V>) e).getKey());
                bucket[j] = e;
            }
        }
    }

    /**
     * Increase/reduce the size of the hash table and rehashes all the entries.
     */
    protected void rehash(int newCap) {
    	capacity = 2 * capacity;
        ArrayList<HashEntry<K, V>>[] old = bucket;
        // new bucket is twice as big
        bucket = (ArrayList<HashEntry<K, V>>[]) new ArrayList[capacity];
        java.util.Random rand = new java.util.Random();
        // new hash scaling factor
        scale = rand.nextInt(prime - 1) + 1;
        // new hash shifting factor
        shift = rand.nextInt(prime);
        for (ArrayList<HashEntry<K, V>> e : old) {
            if ((e != null) && (e != AVAILABLE)) { // a valid entry
                int j = hashValue(((Entry<K, V>) e).getKey());
                bucket[j] = e;
            }
        }
    }
}
