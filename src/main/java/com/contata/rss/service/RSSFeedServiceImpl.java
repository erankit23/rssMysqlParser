package com.contata.rss.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.contata.rss.DTO.ImportDTO;
import com.contata.rss.DTO.LinkDTO;
import com.contata.rss.DTO.MediaDTO;
import com.contata.rss.dao.FeedRepository;
import com.contata.rss.dao.ImportRepository;
import com.contata.rss.dao.LinkRepository;
import com.contata.rss.dao.MediaRepository;
import com.contata.rss.dao.UrlRepository;
import com.contata.rss.model.Feed;
import com.contata.rss.model.Import;
import com.contata.rss.model.Link;
import com.contata.rss.model.Media;
import com.contata.rss.model.Url;
import com.contata.rss.utility.FeedFinder;
import com.contata.rss.utility.ListLinks;

@Component
public class RSSFeedServiceImpl implements RSSFeedService{
	
	@Autowired
	private UrlRepository urlRepository;
	
	@Autowired
	private FeedRepository feedRepository;
	
	@Autowired
	private ImportRepository importRepository;
	
	@Autowired 
	private MediaRepository mediaRepository;
	
	@Autowired 
	private LinkRepository linkRepository;
	
	@Autowired
	RSSFeedService service;

	@Override
	public Object saveFeedToDatabase(String url) {
		Set<URL> dbContent=null;
		try {
			Url urlObj = new Url();
			urlObj.setUrl(url);
			urlObj.setSourceParser("listFeeder");
			
			String html = Jsoup.connect(url).get().html();
			 dbContent = FeedFinder.search(html);
			if(dbContent != null && dbContent.size() > 0){
				System.out.println("DbContent size isssssss  :"+dbContent.size());
				Url savedUrlObj=urlRepository.save(urlObj);
				Iterator content=dbContent.iterator();
				while(content.hasNext()){
					
					String importLink =content.next().toString();
					Feed feedObj = new Feed();
					feedObj.setUrlId(savedUrlObj.getUrlId());
					feedObj.setImports(importLink);
					feedRepository.save(feedObj);
				}
				
			}
			else{
				//return "Input URL is parsed";
				return dbContent;
			}
			
			//return "Input URL is parsed";
			return dbContent;

		} catch (Exception e) {
			e.printStackTrace();
			return "Input URL is already parsed";
		}
	}

	@Override
	public Object saveFeederFinderToDatabase(String url) {
		Map<String,Object> response=null;
		try {
			Url urlObj = new Url();
			urlObj.setUrl(url);
			urlObj.setSourceParser("feederFinder");
			
				response = ListLinks.listLinks(url);
			
				if(response!=null && response.size()>0)
				{
					
					for (Map.Entry<String, Object> entry : response.entrySet())
							{
								Url savedUrlObj=urlRepository.save(urlObj);
								
							    if(entry.getKey().equalsIgnoreCase("Media"))
							    {
							    	
							    	for(MediaDTO mediaObj :(List<MediaDTO>)entry.getValue())
							    	{
							    		Media mediaObject =new Media();
							    		mediaObject.setUrlId(savedUrlObj.getUrlId());
							    		mediaObject.setMediaType(mediaObj.getMediaType());
							    		mediaObject.setMediaURL(mediaObj.getMediaURL());
							    		mediaRepository.save(mediaObject);
							    	}
							    }
							    if(entry.getKey().equalsIgnoreCase("Links"))
							    {
							    	
							    	for(LinkDTO linkObj :(List<LinkDTO>)entry.getValue())
							    	{
							    		Link linkObject =new Link();
							    		linkObject.setUrlId(savedUrlObj.getUrlId());
							    		linkObject.setLinkUrl(linkObj.getLink());
														    		
							    		linkRepository.save(linkObject);
							    	}
							    }
							    if(entry.getKey().equalsIgnoreCase("Imports"))
							    {
							    	
							    	for(ImportDTO importObj :(List<ImportDTO>)entry.getValue())
							    	{
							    		Import importObject =new Import();
							    		importObject.setUrlId(savedUrlObj.getUrlId());
							    		importObject.setImportLink(importObj.getImportLink());
							    		importObject.setImportCategory(importObj.getImportCategory());
							    		
							    		
							    		importRepository.save(importObject);
							    	}
							    }
							    
							}
				}
		} catch (Exception e) {
			
			e.printStackTrace();
			return "Input URL is already parsed";
		}
		return response;
	}

