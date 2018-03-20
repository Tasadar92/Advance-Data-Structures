/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.practica1;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import practica2.HtmlCrawler;
import static org.junit.Assert.*;

/**
 *
 * @author jvelez
 */
public class HtmlCrawlerTest {

    /**
     * Test of getSize method, of class HtmlCrawler.
     * @throws Exception 
     */
   /*@Test
    public void testGetSize() throws Exception {
        HtmlCrawler crawler = new HtmlCrawler("http://www.columbia.edu/~fdc/sample.html",1);
        assertEquals(13, crawler.getSize());
    }

    /**
     * Test of getSize method, of class HtmlCrawler.
     * @throws Exception 
     */
    /*@Test
    public void testGetSize2() throws Exception {
        HtmlCrawler crawler = new HtmlCrawler("http://www.columbia.edu/~fdc/sample.html",0);
        assertEquals(0, crawler.getSize());
    }  
    
    /**
     * Test of getSize method, of class HtmlCrawler.
     * @throws Exception 
     */
    /*@Test
    public void testGetSize3() throws Exception {
        System.out.println("getSize");
        HtmlCrawler crawler = new HtmlCrawler("http://www.columbia.edu/~fdc/sample.html",2);
        assertEquals(370, crawler.getSize());
    }    


    /**
     * Test of getFrontier method, of class HtmlCrawler.
     * @throws Exception 
     */
    /*@Test
    public void testGetFrontier() throws Exception {
        HtmlCrawler crawler = new HtmlCrawler("http://www.columbia.edu/~fdc/sample.html",1);
        assertEquals(13, crawler.getSize());
        int cont = 0;
        for (String s : crawler.getFrontier()) {
            cont++;
        }
        assertEquals(12, cont);
    }

    /**
     * Test of getFirstCommonAntecesor method, of class HtmlCrawler.
     * @throws Exception 
     */
   @Test
   public void testGetFirstCommonAntecesor() throws Exception {
        HtmlCrawler instance = new HtmlCrawler("http://www.columbia.edu/~fdc/sample.html",2);
        String url1 = "http://validator.w3.org/";
        String url2 = "http://www.columbia.edu";
        String expResult = "http://www.columbia.edu/~fdc/sample.html";
        String result = instance.getFirstCommonAntecesor(url1, url2);
        assertEquals(expResult, result);
    }
    
}
