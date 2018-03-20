package practica2;

import static org.junit.Assert.assertEquals;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import material.Position;
import material.tree.BreadthFirstTreeIterator;
import material.tree.narytree.LinkedTree;

import java.net.*;
import java.io.*;

/**
 *
 * @author danie
 */


public class HtmlCrawler {
	private LinkedTree<String> tree = new LinkedTree<>();
    List<String> nodeQueue = new LinkedList<>();
    
    public HtmlCrawler(String URL, int p) throws Exception { 
    	if(p > 0){
    		this.tree.addRoot(URL);
    		Position<String> aux = this.tree.root();
    		crawler(aux, p-1);
    	}
    }
    
    private void crawler(Position<String> node, int p) throws Exception{
    	if(p>=0){
    		URLReader(node.getElement(),node);
    		for (Position<String> hijo : this.tree.children(node)){
    			crawler(hijo, p - 1);
    		}
    	}
    }
    public int getSize() {
    	TreeSizeCalculator calc = new TreeSizeCalculator();
        int size = calc.size(this.tree);
        return size;

    }
            
    public Iterable<String> getFrontier() {
    	ArrayList<String> list = (ArrayList) new ArrayList<>();
    	
       	LeafIterator<String> leaf = new LeafIterator<String>(tree);
       	
       	while(leaf.hasNext()){
       		if(leaf.next() != null)
       				list.add(leaf.next().getElement());
       	}
		return list;
    }
    
    public String getFirstCommonAntecesor(String url1, String url2) {
    	if(tree != null && !tree.isEmpty()){
			LinkedTree<String> t = (LinkedTree<String>) tree;
			Position aux1 = null;
			Position aux2 = null;
			String nodo = null;
			Position url;
			boolean esta = false; boolean esta1 = false; boolean esta2 = false;
			
			BreadthFirstTreeIterator it = new BreadthFirstTreeIterator(t);
			
			while(it.hasNext() && !esta){
				url = it.next();
				if(url.getElement().equals(url1) && !esta1){
					aux1 = url;
					esta1 = true;
				}
				if(url.getElement().equals(url2) && !esta2){
					aux2 = url;
					esta2  = true;
				}
				
				esta = (esta1 && esta2);
			}
			
			if((url1 != null) && (t.isInternal(aux1) || t.isLeaf(aux1) || t.isRoot(aux1))){
				if((url2 != null) && (t.isInternal(aux2) || t.isLeaf(aux2) || t.isRoot(aux2))){
					
					if(this.level(aux1) > this.level(aux2)){
						
						while(this.level(aux1) != this.level(aux2))
							aux1 = this.tree.parent(aux1);
						
					}else if(this.level(aux1) < this.level(aux2)){
						
						while(this.level(aux2) != this.level(aux1))
							aux2 = this.tree.parent(aux2);
						
					}else{
						
						while(!(this.tree.parent(aux1).equals(this.tree.parent(aux2)))){
							if(this.tree.parent(aux1) != null)
								aux1 = this.tree.parent(aux1);
							
							if(this.tree.parent(aux2) != null)
								aux2 = this.tree.parent(aux2);							
						}
						nodo = aux1.toString();
					}
					return nodo;
				}
				throw new RuntimeException("La url 2 no es valido o no pertenece al arbol");
			}
			throw new RuntimeException("La url 1 no es valido o no pertenece al arbol");
		}
		throw new RuntimeException("Arbol no valido");
	}
    
    public void URLReader(String url, Position pos) throws Exception {
    	try{
            URL dir = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(dir.openStream()));

            String input;
            while ((input = in.readLine()) != null){
            	String protocolo = getUrlInput(input);
            	
            	if(protocolo != "invalid protocol"){
            		if(!this.nodeQueue.contains(protocolo)){
            			this.tree.add(protocolo,pos);
            			this.nodeQueue.add(protocolo);
            		}
            	//if(ParseURL(inputLine) != null){
            		//nodeQueue.add(inputLine);
            	}
            }
            in.close();
    	}catch (MalformedURLException ex) {
            Logger.getLogger(HtmlCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HtmlCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public int level(Position p) {
		int cont = 1;
		if(tree.isEmpty()){
			return 0;
		}else if(tree.isRoot(p)){
			return cont;
		}else{
			Position aux = tree.parent(p);
			while(!(tree.isRoot(aux))){
				cont++;
				aux = tree.parent(aux);
			}
			return cont +1;
		}
	}

    public String getUrlInput(String input) throws Exception {
    		
    	String URL = "invalid protocol";
    	Pattern p = Pattern.compile("\\bhttp://\\b");
        Matcher m = p.matcher(input);
        
        while(m.find()){
        if(m.end() != -1 && m.start() != -1){
        	int fin = input.indexOf('"', m.start() + 1);
        
        	if(fin != -1){
        		int etiqueta = input.indexOf('#', m.end() + 1);
        		URL = input.substring(m.start(), fin);
        	
        		if(etiqueta != -1)
        			URL = input.substring(m.start(), etiqueta);
        	}
    	}
        
        
        //String init = input.getProtocol();//int init = linea.indexOf("http://");
    	//if(init.length() != 0){
    		//String auth = input.getAuthority();//int fin = linea.indexOf('"', init + 1);
    		
    		//if(auth.length() != 0)/*if(fin != -1)*/{
    			//String host = input.getHost();//int tag = linea.indexOf('#', init + 1);
    			//URL = init + auth;//URL = linea.substring(init, fin);	
    			//if(host.length() != 0)/*if(tag != -1)*/
    				//URL = URL + host;//URL = linea.substring(init, tag);
    			
    	//}
         /* System.out.println("protocol = " + aURL.getProtocol());
            System.out.println("authority = " + aURL.getAuthority());
            System.out.println("host = " + aURL.getHost());
            System.out.println("port = " + aURL.getPort());
            System.out.println("path = " + aURL.getPath());
            System.out.println("query = " + aURL.getQuery());
            System.out.println("filename = " + aURL.getFile());
            System.out.println("ref = " + aURL.getRef());*/
        } 
    	return URL;
    }
}
