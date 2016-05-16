package com.company;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by Nikola on 5/8/2016.
 */
public class GoogleSearch {

    private String base = "https://www.google.com/search?q=";
    private String firstResultLink;
    private String query;

    public GoogleSearch(){}
    public void search(String query)
    {
        this.query = query;
        execute();
    };
    public String getFirstResultLink()
    {
        return firstResultLink;
    }
    private void execute()
    {
        String request = base + query + "&num=1";
        try{
            Document doc = Jsoup
                    .connect(request)
                    .userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
                    .timeout(5000).get();

            Elements links = doc.select("a[href]");
            for(Element link : links)
            {
                String temp = link.attr("href");
                if(temp.startsWith("/url?q="))
                {
                    firstResultLink = temp.substring(7, temp.indexOf("&"));
                    break;
                }

            }
        }catch(Exception e){e.printStackTrace();}

    }

}