package com.company;

/**
 * Created by Nikola on 5/29/2016.
 */
public class AccountManager {

    String accountListFile;
    private String email = "gameboss121212@gmail.com";
    private String youtubeClientID = "779019512534-fbu64jvb2ham12a92ss8n1np1aj1am3h.apps.googleusercontent.com";
    private String youtubeClientSecret = "CMhPmO2dYTTsI6UwinE5lBO5";

    private String bloggerClientID = "/";
    private String bloggerClientSecret = "/";
    private String bloggerRefreshToken = "/";
    private int uploads = 0;

    public AccountManager()
    {
        //TODO: Load JSON file with all accounts
        //TODO: Load Blogger JSON file
    }
    public String getBloggerClientSecret() { return bloggerClientSecret;}
    public String getBloggerClientID() {return bloggerClientID; }
    public String getBloggerRefreshToken() {return bloggerRefreshToken; }
    public String getEmail()
    {
        return email;
    }
    public String getYoutubeClientID()
    {
        return youtubeClientID;
    }
    public String getYoutubeClientSecret()
    {
        return youtubeClientSecret;
    }
    public int getUploads()
    {
        return uploads;
    }
    public void incrementUploads(int val)
    {
        uploads += val;
    }
    public boolean next()
    {
        return false;
    }

}
