package com.company;

import org.json.JSONObject;
import org.mortbay.util.ajax.JSON;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;

/**
 * Created by Nikola on 5/10/2016.
 */
public class BloggerPost {

    private String ACCESS_TOKEN;
    private String blogID;
    private String title;
    private String content;
    private String base = "https://www.googleapis.com/blogger/v3/blogs/";
    private String extraParameters = "";
    private String response;
    private String postUrl;
    public BloggerPost(String accessToken)
    {
        ACCESS_TOKEN = accessToken;
    }
    public void setBlogId(String blogID)
    {
        this.blogID = blogID;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public void addParameters(String param)
    {
        extraParameters = param;
    }
    public String getPostUrl()
    {
        extractPostUrl();
        return postUrl;
    }
    public void upload()
    {
        String data = createDataString();

        HttpsURLConnection connection;
        OutputStreamWriter writer;
        BufferedReader reader;
        try {
            String request = base + blogID + "/posts";
            if(extraParameters.length() > 1)
                request += "?" + extraParameters;

            URL url = new URL(request);

            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", " Bearer " + ACCESS_TOKEN);
            connection.addRequestProperty("Content-type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            response = "";
            while(true)
            {
                String line = reader.readLine();
                if(line != null)
                    response += line;
                else
                    break;
            }

        } catch (Exception e) { e.printStackTrace(); }
    }
    private String createDataString()
    {
        String data = "";
        try{
            JSONObject object = new JSONObject();
            object.append("title", title);
            object.append("content", content);
            data = object.toString();
        }catch(Exception e){e.printStackTrace();}

        return data;
    }
    private void extractPostUrl()
    {
        try{
            JSONObject object = new JSONObject(response);
            postUrl = object.getString("url");
        }catch(Exception e){e.printStackTrace();}
    }

}