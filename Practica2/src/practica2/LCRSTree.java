package practica2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import material.Position;
import material.tree.BreadthFirstTreeIterator;
import material.tree.Tree;
import material.tree.narytree.LinkedTree;
import material.tree.narytree.NAryTree;

/**
*
* @author danie
*/

public class LCRSTree<E> implements NAryTree<E> {
	private LCRSTreeNode<E> root;
	
	private class LCRSTreeNode<E> implements Position<E>{
		private E elem;
		private LCRSTreeNode<E> parent;
		private LCRSTreeNode<E> lchildren;
		private List<LCRSTreeNode<E>> rbrother;
		private LCRSTree<E> mitree;
		
		public LCRSTreeNode(E e, LCRSTreeNode<E> p, LCRSTreeNode<E> c, List<LCRSTreeNode<E>> b, LCRSTree<E> mt) {
	         this.elem = e;
	         this.parent = p;
	         this.lchildren = c;
	         this.rbrother = b;
	         this.mitree = mt;
		}

		@Override
		public E getElement() {
			// TODO Auto-generated method stub
			return elem;
		}
		
        /**
         * Sets the element stored at this position
         */
        public void setElement(E e) {
            elem = e;
        }

        /**
         * Returns the children of this position
         */
        public LCRSTreeNode<E> getChildren() {
            return lchildren;
        }

        /**
         * Sets the right child of this position
         */
        public final void setChildren(LCRSTreeNode<E> c) {
            lchildren = c;
        }

        /**
         * Returns the parent of this position
         */
        public LCRSTreeNode<E> getParent() {
            return parent;
        }

        /**
         * Sets the parent of this position
         */
        public final void setParent(LCRSTreeNode<E> v) {
            parent = v;
        }
        
        /**
         * Returns the parent of this position
         */
        public List<LCRSTreeNode<E>> getBrother() {
            return rbrother;
        }

        /**
         * Sets the parent of this position
         */
        public final void setBrother(List<LCRSTreeNode<E>> b) {
            rbrother = b;
        }
        
        /**
         * Returns the parent of this position
         */
        public LCRSTree<E> getMiTree() {
            return mitree;
        }

        /**
         * Sets the parent of this position
         */
        public final void setMiTree(LCRSTree<E> mt) {
            mitree = mt;
        }
	}
		  
	/**
     * Creates an empty tree.
     */
    public LCRSTree() {
    		root = null;
    }

    /**
     * Returns whether the tree is empty.
     *
     * @return True if is empty.
     *
     */
    @Override
    public boolean isEmpty() {
    	return (root == null);
    }
    
    private LCRSTreeNode<E> checkPosition(Position<E> t) throws RuntimeException{
    	if(!(t == null) || (t instanceof LCRSTreeNode)) {
    		LCRSTreeNode<E> aux = (LCRSTreeNode<E>) t;
    			return aux;
    	}else{
    		throw new RuntimeException("La posicion no es valida");
    	}
    }
    /**
     * Returns whether a node is internal.
     * @param v
     * @return 
     */
    @Override
    public boolean isInternal(Position<E> v) {
    	LCRSTreeNode<E> aux = checkPosition(v);
    	
    	return (aux.getChildren() != null);
    	//(!(aux.getParent() == null) && !(aux.getChildren() == null));
    }

    /**
     * Returns whether a node is external.
     * @param p
     * @return 
     */
    @Override
    public boolean isLeaf(Position<E> p) {
    	
    	LCRSTreeNode<E> aux = checkPosition(p);
    	return (!this.isInternal(aux));
    	
    }

    /**
     * Returns whether a node is the root.
     * @param p
     * @return 
     */
    @Override
    public boolean isRoot(Position<E> p) {
    	
    	LCRSTreeNode<E> aux = checkPosition(p);
    	return (aux.getParent() == null);

    }

    /**
     * Returns the root of the tree.
     * @return 
     */
    @Override
    public Position<E> root() {
    	if(!(root == null)){
    		return root;
    	}
    	throw new RuntimeException("El arbol esta vacio");
    }

    /**
     * Returns the parent of a node.
     * @param p
     * @return 
     */
    @Override
    public Position<E> parent(Position<E> p) {
    	LCRSTreeNode<E> aux = checkPosition(p);
    	if(!(aux.getParent() == null)){
    		return aux.getParent();
    	}
    	throw new RuntimeException("El nodo no tiene padre");
    }

    /**
     * Returns an iterable collection of the children of a node.
     * @param p
     */
    @Override
    public Iterable<? extends Position<E>> children(Position<E> p) {
    	LCRSTreeNode<E> aux = checkPosition(p);
    	List<LCRSTreeNode<E>> l = new ArrayList();
    	ArrayList<Position<E>> list =  new ArrayList();
    	
    	if (!(aux.getChildren() == null)){
    		
    		LCRSTreeNode<E> e = aux.getChildren();
    		list.add(e);
    		
    		l = e.getBrother();
    		
    		for(Position<E> n: l){
    			list.add(n);
    		}

    	}
    		return list;
    }

    /**
     * Returns an iterator of the elements stored at the nodes. The nodes are
     * visited according to a breath-first search
     */
    @Override
    public Iterator<Position<E>> iterator() {
    	return new BreadthFirstTreeIterator<>(this);
    }

 
    /**
     * Replaces the element at a node.
     */
    @Override
    public E replace(Position<E> p, E e) {
    	if((!(this == null)) && (!(this.isEmpty()))){
    		LCRSTreeNode<E> aux = checkPosition(p);
    		E temp = p.getElement();
    		aux.setElement(e);
    		return temp;
    	}
    		throw new RuntimeException("No hay nodos coincidentes");
    }

