package practica3;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.management.RuntimeErrorException;

import material.Position;
import material.tree.BreadthFirstTreeIterator;
import material.tree.Tree;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.InorderBinaryTreeIterator;
import material.tree.binarytree.LinkedBinaryTree;

public class ArrayBinaryTree<E> implements BinaryTree<E> {
	private int size = 0;
	private int cap = 200;
	private ABTPos<E> tree[];
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void setCap(int cap){
		this.cap = cap;
	}

	public int getSize() {
		return size;
	}

	public int getCap(){
		return cap;
	}

	private class ABTPos<E> implements Position<E>{
		private int index;
		private E element;
		
		public ABTPos(int index, E e){
			this.index = index;
			this.element = e;
		}
		
		
		public E getElement() {
			return element;
		}
		
		public int getIndex() {
			return index;
		}
		
		public void setElement(E element) {
			this.element = element;
		}
		
		public void setIndex(int index) {
			this.index = index;
		}
	}

    private ABTPos<E> checkPosition(Position<E> t) throws RuntimeException{
    	if(!(t == null) || (t instanceof ABTPos)) {
    		ABTPos<E> aux = (ABTPos<E>) t;
    		return aux;
    	}
    	throw new RuntimeException("La posicion no es valida");
    }
	
	public ArrayBinaryTree(){
		this.tree  = (ABTPos<E> []) new ABTPos[cap];
	}
    
	public ArrayBinaryTree(int capac){
		this.tree  = (ABTPos<E> []) new ABTPos[capac];
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (size == 0);
	}

	@Override
	public Position<E> parent(Position<E> v) {
		// TODO Auto-generated method stub
		ABTPos<E> aux = checkPosition(v);	
		if(!(this.size == 0) && (this.tree[aux.getIndex()/2] != null)){

				if(aux.getIndex() % 2 == 0)
					return (Position) this.tree[aux.getIndex()/2];
				else{
					return (Position) this.tree[(aux.getIndex()/2)];
				}
		}
		throw new RuntimeException("No tiene padre");
	}

	@Override
	public Iterable<? extends Position<E>> children(Position<E> v) {
		// TODO Auto-generated method stub
		ABTPos<E> aux = checkPosition(v);
		ArrayList<Position<E>> l = new ArrayList<>();
		int pos1 = 0; int pos2 = 0;
		boolean esta1 = false; boolean esta2 = false;
		
		while(pos1 < size && !esta1){
			pos1++;
			esta1 = this.tree[pos1].equals(this.tree[aux.getIndex()*2]);
			
		}
		
		while(pos2 < size && !esta2){
			pos2++;
			esta2 = this.tree[pos2].equals(this.tree[(aux.getIndex()*2)+1]);
			
		}
		if(esta1 && esta2)
			if((this.tree[(aux.getIndex()*2)+1] != null && ( this.tree[aux.getIndex()*2]) != null)){
				
				l.add((Position) this.tree[pos1]);
				l.add((Position) this.tree[pos2]);
			
			}else if(((this.tree[(aux.getIndex()*2)+1] == null && ( this.tree[aux.getIndex()*2]) != null))){
			
				l.add(this.tree[pos2]);
		
			}else if((this.tree[(aux.getIndex()*2)+1] != null && ( this.tree[aux.getIndex()*2]) == null)){
			
				l.add(this.tree[pos1]);
		
			}
		return l;
	}

	@Override
	public Iterator<Position<E>> iterator() {
		// TODO Auto-generated method stub
		return new InorderBinaryTreeIterator<>(this);
	}

	@Override
	public Position<E> left(Position<E> v) {
		// TODO Auto-generated method stub
		int cont = 1;
		int i = 1;
		boolean esta = false;
		ABTPos<E> aux = checkPosition(v);
		if(this.tree[aux.getIndex()*2] != null){
				cont = aux.getIndex()*2;
				return (Position) this.tree[cont];
		}
		throw new RuntimeException("No tiene hijo izquierdo");
	}

	@Override
	public Position<E> right(Position<E> v) {
		// TODO Auto-generated method stub
		int cont = 1;
		int i = 1;
		boolean esta = false;
		ABTPos<E> aux = checkPosition(v);
		if(this.tree[(aux.getIndex()*2) + 1] != null){
				cont = (aux.getIndex()*2) + 1;
		return (Position) this.tree[cont];
		}
		throw new RuntimeException("No tiene hijo derecho");
	}

