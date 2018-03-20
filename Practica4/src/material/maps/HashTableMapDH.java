package material.maps;

/**
 * @param <K> The hey
 * @param <V> The stored value
 */
public class HashTableMapDH<K, V> extends AbstractHashTableMap<K, V> {

    public HashTableMapDH(int size) {
        super(size);
    }

    public HashTableMapDH() {
        super();
    }

    public HashTableMapDH(int p, int cap) {
        super(p, cap);
    }

    @Override
    protected int offset(K key, int i) {
    		
    		return i + capacity - (hashValue(key) % capacity);
    }
}

/*Dada	una	entrada	<k,v>,	
si	la	posición	h(k)	se	encuentra	ocupada,	entonces	se	prueban	de	forma	iterativa	las	
cubetas	A[(h(k)+d(k)*i)	mod N]	para	i=1,	2,3	4,	…	donde	d(k)	representa	una	función	
hash	secundaria.	Una	opción	habitual	es	que	d(k)=	q	– (k	mod q)	siendo	q	un	número	
primo	q	<	N.	*/