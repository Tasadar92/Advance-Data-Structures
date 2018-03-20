package practica3;

import java.util.LinkedList;

import material.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.LinkedBinaryTree;

/**
*
* @author danie
*/

public class Diameter<E> {
	
	public int evalDiameter(BinaryTree<Integer> tree, Position<Integer> v1, Position<Integer> v2) {
		if(tree != null && !tree.isEmpty()){
			LinkedBinaryTree<Integer> t = (LinkedBinaryTree<Integer>) tree; 
			if((v1 != null) && (t.isInternal(v1) || t.isLeaf(v1) || t.isRoot(v1))){
				if((v2 != null) && (t.isInternal(v2) || t.isLeaf(v2) || t.isRoot(v2))){
					if(v1.equals(v2)){
						return 0;
					}else{
						LinkedList<Position<Integer>> l1 = new LinkedList<>();
						LinkedList<Position<Integer>> l2 = new LinkedList<>();
						v1 = t.parent(v1);
						while(!(v1.equals(t.root()))){
							l1.addFirst(v1);	
							v1 = t.parent(v1);
						}
						v2 = t.parent(v2);
						while(!(v2.equals(t.root()))){
							l2.addFirst(v2);
							v2 = t.parent(v2);
						}
						if(l1.size() > l2.size()){
							if(l2.isEmpty()){
								return l1.size() - l2.size() + 2;
							} 
							int cont = l2.size() - 1;
							int diam = l1.size() - l2.size();
							boolean exit = false;
							
							while(!(l1.get(cont).equals(l2.get(cont)) && !exit)){
								diam += 2;
								if(cont > 0){
									cont--;
								}else{
									exit = true;
								}
							}
							return diam;
						}else if(l1.size() < l2.size()){
							if(l1.isEmpty()){
								return l2.size() - l1.size() + 2;
							} 
							int cont = l1.size() - 1;
							int diam = l2.size() - l1.size();
							boolean exit = false;
							
							while(!(l1.get(cont).equals(l2.get(cont))) && !exit){
								diam += 2;
								if(cont > 0){
									cont--;
								}else{
									exit = true;
								}
							}
							diam += 2;
							return diam;
						}else{
							if(l1.isEmpty() && l2.isEmpty()){
								return 2;
							} 
							int cont = l1.size() - 1;
							int diam = 0;
							boolean exit = false;
							
							while(!(l1.get(cont).equals(l2.get(cont)) && !exit)){
								diam += 2;
								if(cont > 0){
									cont--;
								}else{
									exit = true;
								}
							}
							return diam;
						}
					}
				}
				throw new RuntimeException("El nodo 2 no es valido o no pertenece al arbol");
			}
			throw new RuntimeException("El nodo 1 no es valido o no pertenece al arbol");
		}
		throw new RuntimeException("Arbol no valido");
	}	
}