	@Override
	public boolean hasLeft(Position<E> v) {
		// TODO Auto-generated method stub

		boolean esta = false;
		ABTPos<E> aux = checkPosition(v);
		if(aux.getIndex()*2 < this.cap)	 	
			esta = (this.tree[aux.getIndex()*2] != null);
		return esta;
	}

	@Override
	public boolean hasRight(Position<E> v) {
		// TODO Auto-generated method stub
		int cont = 0;
		int i = 0;
		boolean esta = false;
		ABTPos<E> aux = checkPosition(v);
		if((aux.getIndex()*2)+1 < this.cap)	 	
			esta = (this.tree[aux.getIndex()*2] != null);
		return esta;
	}

	@Override
	public boolean isInternal(Position<E> v) {
		// TODO Auto-generated method stub
		int pos = 1;
		boolean esta = false;
		boolean interno = false;
		int parent_pos;
		
		ABTPos<E> aux = checkPosition(v);
		while(pos < cap && !esta){
			if(this.tree[pos] != null){
				esta = this.tree[pos].equals(v);
				pos++;
			}else{
				pos++;
			}
		}
				
		if(esta)
			if(pos % 2 == 0)
				interno = (((this.tree[aux.getIndex()*2]) != null || (this.tree[(aux.getIndex()*2)+1] != null) && (!(this.tree[aux.getIndex()/2] == null))));
			else{
				interno = (((this.tree[aux.getIndex()*2]) != null || (this.tree[(aux.getIndex()*2)+1] != null) && (!(this.tree[aux.getIndex()/2] == null))));
			}
		return interno;
		//return (aux.hasLeft(v) && aux.hasRight(v));
	}

	@Override
	public boolean isLeaf(Position<E> p) {
		// TODO Auto-generated method stub
		return (!(isInternal(p)));
	}

	@Override
	public boolean isRoot(Position<E> p) {
		// TODO Auto-generated method stub
		ABTPos<E> aux = checkPosition(p);
		return (this.tree[1].getIndex() == aux.getIndex());
	}

	@Override
	public Position<E> root() {
		// TODO Auto-generated method stub
		return this.tree[1];
	}

	@Override
	public E replace(Position<E> p, E e) {
		// TODO Auto-generated method stub
		ABTPos<E> aux = checkPosition(p);
		this.tree[aux.getIndex()].setElement(e);;
		return e;
	}

	@Override
	public Position<E> sibling(Position<E> p) {
		// TODO Auto-generated method stub
		ABTPos<E> aux = checkPosition(p);
		
		if(aux.getIndex() % 2 == 0)
			if(this.tree[aux.getIndex()+1] != null && aux.getIndex()/2 != 0)
				return (Position) this.tree[aux.getIndex()+1];
		else 
			if(this.tree[aux.getIndex()-1] != null && (aux.getIndex()/2) != 0)
				return (Position) this.tree[aux.getIndex()+1];		
			
		throw new RuntimeException("El nodo dado no tiene hermano");
	}

	@Override
	public Position<E> addRoot(E e) {
		// TODO Auto-generated method stub
		if(this.tree != null && !(this.tree.length == 0)){
			ABTPos<E> aux = new ABTPos<>(1,e);
			if(this.size == 0){
				this.tree[1] = aux;
				size++;
				return aux;
			}
			throw new RuntimeException("Ya existe una raiz");
		}
		throw new RuntimeException("Arbol no valido");
	}

	@Override
	public Position<E> insertLeft(Position<E> p, E e) {
		// TODO Auto-generated method stub
			ABTPos<E> aux = checkPosition(p);
			int pos = 0;
			boolean esta = false;
			
			while(pos < cap && !esta){
				pos++;
				if(this.tree[pos] == null)
					pos++;
				else{
					esta = this.tree[pos].equals(p);
				}
			}
			
			ABTPos<E> auxleft = new ABTPos<E>((aux.getIndex()*2), e);
			
			if(esta){
				if((this.tree[pos*2] != null)){
					throw new RuntimeException("Node already has a left child");
				}else{
					
					this.tree[pos*2] = auxleft;
				}
				size++;
			}
			return auxleft;
	}

	@Override
	public Position<E> insertRight(Position<E> p, E e) {
		// TODO Auto-generated method stub
			ABTPos<E> aux = checkPosition(p);
			int pos = 0;
			boolean esta = false;
			
			while(pos < cap && !esta){
				pos++;
				if(this.tree[pos] == null)
					pos++;
				else{
					esta = this.tree[pos].equals(p);
				}
			}
			
			ABTPos<E> auxright = new ABTPos<E>(((aux.getIndex()*2) + 1), e);
			
			if(esta){
				if(this.tree[pos*2+1] != null){
					throw new RuntimeException("Node already has a right child");
				}else{
					this.tree[pos*2+1] = auxright;
				}
				size++;
			}
			return auxright;
	}