    /**
     * Adds a root node to an empty tree
     */
    @Override
    public Position<E> addRoot(E e) {
    	LCRSTreeNode<E> newRoot = new LCRSTreeNode<E>(e, null, null, null, this);
    	
    	if(root == null){
    		this.root = newRoot;
    	}else{
    		throw new RuntimeException("No es arbol vacio");
    	}
		return newRoot;
    }

    /**
     * Swap the elements at two nodes
     */
    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
    	if(!(this.isEmpty())){
    		LCRSTreeNode<E> aux1 = checkPosition(p1);
    		LCRSTreeNode<E> aux2 = checkPosition(p2);
    		
    		E e1 = p1.getElement();
    		//E e2 = aux2.getElement();
    		
    		aux1.setElement(p2.getElement());
    		aux2.setElement(e1);
    	}else{
    		throw new RuntimeException("Arbol Vacio");
    	}
    }

    /**
     * Add a new node whose parent is pointed by a given position.
     *
     * @param p The position of the parent, e the element stored in the new
     * created node.
     */
    @Override
    public Position<E> add(E element, Position<E> p) { //reimplementar con el Iterable children
    	
    	LCRSTreeNode<E> newNode;
    	LCRSTreeNode<E> parent = checkPosition(p);
    	
    	if(parent.getChildren() == null){
    		newNode = new LCRSTreeNode<>(element, parent, null, new ArrayList<LCRSTreeNode<E>>(), this);
    		parent.setChildren(newNode);
    	}else{
    		List<LCRSTreeNode<E>> l = parent.getChildren().getBrother();
    		newNode = new LCRSTreeNode<>(element, parent, null, new ArrayList<LCRSTreeNode<E>>(), this);
    		
			l.add(newNode);    		
    	}
        return newNode;
    }

    /**
     * Add a new node whose parent is pointed by a given position, 
     * and set the child at the position n if possible.
     *
     * @param p The po-sition of the parent, e the element stored in the new
     * created node.
     */
    @Override
    public Position<E> add(E element, Position<E> p, final int n) {
    	LCRSTreeNode<E> newNode;
    	LCRSTreeNode<E> parent = checkPosition(p);
    	
    	if(parent.getChildren() == null){
    		newNode = new LCRSTreeNode<>(element, parent, null, null, this);
    		parent.setChildren(newNode);
    	}else{
    		List<LCRSTreeNode<E>> l = parent.getChildren().getBrother();
    		newNode = new LCRSTreeNode<>(element, parent, null, null, this);
    		
    		if (n > l.size())
                throw new RuntimeException("The element can't be inserted at specified position.");
            
    		l.add(n, newNode);
    		l.add(newNode);
    		
    	}
        return newNode;
    }
    
    
    
    /**
     * Remove a node and its corresponding subtree rooted at node.
     *
     * @param p The position of the node to be removed.
     */
    @Override
    public void remove(Position<E> p) {
    	/*LCRSTreeNode<E> aux = checkPosition(p);
    	
    	if(!(aux.getParent() == null)){
    		LCRSTreeNode<E> nodo = aux.getChildren();

    		while(!(nodo.getBrother() == null)){
    			LCRSTreeNode<E> nodoaux = nodo.getBrother();
    			nodo.setBrother(null);
    			nodo = nodoaux;
    		}
    		aux.getChildren().setChildren(null);
    		aux.setParent(null);
    	}else{
    		this.root = null;    		
    	}*/
    	 LCRSTreeNode<E> node = checkPosition(p);
         if (node.getParent() != null) {
             LCRSTreeNode<E> parent = node.getParent();
             
             if(parent.getChildren().equals(node)){
            	 parent.setChildren(null);
            	 node.setParent(null);
             }else{
            	 List<LCRSTreeNode<E>> aux = parent.getChildren().getBrother();
            	 LCRSTreeNode<E> temp = null;
            	 
            	 for(LCRSTreeNode<E> n: aux){
            		 if(n.equals(node)) 
            			 temp = n;
            	 }
            	 aux.remove(temp);
             }
             
         } else {
             this.root = null;
         }
    }
    
    /**
     * Create un new tree from node v.
     *
     * @param v new root node
     * @return  The new tree.
     */
    public LCRSTree<E> subTree(Position<E> v) {
    	remove(v);
    	LCRSTreeNode<E> newRoot = checkPosition(v);
    	newRoot.parent = null;
    	LCRSTree<E> tree = new LCRSTree<>();
    	tree.root = newRoot;
    	return tree;
    }    
    
    /**
     * Attach tree t as children of node p
     * @param p
     * @param t 
     */
    public void attach(Position<E> p, Tree<E> t) {
    	LCRSTreeNode<E> aux = checkPosition(p);
    	LCRSTree<E> tree = (LCRSTree<E>) t;
    	
    	if(tree != null && !tree.isEmpty()){
    		if(aux.getChildren() == null){
    			aux.setChildren(tree.root);
    		}else{
    			LCRSTreeNode<E> r = checkPosition(t.root());
    			
    			if(aux.getChildren() == null){
    				aux.setChildren(r);
    			}else{
    				List<LCRSTreeNode<E>> nodo = aux.getChildren().getBrother();
    				nodo.add(r);
    			}
    			r.setParent(aux);
				tree.root = null;
    		}
    	}
    	if(tree == this)
    		throw new RuntimeException("Un arbol no puede adjuntarse a si mismo");
    }

}
