package material.tree.binarysearchtree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import material.Position;
import material.tree.BreadthFirstTreeIterator;
import material.tree.binarytree.InorderBinaryTreeIterator;

/**
 * AVLTree class - implements an AVL Tree by extending a binary search tree.
 * @author A. Duarte, J. Vélez
 */
public class AVLTree<E> implements BinarySearchTree<E> {

    //Esta clase es necesaria para guardar el valor de la altura AVL en los nodos BTNodes
    private class AVLInfo<T> implements Comparable<AVLInfo<T>>, Position<T> {

        private int height;
        private T element;
        private Position<AVLInfo<T>> pos;

        AVLInfo(T element) {
            this.element = element;
            this.pos = null;
            this.height = 1;
        }

        public void setTreePosition(Position<AVLInfo<T>> pos) {
            this.pos = pos;
        }

        public Position<AVLInfo<T>> getTreePosition() {
            return this.pos;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        @Override
        public T getElement() {
            return element;
        }

        @Override
        public int compareTo(AVLInfo<T> o) {
            if (element instanceof Comparable && o.element instanceof Comparable) {
                Comparable<T> c1 = (Comparable<T>) element;
                return c1.compareTo(o.element);

            } else {
                throw new ClassCastException("Element is not comparable");
            }
        }

        @Override
        public String toString() {
            return element.toString();
        }

        @Override
        public boolean equals(Object obj) {
            AVLInfo<T> info = (AVLInfo<T>) obj;
            return element.equals(info.getElement());
        }

    }

    private class AVLTreeIterator<T> implements Iterator<Position<T>> {

        private Iterator<Position<AVLInfo<T>>> it;

        public AVLTreeIterator(Iterator<Position<AVLInfo<T>>> iterator) {
            this.it = iterator;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<T> next() {
            Position<AVLInfo<T>> aux = it.next();
            return aux.getElement();
        }

        @Override
        public void remove() {
            it.remove();
        }
    }

    private LinkedBinarySearchTree<AVLInfo<E>> bst = new LinkedBinarySearchTree<>();
    private Reestructurator reestructurator = new Reestructurator();
    
    @Override
    public Position<E> find(E value) {
        AVLInfo<E> searchedValue = new AVLInfo<>(value);
        Position<AVLInfo<E>> output = bst.find(searchedValue);
        return (output == null) ? null : output.getElement();
    }

