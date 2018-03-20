package practica3;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

import material.Position;
import material.tree.BreadthFirstTreeIterator;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.LinkedBinaryTree;

public class BinaryTreeUtils<E> {
	
	private BinaryTree<E> tree;
	private BinaryTree<E> izq;
	private BinaryTree<E> der;
	private Deque<Position<E>> list;
	
	public BinaryTreeUtils(BinaryTree<E> tree) {
		this.tree = tree;
	}
	/** 
	  * Given a tree the method returns a new tree where all left children 
	  * become right children and vice versa
	*/
	public BinaryTree<E> mirror() {
			LinkedBinaryTree t = new LinkedBinaryTree();
			Deque<Position<E>> nodeQueue = new LinkedList<>();
			
			t.addRoot(this.tree.root());
			nodeQueue.add(this.tree.root());
			
			while(!(nodeQueue.isEmpty())){
				
				Position<E> aux = nodeQueue.pollFirst();
				BreadthFirstTreeIterator it = new BreadthFirstTreeIterator<>(t);
				
				if(this.tree.hasLeft(aux) && this.tree.hasRight(aux)){
					
					izq = this.tree.subTree(this.tree.left(aux));
					der = this.tree.subTree(this.tree.right(aux));
					
					t.attachLeft(aux, der);
					t.attachRight(aux, izq);
					
					nodeQueue.add(this.tree.left(aux));
					nodeQueue.add(this.tree.right(aux));
					
				}else if(!(this.tree.hasLeft(aux))){
					Position<E> temp = aux;
					if(!(tree.isLeaf(aux))){
						der = this.tree.subTree(this.tree.right(aux));
					
						t.attachLeft(aux, der);
					
						nodeQueue.add(this.tree.left(temp));
					}else{
						t.attachLeft(aux, der);
					}
					
				}else if(!(this.tree.hasRight(aux))){
					if(tree.isLeaf(aux)){
						izq = this.tree.subTree(this.tree.left(aux));
					
						t.attachRight(aux, izq);
					
						nodeQueue.add(this.tree.left(aux));
					}else{
						t.attachRight(aux, izq);
					}
				}else{
					
					return t;
				}

			}
			return t;
	}

	/** 
	  * Determines whether the element e is the tree or not
	*/
	public boolean contains (E e) {
		boolean encontrado = false;
		if(tree.isEmpty()){
			return encontrado;
		}else{
			BreadthFirstTreeIterator<E> it = new BreadthFirstTreeIterator<E>(tree);
			
			while((it.hasNext()) && !(encontrado)){
				if(it.next().getElement().equals(e)){
					encontrado = true;
				}
			}
		}
		return encontrado;
	}
	
	/** 
	  * Determines the level of a node in the tree (root is located at level 1)
	*/
	public int level(Position<E> p) {
		int cont = 1;
		if(tree.isEmpty()){
			return 0;
		}else if(tree.isRoot(p)){
			return cont;
		}else{
			Position<E> aux = tree.parent(p);
			while(!(tree.isRoot(aux))){
				cont++;
				aux = tree.parent(aux);
			}
			return cont +1;
		}
	}
}