	@Override
	public boolean rssLinkFetchParser(String url,String sectionClass,int tableIndex,String ulDivIdentifierName,String divIdentifier,
			boolean onlyTableWithinDiv) {
		Document doc;
		ArrayList<String> alList=new ArrayList<String>();
		try {
			doc = Jsoup.connect(url).get();
			if(tableIndex!=-1)
			{
				System.out.println("-----");
				//select the specific table.
				Element table = doc.select("table").get(tableIndex); 
				
				
				for(Element row : table.select("tr"))
				{
					Elements links=row.select("a[href]");
					for(Element link : links)
					{
						System.out.println("$$$$    "+link.attr("abs:href"));
						Object resultContent=service.saveFeedToDatabase(link.attr("abs:href"));
						alList.add(resultContent.toString());
					}
				}

			}else if(!ulDivIdentifierName.equalsIgnoreCase(""))
			{
				System.out.println("======");
				for(Element li : doc.select("."+ulDivIdentifierName+"> ul"))
			    {
			    	for(Element link :li.select("a[href]"))
			    	{
			    		System.out.println("###    "+link.attr("abs:href"));
			    		Object resultContent=service.saveFeedToDatabase(link.attr("abs:href"));
						alList.add(resultContent.toString());
			    	}
			    }
				for(Element li : doc.select("#"+ulDivIdentifierName+"> ul"))
			    {
			    	for(Element link :li.select("a[href]"))
			    	{
			    		System.out.println("###    "+link.attr("abs:href"));
			    		Object resultContent=service.saveFeedToDatabase(link.attr("abs:href"));
						alList.add(resultContent.toString());
			    	}
			    }
				
			}else if(!divIdentifier.equalsIgnoreCase("") && onlyTableWithinDiv==true)
			{
				System.out.println("####");
				for(Element links : doc.select("."+divIdentifier+" > table"))
			    {
					System.out.println("1");
			    	for(Element link :links.select("a[href]"))
			    	{
			    		System.out.println("2");
			    		System.out.println("###    "+link.attr("abs:href"));
			    		Object resultContent=service.saveFeedToDatabase(link.attr("abs:href"));
						alList.add(resultContent.toString());
			    	}
			    }
				for(Element link : doc.select("#"+divIdentifier))
			    {
			    	/*for(Element link :doc.select("#"+divIdentifier).get(0).select("a[href]"))
			    	{*/
			    		System.out.println("###    "+link.attr("abs:href"));
			    		Object resultContent=service.saveFeedToDatabase(link.attr("abs:href"));
						alList.add(resultContent.toString());
			    	
			    }
			    
				
			}
			else if(!divIdentifier.equalsIgnoreCase(""))
			{
				System.out.println("####======");
				for(Element links : doc.select("."+divIdentifier))
			    {
			    	for(Element link :links.select("a[href]"))
			    	{
			    		System.out.println("###    "+link.attr("abs:href"));
			    		Object resultContent=service.saveFeedToDatabase(link.attr("abs:href"));
						alList.add(resultContent.toString());
			    	}
			    }
				for(Element link : doc.select("#"+divIdentifier))
			    {
			    	/*for(Element link :doc.select("#"+divIdentifier).get(0).select("a[href]"))
			    	{*/
			    		System.out.println("###    "+link.attr("abs:href"));
			    		Object resultContent=service.saveFeedToDatabase(link.attr("abs:href"));
						alList.add(resultContent.toString());
			    	
			    }
			    
				
			}
			else if(sectionClass.equalsIgnoreCase(""))
			{
				for(Element contentDiv : doc.select("section.zn-has-35-containers > div"))
			    {
			    	
			    }
				
			}
			Elements links = doc.select("a[href]");
			for(Element link : links)
			{/*
				System.out.println(" parsed links "+link.attr("abs:href"));
				Object resultContent=service.saveFeedToDatabase(link.attr("abs:href"));
				alList.add(resultContent.toString());*/
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return false;
	}

}
