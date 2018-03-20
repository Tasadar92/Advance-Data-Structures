/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.util.*;

import material.Position;
import material.tree.BreadthFirstTreeIterator;
import material.tree.Tree;
import material.tree.narytree.LinkedTree;

/**
 *
 * @author jvelez
 * @param <T>
 */
public class LeafIterator <T> implements Iterator<Position<T>> {
	private Deque<Position<T>> nodeQueue = new LinkedList<>();
    private Tree<T> tree;
    Position<T> aux;
    
    public LeafIterator(Tree<T> tree) {
    	this.tree = tree;
    	nodeQueue.add(tree.root());
    }
            
    @Override
    public boolean hasNext() {
        return !(this.nodeQueue.isEmpty());
    }

    @Override
    public Position<T> next() {
    	 Position<T> aux = nodeQueue.pollFirst();
    	 
    	 if(tree.isLeaf(aux))
    		 return aux;
         for (Position<T> node : tree.children(aux)) {
     			nodeQueue.add(node);
         }
         return next();
    }
}
