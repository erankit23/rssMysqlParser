package com.contata.rss.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.contata.rss.DTO.ImportDTO;
import com.contata.rss.DTO.LinkDTO;
import com.contata.rss.DTO.MediaDTO;

public class ListLinks {
	
	public static Map<String,Object> listLinks(String args) throws IOException {
		List<MediaDTO> mediaTempList=new ArrayList<MediaDTO>();
		List<LinkDTO> linkTempList=new ArrayList<LinkDTO>();
		List<ImportDTO> importTempList=new ArrayList<ImportDTO>();
		
		Map<String,Object> listLinksSet=new HashMap<String,Object>();
	
       // Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = args;
        print("Fetching %s...", url);
        Document doc =null;
        Elements links =null;
        Elements media=null;
        Elements imports =null;
      try {
         doc = Jsoup.connect(url).get();
         links = doc.select("a[href]");
         media = doc.select("[src]");
        imports = doc.select("link[href]");
      }
      
      catch (Exception e) {
          System.err.println("An exception was thrown");
      }
        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
        	MediaDTO mediaObj=new MediaDTO();
            if (src.tagName().equals("img")){
               /* print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));*/
            	mediaObj.setMediaType(src.tagName());
            	mediaObj.setMediaURL(src.attr("abs:src"));
            	mediaTempList.add(mediaObj);
               /* tempList.add(print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20)));*/
                
            }
            else
            {
            	mediaObj.setMediaType(src.tagName());
            	mediaObj.setMediaURL(src.attr("abs:src"));
            	mediaTempList.add(mediaObj);
                /*print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));*/
            	
                /*tempList.add(print(" * %s: <%s>", src.tagName(), src.attr("abs:src")));*/
            }
            }
        listLinksSet.put("Media", mediaTempList);
        
        /*tempList= new ArrayList<String>();*/
        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
           /* tempList.add(print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel")));*/
            ImportDTO importObj = new ImportDTO();
            importObj.setImportLink(link.attr("abs:href"));
            importObj.setImportCategory(link.attr("rel"));
            importTempList.add(importObj);
        }
        listLinksSet.put("Imports", importTempList);
        
        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
        	LinkDTO linkObj =new LinkDTO();
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
            linkObj.setLink(link.attr("abs:href"));
            linkObj.setLinkTitle(trim(link.text(), 35));
            linkTempList.add(linkObj);
            /*tempList.add(print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35)));*/
        }
        listLinksSet.put("Links", linkTempList);
        return listLinksSet;
    }

    /*private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }*/
	
	private static String print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
        return String.format(msg, args);
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
    
    /*public static String toPrettyFormat(Object json) 
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }*/
}