    @Override
    public Iterable<Position<E>> findAll(E value) {
        AVLInfo<E> searchedValue = new AVLInfo<>(value);
        List<Position<E>> aux = new ArrayList<>();
        for (Position<AVLInfo<E>> n : bst.findAll(searchedValue)) {
            aux.add(n.getElement());
        }
        return aux;
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    @Override
    public int size() {
        return bst.size();
    }

    /**
     * Returns whether a node has balance factor between -1 and 1.
     */
    private boolean isBalanced(Position<AVLInfo<E>> p) {
        int leftHeight = (bst.binTree.hasLeft(p)) ? bst.binTree.left(p).getElement().getHeight() : 0;
        int rightHeight = (bst.binTree.hasRight(p)) ? bst.binTree.right(p).getElement().getHeight() : 0;
        final int bf = leftHeight - rightHeight;
        return ((-1 <= bf) && (bf <= 1));
    }

    /**
     * Return a child of p with height no smaller than that of the other child.
     */
    private Position<AVLInfo<E>> tallerChild(Position<AVLInfo<E>> p) {

        int leftHeight = (bst.binTree.hasLeft(p)) ? bst.binTree.left(p).getElement().getHeight() : 0;
        int rightHeight = (bst.binTree.hasRight(p)) ? bst.binTree.right(p).getElement().getHeight() : 0;

        if (leftHeight > rightHeight) {
            return bst.binTree.left(p);
        } else if (leftHeight < rightHeight) {
            return bst.binTree.right(p);
        }

        // equal height children - break tie using parent's type
        if (bst.binTree.isRoot(p)) {
            return bst.binTree.left(p);
        }

        if (p == bst.binTree.left(bst.binTree.parent(p))) {
            return bst.binTree.left(p);
        } else {
            return bst.binTree.right(p);
        }
    }

    private void calculateHeight(Position<AVLInfo<E>> p) {
        int leftHeight = (bst.binTree.hasLeft(p)) ? bst.binTree.left(p).getElement().getHeight() : 0;
        int rightHeight = (bst.binTree.hasRight(p)) ? bst.binTree.right(p).getElement().getHeight() : 0;
        p.getElement().setHeight(1 + Math.max(leftHeight, rightHeight));
    }

    /**
     * Rebalance method called by insert and remove. Traverses the path from p
     * to the root. For each node encountered, we recompute its height and
     * perform a trinode restructuring if it's unbalanced.
     */
    private void rebalance(Position<AVLInfo<E>> zPos) {
        if (bst.binTree.isInternal(zPos)) {
            calculateHeight(zPos);
        } else {
            zPos.getElement().setHeight(1);
        }
        while (!bst.binTree.isRoot(zPos)) { // traverse up the tree towards the
            // root
            zPos = bst.binTree.parent(zPos);
            calculateHeight(zPos);
            if (!isBalanced(zPos)) {
                // perform a trinode restructuring at zPos's tallest grandchild
                Position<AVLInfo<E>> xPos = tallerChild(tallerChild(zPos));
                zPos = reestructurator.restructure(xPos, bst.binTree);
                calculateHeight(bst.binTree.left(zPos));
                calculateHeight(bst.binTree.right(zPos));
                calculateHeight(zPos);
            }
        }
    }

    @Override
    public Position<E> insert(E e) {
        AVLInfo<E> aux = new AVLInfo<>(e);
        Position<AVLInfo<E>> internalNode = bst.insert(aux);
        aux.setTreePosition(internalNode);
        rebalance(internalNode);
        return aux;
    }

    @Override
    public void remove(Position<E> pos) throws IllegalStateException {
        AVLInfo<E> avlInfo = (AVLInfo<E>) pos;
        Position<AVLInfo<E>> treePos = avlInfo.getTreePosition();
        Position<AVLInfo<E>> parent = null;
                
        if (bst.binTree.isLeaf(treePos) || !bst.binTree.hasLeft(treePos) || !bst.binTree.hasRight(treePos)) {
            if (bst.binTree.root() != treePos)
                parent = bst.binTree.parent(treePos);
            bst.binTree.remove(treePos);
        } else {
            Position<AVLInfo<E>> sucessor = bst.sucessor(treePos);
            bst.binTree.swap(sucessor,treePos);
            if (bst.binTree.root() != treePos)
                parent = bst.binTree.parent(treePos);
            bst.binTree.remove(treePos);
        }
        bst.size--;
        if (parent != null) {
            rebalance(parent);
        }       
    }

    @Override
    public Iterator<Position<E>> iterator() {
        Iterator<Position<AVLInfo<E>>> bstIt = bst.iterator();
        AVLTreeIterator<E> it = new AVLTreeIterator<>(bstIt);
        return it;
    }

    /**
     *	@author danie
     */
    
    public Position<E> first() {
    	Position<AVLInfo<E>> pos = bst.binTree.root();
    	
    	while(bst.binTree.hasLeft(pos)){
    		pos = bst.binTree.left(pos);
    	}
        return pos.getElement();
    }

    public Position<E> last() {
    	Position<AVLInfo<E>> pos = bst.binTree.root();
    	
    	while(bst.binTree.hasRight(pos)){
    		pos = bst.binTree.right(pos);
    	}
        return pos.getElement();
    }

	@Override
	public Iterable<Position<E>> successors(Position<E> pos) {
		
		Deque<Position<E>> nodeQueue = new LinkedList();
		ArrayList<Position<E>> l = new ArrayList<>();
		
		nodeQueue.add(pos);
		l.add(pos);
		
		while(!nodeQueue.isEmpty()){
			AVLInfo<E> aux = (AVLInfo) nodeQueue.pollFirst();
			Position<AVLInfo<E>> treePos = aux.getTreePosition();
			Position<AVLInfo<E>> newPos = bst.sucessor(treePos);
			
			if(newPos != null){
				l.add(newPos.getElement());
				nodeQueue.add(newPos.getElement());
			}	
		}
		
		return l;
	}

	@Override
	public Iterable<Position<E>> predecessors(Position<E> pos) {
				
		Deque<Position<E>> nodeQueue = new LinkedList();
		ArrayList<Position<E>> l = new ArrayList<>();
		Position<AVLInfo<E>> min = bst.minimum(bst.binTree.root());
		AVLInfo<E> auxPos = (AVLInfo) pos;
		Position<AVLInfo<E>> treePos2 = auxPos.getTreePosition();
		
		nodeQueue.add(min.getElement());
		l.add(min.getElement());
		
		while(!nodeQueue.isEmpty()){
			AVLInfo<E> aux = (AVLInfo) nodeQueue.pollFirst();
			Position<AVLInfo<E>> treePos = aux.getTreePosition();
			Position<AVLInfo<E>> newPos = bst.sucessor(treePos);
			
			if(newPos != null && !newPos.equals(bst.sucessor(treePos2))){
				l.add(newPos.getElement());
				nodeQueue.add(newPos.getElement());
				
			}	
		}
		
		return l;
	}
	
}
