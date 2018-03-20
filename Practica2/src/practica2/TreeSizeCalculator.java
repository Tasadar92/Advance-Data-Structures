package practica2;

import material.tree.*;
import material.tree.narytree.LinkedTree;
import material.*;

import java.util.*;

/**
 *
 * @author danie
 */
public class TreeSizeCalculator {
    /**
     * @param t
     * @return the size of the tree t
     */
    public static int size(Tree t){
    	if(!t.isEmpty()){
    		return calculator(t);
    	}else{
    		return 0;
    	}
    }
    
    public static int calculator(Tree t){
    	int cont = 0;
    	BreadthFirstTreeIterator it = new BreadthFirstTreeIterator (t);
    	
    	while (it.hasNext()){
    			it.next();
    			cont++;
    	}
    	
    	return cont;
    }
}
