package practica4;

import material.maps.HashTableMapDH;
import material.maps.HashTableMapSC;

public class Degree<K,Student> {
	private String id;
	private String name;
	
	public Degree(String idDegree, String name) {
		this.id = idDegree;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public K getId() {
		// TODO Auto-generated method stub
		return (K) id;
	}
}
