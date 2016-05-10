package com.company;

import java.io.*;
import java.net.*;

/**
 * Created by Nikola on 5/10/2016.
 */
public class BloggerPost {

    private String ACCESS_TOKEN;
    private String API_KEY;
    private String blogID;
    private String title;
    private String content;
    private String base = "https://www.googleapis.com/blogger/v3/blogs/7459603265214871506/posts?isDraft=true";

    public BloggerPost(String accessToken, String apiKey)
    {
        ACCESS_TOKEN = accessToken;
        API_KEY = apiKey;
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
    public void upload()
    {
        try {
            System.out.println("sending req");
            URI uri = new URI(base);
            String host = "www.googleapis.com";
            String path = "/blogger/v3/blogs/" + blogID + "/posts?isDraft=true&access_token=" + ACCESS_TOKEN;
            int port = 443;

            Socket socket = new Socket(host, port);
            PrintWriter request = new PrintWriter(socket.getOutputStream());
            request.print("POST " + path + " HTTP/1.1\r\n" +
                          "Host: " + host + "\r\n" +
                          "Content-type: application/json\r\n\r\n" +
                          "{\n" +
                    " \"title\": \"asdasdasd\",\n" +
                    " \"content\": \"assda\"\n" +
                    "}");
            request.flush();

            InputStream inStream = socket.getInputStream( );
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(inStream));
            String line;
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) { e.printStackTrace(); }
    }

}