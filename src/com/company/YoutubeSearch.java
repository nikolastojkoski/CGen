package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

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

        HttpsURLConnection httpsURLConnection;
        String queryAddress = base + query + "&key=" + api_key;
        BufferedReader reader;
        InputStream inputStream;
        String jsonString = "";

        try {
            URL url = new URL(queryAddress);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();

            inputStream = httpsURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while(true)
            {
                String line = reader.readLine();
                if(line != null)
                    jsonString += line;
                else
                    break;
            }

            httpsURLConnection.disconnect();
            reader.close();

        }catch(Exception e){e.printStackTrace();}

        return jsonString;
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