	@Override
	public E remove(Position<E> p) {
		// TODO Auto-generated method stub
		if(this.tree != null && !(this.size == 0)){
			ABTPos<E> aux = checkPosition(p);
			
			if((this.tree[aux.getIndex()*2] == null && this.tree[(aux.getIndex()*2)+1] == null)){
				
				this.tree[aux.getIndex()] = null;
				size--;
			
			}else if((!(this.tree[aux.getIndex()*2] == null) && (this.tree[(aux.getIndex()*2)+1] == null))){
				Position<E> pos = this.parent(this.tree[aux.getIndex()]);
				Deque<ABTPos<E>> nodeQueue = new LinkedList<>();
				
				if(this.hasLeft(p))
					nodeQueue.add((ABTPos<E>) this.left(p));
				if(this.hasRight(p))
					nodeQueue.add((ABTPos<E>) this.left(p));
				
				this.tree[aux.getIndex()] = null;
				size--;
				
				while(!(nodeQueue.isEmpty())){
					ABTPos<E> node = nodeQueue.pollFirst();
					if(node != null){
						if(this.tree[node.getIndex()].getIndex() % 2 == 0){
							pos = this.parent(this.insertLeft(pos, node.getElement()));
						}else if(this.tree[node.getIndex()].getIndex() % 2 != 0){
							pos = this.parent(this.insertRight(pos, node.getElement()));
						}
					}
				}
				
			}else if(this.tree[aux.getIndex()*2] == null && !(this.tree[(aux.getIndex()*2)+1] == null)){
				
				Position<E> pos = this.parent(this.tree[aux.getIndex()]);
				Deque<ABTPos<E>> nodeQueue = new LinkedList<>();
				
				if(this.hasLeft(p))
					nodeQueue.add((ABTPos<E>) this.left(p));
				if(this.hasRight(p))
					nodeQueue.add((ABTPos<E>) this.left(p));
				
				this.tree[aux.getIndex()] = null;
				size--;
				
				while(!(nodeQueue.isEmpty())){
					ABTPos<E> node = nodeQueue.pollFirst();
					if(node != null){
						if(this.tree[node.getIndex()].getIndex() % 2 == 0){
							pos = this.parent(this.insertLeft(pos, node.getElement()));
						}else if(this.tree[node.getIndex()].getIndex() % 2 != 0){
							pos = this.parent(this.insertRight(pos, node.getElement()));
						}
					}
				}
				
			}else{
				throw new RuntimeException("El nodo a borrar tiene dos hijos, no se puede borrar");
			}
			return aux.getElement();
		}
			throw new RuntimeException("El arbol ya esta vacio");
	}

