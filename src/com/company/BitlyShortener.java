package com.company;

import org.json.JSONObject;

/**
 * Created by Nikola on 5/3/2016.
 */
public class BitlyShortener {

    private String login;
    private String apiKey;
    private String longUrl;

    private String base = "https://api-ssl.bitly.com/v3/shorten?";

    public BitlyShortener(String login, String apiKey)
    {
        this.login = login;
        this.apiKey = apiKey;
    }
    public String getShortUrl(String longUrl)
    {
        this.longUrl = longUrl;
        return extractShortUrl(getJSONString());
    }
    private String getJSONString()
    {
        String queryAddress = base + "login=" + login + "&apiKey=" + apiKey + "&longUrl=" + longUrl;
        ApiConnection apiConnection = new ApiConnection();
        apiConnection.setQuery(queryAddress);
        return apiConnection.getResponse();
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
