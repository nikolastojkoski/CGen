package com.company;

/**
 * Created by Nikola on 5/29/2016.
 */
public class AccountManager {

    String accountListFile;
    private String youtubeEmail = "/";
    private String youtubeClientID = "/";
    private String youtubeClientSecret = "/";

    private String bloggerEmail = "/";
    private String bloggerClientID = "/";
    private String bloggerClientSecret = "/";
    private int uploads = 0;

    public AccountManager()
    {
        //TODO: Load JSON file with all accounts
        //TODO: Load Blogger JSON file
    }
    public String getBloggerClientSecret() { return bloggerClientSecret;}
    public String getBloggerClientID() {return bloggerClientID; }
    public String getBloggerEmail(){return bloggerEmail;}

    public String getYoutubeEmail()
    {
        return youtubeEmail;
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