	@Override
	public void swap(Position<E> p1, Position<E> p2) {
		// TODO Auto-generated method stub
		if(this != null && !(this.isEmpty())){
			ABTPos<E> aux1 = checkPosition(p1);
			ABTPos<E> aux2 = checkPosition(p2);
		
			ABTPos<E> nodaux1 = this.tree[aux1.getIndex()];
			ABTPos<E> nodaux2 = this.tree[aux2.getIndex()];
		
			E e1 = nodaux1.getElement();
			E e2 = nodaux2.getElement();
		
			this.tree[nodaux1.getIndex()].setElement(e2);
			this.tree[nodaux2.getIndex()].setElement(e1);
		}else{
			throw new RuntimeException("Arbol Vacio");
		}
	}
	@Override
	public BinaryTree<E> subTree(Position<E> v) {
		// TODO Auto-generated method stub
		//ArrayBinaryTree<E>.ABTPos<E> t[];
		Deque<ABTPos> nodeQueue = new LinkedList<>();
		Position<E> p;
		ArrayBinaryTree<String> t = new ArrayBinaryTree<>();
		int i = 1;
		
		ArrayBinaryTree<String>.ABTPos<String> aux = (ArrayBinaryTree<String>.ABTPos<String>) checkPosition(v);
		
		if(aux != null){
			nodeQueue.add(aux);
					
			while(!(nodeQueue.isEmpty())){
				aux = nodeQueue.pollFirst();
				if(aux != null){
					nodeQueue.add(this.tree[aux.getIndex()*2]);
					nodeQueue.add(this.tree[(aux.getIndex()*2)+1]);
			
					t.tree[i] = aux;
			
					i++;
				}else{
					i++;
				}
			}
		/*if(checkPosition(v) != null){
			int pos = 2; 
			ArrayBinaryTree<String> t = new ArrayBinaryTree<>();
			//ArrayBinaryTree<ABTPos<E>>[] sol;
			p = v;
			ABTPos<E> aux = checkPosition(v);
			
			nodeQueue.add(this.tree[aux.getIndex()]);
			
			while(!(nodeQueue.isEmpty())){
				ABTPos nodeaux = nodeQueue.pollFirst();
				
				if(nodeaux != null){
					
					if(nodeaux.equals(this.tree[aux.getIndex()])){

						p = (Position<E>) t.addRoot((String) this.tree[aux.getIndex()].getElement());
					
						nodeQueue.addLast(this.tree[nodeaux.getIndex()*2]);
						nodeQueue.addLast(this.tree[nodeaux.getIndex()*2+1]);
					
					} else{
										
						if(nodeaux.getIndex() % 2 == 0){
				
							//t.insertLeft((Position) p,(String) nodeaux.getElement());
							p = (Position<E>) t.parent(t.insertLeft((Position) p,(String) nodeaux.getElement()));
						
							pos++;
						
							nodeQueue.addLast(this.tree[nodeaux.getIndex()*2]);
							nodeQueue.addLast(this.tree[nodeaux.getIndex()*2+1]);
											
						}else if(nodeaux.getIndex() % 2 != 0){
						
							//t.insertRight((Position<String>) p, (String) nodeaux.getElement());
							p = (Position<E>) t.parent(t.insertRight((Position) p, (String) nodeaux.getElement()));
						
							pos++;

							nodeQueue.addLast(this.tree[nodeaux.getIndex()*2]);
							nodeQueue.addLast(this.tree[nodeaux.getIndex()*2+1]);
						
						}
					}		
				}
			/*i = aux.getIndex();
			
			while(i < this.size){
				if(i != aux.getIndex()){
					t.addRoot((String) this.tree[i].getElement());
					i = 2*i;
				}else{
					if(this.tree[i] != null){
						if(this.tree[i].getIndex() % 2 == 0){
							t.insertLeft((Position) this.tree[i/2], (String) this.tree[i].getElement());
						}else{
							t.insertRight((Position) this.tree[i/2], (String) this.tree[i].getElement());
						}
					}
					i++;
					if(aux.getIndex() != i/2){
						i--;
						i = (2*i);
						aux = this.tree[i/2];
					}
				} */	
			return (BinaryTree<E>) t;
		}
		throw new RuntimeException("Posicion dada invalida");
	}

	@Override
	public void attachLeft(Position<E> p, BinaryTree<E> tree) {
		// TODO Auto-generated method stub
		ABTPos<E> aux = checkPosition(p);
		ArrayBinaryTree<E> t = (ArrayBinaryTree<E>) tree;
		
		if(tree == this){
			throw new RuntimeException("Cannot attach a tree over himself");
		}
			
		Position<E> pos = p;
		int i = 1;
	
		BreadthFirstTreeIterator<ABTPos<E>> it = new BreadthFirstTreeIterator(tree);
			
		while(it.hasNext()){
			Position<E> node = (Position) it.next();
			
			if(node != null){
				if(node.equals(t.root())){
					pos = this.insertLeft(pos, (E) node.getElement());
				}else if(node.equals(t.left(t.root()))){
					pos = this.insertLeft(pos, (E) node.getElement());
					pos = this.parent(pos);
				}else{
					pos = this.insertRight(pos, (E) node.getElement());
					pos = this.parent(pos);
				}
			}
		}
	}

	@Override
	public void attachRight(Position<E> p, BinaryTree<E> tree) {
		// TODO Auto-generated method stub
		ArrayBinaryTree t = (ArrayBinaryTree) tree;
		ABTPos<E> aux = checkPosition(p);
		
		if(tree == this){
			throw new RuntimeException("Cannot attach a tree over himself");
		}
			
		Position<E> pos = p;
		int i = 1;
	
		BreadthFirstTreeIterator<ABTPos<E>> it = new BreadthFirstTreeIterator(tree);

			
		while(it.hasNext()){
			Position<E> node = (Position) it.next();
			
			if(node != null){
				if(node.equals(tree.root())){
					pos = this.insertRight(pos, (E) node.getElement());
				}else if(node.equals(t.left(t.root()))){
					pos = this.insertRight(pos, (E) node.getElement());
					pos = this.parent(pos);
				}else{
					pos = this.insertLeft(pos, (E) node.getElement());
					pos = this.parent(pos);
				}
			}
		}		
	}
}
