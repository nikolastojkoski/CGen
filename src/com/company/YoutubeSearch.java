package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Nikola on 5/2/2016.
 */
public class YoutubeSearch {

    private String base = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&type=video+&q=";
    private String api_key;
    private String query;
    private String firstResultID;

    public YoutubeSearch(String key)
    {
        this.api_key = key;
    }

    public String getFirstResultID()
    {
        return firstResultID;
    }

    public void searchVideo(String title)
    {
        this.query = title;
        firstResultID = extractID(getJSONString());
    }

    private String getJSONString()
    {
        String queryAddress = base + query + "&key=" + api_key;

        ApiConnection apiConnection = new ApiConnection();
        apiConnection.setQuery(queryAddress);
        return apiConnection.getResponse();
    }
    private String extractID(String jsonString)
    {
        String videoID = "";

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            videoID = jsonArray.getJSONObject(0).getJSONObject("id").getString("videoId");
        }catch(Exception e){e.printStackTrace();}

        return videoID;
    }


}
