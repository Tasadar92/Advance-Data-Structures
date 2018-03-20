/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree.binarytree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import material.Position;

/**
 *
 * @author danie
 * @param <T>
 */
public class PosorderBinaryTreeIterator<T> implements Iterator<Position<T>> {

    private Deque<Position<T>> nodeStack = new LinkedList<>();
    private final BinaryTree<T> tree;

    public PosorderBinaryTreeIterator(BinaryTree <T> tree) {
        this(tree,tree.root());
    }


    public PosorderBinaryTreeIterator(BinaryTree <T> tree, Position<T> node) {
        this.tree = tree;
        goToLastInRight(node);
    }

    private void goToLastInRight(Position<T> node) {
        nodeStack.addFirst(node);
        
        while (tree.hasLeft(node)) {
            node = tree.right(node);
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
        if (tree.hasLeft(aux)) {
            goToLastInRight(tree.left(aux));
        }
        
        return aux;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not implemented.");
    }
}