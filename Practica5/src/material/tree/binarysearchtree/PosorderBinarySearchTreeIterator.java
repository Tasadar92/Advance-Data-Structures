/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree.binarysearchtree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import material.Position;

/**
 *
 * @author danie
 * @param <T>
 */
public class PosorderBinarySearchTreeIterator<T> implements Iterator<Position<T>> {

    private Deque<Position<T>> nodeStack = new LinkedList<>();
    private final LinkedBinarySearchTree<T> tree;

    public PosorderBinarySearchTreeIterator(LinkedBinarySearchTree <T> tree) {
        this(tree,tree.binTree.root());
    }


    public PosorderBinarySearchTreeIterator(LinkedBinarySearchTree <T> tree, Position<T> node) {
        this.tree = tree;
        goToLastInRight(node);
    }

    private void goToLastInRight(Position<T> node) {
        nodeStack.addFirst(node);
        
        while (tree.binTree.hasRight(node)) {
            node = tree.binTree.right(node);
            nodeStack.addFirst(node);
        }
    }
        
    @Override
    public boolean hasNext() {
        return (!nodeStack.isEmpty());
    }

    /**
     * This method visits the nodes of a tree by following a breath-first search
     */
    @Override
    public Position<T> next() {
        Position<T> aux = nodeStack.removeFirst();
        if (tree.binTree.hasLeft(aux)) {
            goToLastInRight(tree.binTree.left(aux));
        }
        
        return aux;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}