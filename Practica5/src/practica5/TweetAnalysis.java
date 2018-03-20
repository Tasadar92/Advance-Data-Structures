package practica5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import material.Position;
import material.tree.binarysearchtree.*;
import material.tree.binarytree.InorderBinaryTreeIterator;
import material.tree.binarytree.PosorderBinaryTreeIterator;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *	@author danie
 */

public class TweetAnalysis {
	
	private LinkedBinarySearchTree<Tweet> tree = new LinkedBinarySearchTree();
	/**
	 * Adds a new set of tweets to the tree from the given file
	 * @throws IOException 
	 */
	public void addFile(String tweetFile) throws IOException {
		String path = tweetFile;
		BufferedReader bf = new BufferedReader(new FileReader(path));
		String line = null;
		String json = "";
		
		while ((line = bf.readLine()) != null) {
			json += line;
		}
		
		bf.close();
		
		JSONObject obj = new JSONObject(json);
		JSONArray tweets = obj.getJSONArray("statuses");
		
		for (int i=0;i<tweets.length();i++) {
			JSONObject t = tweets.getJSONObject(i);
			JSONObject user = t.getJSONObject("user");
			String name = user.getString("screen_name");
			String text = t.getString("text");
			int retweet = t.getInt("retweet_count");
			int favorite = t.getInt("favorite_count");
			
			Tweet tw = new Tweet(name, text, retweet, favorite);
			tree.insert(tw);
			//System.out.println("Tweet "+i+": "+t.get("text"));
		}
	}
	
	/**
	 * Recovers all the tweets with score larger or equal than min and smaller or equal than max
	 */
	public Iterable<Tweet> findTweets(int min, int max) {
		ArrayList<Tweet> l2 = new ArrayList();
		
		BreadthFirstSearchTreeIterator<Tweet> it = new BreadthFirstSearchTreeIterator<Tweet>(tree);
		
		while(it.hasNext()){
			Position<Tweet> aux = it.next();
			if(aux != null){
				int pts = aux.getElement().getFavorite() + aux.getElement().getRetweets();
				if(pts >= min && pts <= max)
					l2.add(aux.getElement());
			}
				
		}
		return l2;
	}
	
	/**
	 * Recovers all the tweets with score smaller or equal than percent*MAX_SCORE
	 */
	public Iterable<Tweet> worstTweets(double percent) {
		Iterable<Tweet> l1;
		ArrayList<Tweet> l2 = new ArrayList();
		
		InorderBinarySearchTreeIterator<Tweet> it = new InorderBinarySearchTreeIterator<>(tree);
		l2.add(it.next().getElement());
		
		double mejor = (l2.get(0).getFavorite() + l2.get(0).getRetweets())*percent;

		while(it.hasNext()){
			Position<Tweet> aux = it.next();
			if(aux != null){
				double pts = (aux.getElement().getFavorite()+aux.getElement().getRetweets())*percent;
				if(pts <= mejor)
					l2.add(aux.getElement());
			}
		}
		return l2;
	}
	
	/**
	 * Recovers all the tweets with score larger or equal than percent*MAX_SCORE
	 */
	public Iterable<Tweet> bestTweets(double percent) {
		Iterable<Tweet> l1;
		ArrayList<Tweet> l2 = new ArrayList();

		PosorderBinarySearchTreeIterator<Tweet> it = new PosorderBinarySearchTreeIterator<>(tree);
		l2.add(it.next().getElement());
		
		double mejor = (l2.get(0).getFavorite() + l2.get(0).getRetweets())*percent;

		while(it.hasNext()){
			Position<Tweet> aux = it.next();
			if(aux != null){
				double pts = (aux.getElement().getFavorite()+aux.getElement().getRetweets())*percent;
				if(pts >= mejor)
					l2.add(aux.getElement());
			}
		}
		return l2;
	}

}
