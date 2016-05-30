package com.company;

import org.json.JSONObject;

/**
 * Created by Nikola on 5/3/2016.
 */
public class BitlyShortener {

    private String login;
    private String apiKey;
    private String longUrl;
    private String shortUrl;
    private String base = "https://api-ssl.bitly.com/v3/shorten?";

    public BitlyShortener(String login, String apiKey)
    {
        this.login = login;
        this.apiKey = apiKey;
    }
    public String getShortUrl()
    {
        return shortUrl;
    }
    public void shorten(String longUrl)
    {
        this.longUrl = longUrl;
        String queryAddress = base + "login=" + login + "&apiKey=" + apiKey + "&longUrl=" + longUrl;
        ApiConnection apiConnection = new ApiConnection();
        apiConnection.setQuery(queryAddress);
        shortUrl = extractShortUrl(apiConnection.getResponse());
    }
    private String extractShortUrl(String jsonString)
    {
        String shortUrl = "";

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            shortUrl = jsonObject.getJSONObject("data").getString("url").replace("\\", "");

        }catch(Exception e){e.printStackTrace();}

        return shortUrl;
    }

}
