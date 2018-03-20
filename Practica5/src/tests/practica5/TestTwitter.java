package tests.practica5;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import practica5.Tweet;
import practica5.TweetAnalysis;

public class TestTwitter {
	
	private TweetAnalysis tweets;

	@Before
	public void setUp() throws Exception {
		tweets = new TweetAnalysis();
		String ruta = "C:/Users/danie/Desktop/tweets/infinitywar.json";
		tweets.addFile(ruta);
	}

	@Test
	public void testFindTweets() {
		Set<String> expected = new HashSet<>();
		expected.add("TNTLA"); // Tweet 7
		expected.add("CinePREMIERE"); // Tweet 9
		expected.add("CinemarkChile"); // Tweet 8
		expected.add("policia"); // Tweet 5
		for (Tweet t : tweets.findTweets(300, 600)) {
			assertTrue(expected.contains(t.getScreenname()));
			expected.remove(t.getScreenname());
		}
		assertTrue(expected.isEmpty());
	}

	@Test
	public void testWorstTweets() {
		Set<String> expected = new HashSet<>();
		expected.add("CinePREMIERE"); // Tweet 13
		//expected.add("altapeli"); // Tweet 12
		for (Tweet t : tweets.worstTweets(0.02)) {
			assertTrue(expected.contains(t.getScreenname()));
			expected.remove(t.getScreenname());
		}
		assertTrue(expected.isEmpty());
	}

	@Test
	public void testBestTweets() {
		Set<String> expected = new HashSet<>();
		expected.add("MarvelLATAM"); // Tweet 0
		for (Tweet t : tweets.bestTweets(0.8)) {
			assertTrue(expected.contains(t.getScreenname()));
			expected.remove(t.getScreenname());
		}
		assertTrue(expected.isEmpty());
	}

}
