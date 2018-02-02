package com.contata.rss.utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class FeedFinder {
    private static Set<URL> search(Document doc) { // static Set<URL> search(Document doc) {
        Set<URL> feeds = new HashSet<URL>();
        Elements links = doc.select("head > link" +
                                    "[rel=alternate]" +
                                    "[type~=(application/(rss|(x(\\.|\\-))?atom|rdf)\\+|text/)xml]" +
                                    "[href~=.+]");
        if(links.size()>0)
        {
        for (Element link : links) {
            try {
                feeds.add(new URL(link.attr("abs:href")));
            }
            catch (MalformedURLException e) {
                // ignore
            }
        	}
        }else
        {
	        
	        for (Element e : doc.select("channel")) {
	        	
	        	Elements linkTag=e.getElementsByTag("link");
	        	for(Element link : linkTag)
	        	{
	            System.out.println(link.text());
	        	try {
					feeds.add(new URL(link.text()));
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	}
	        }
	        
        }
        return feeds;
    }

    public static Set<URL> search(String html) {
    	Set<URL> feedsResult=new HashSet<URL>();
    	feedsResult=search(Jsoup.parse(html, "", Parser.xmlParser()));
    	if(feedsResult.size()>0)
    	{
    		System.out.println("Using XML parser");
    		return feedsResult;
    		
    	}
    	else
    	{
    		feedsResult=search(Jsoup.parse(html));
    		System.out.println("Using HTML	 parser");
    	}
    	 return feedsResult;
       /* return search(Jsoup.parse(html));*/
    }

    public static Set<URL> search(String html, String baseUri) {
        return search(Jsoup.parse(html, baseUri));
    }

    public static Set<URL> search(File in, String charsetName) throws IOException {
        return search(Jsoup.parse(in, charsetName));
    }

    public static Set<URL> search(File in, String charsetName, String baseUri) throws IOException {
        return search(Jsoup.parse(in, charsetName, baseUri));
    }
}

