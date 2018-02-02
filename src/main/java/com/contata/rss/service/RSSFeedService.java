package com.contata.rss.service;

import java.net.URL;
import java.util.Set;

public interface RSSFeedService {
	
 public Object saveFeedToDatabase(String url);
 
 public Object saveFeederFinderToDatabase(String url);

 public boolean rssLinkFetchParser(String url, String sectionClass, int tableIndex, String ulDivClassName,String divIdentifier, boolean onlyTableWithindiv);


}
