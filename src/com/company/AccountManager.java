package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Nikola on 5/29/2016.
 */
public class AccountManager {

    private final String bloggerSecretsFile = "resources/blogger_client_secrets.json";
    private final String youtubeAccountsFile = "resources/youtubeAccounts.json";
    final String backupDirectory = "resources/backups";

    private JSONObject youtubeAccounts;
    private JSONArray youtubeAccountsArray;
    private int youtubeAccountsArraySize;
    private int position = 0;

    private String youtubeEmail;
    private String youtubeClientID;
    private String youtubeClientSecret;
    private String youtubeRefreshToken;
    private int uploads = 0;
    private boolean authenticated;

    private String bloggerEmail;
    private String bloggerClientID;
    private String bloggerClientSecret;
    private String bloggerRefreshToken;


    public AccountManager()
    {
        JSONObject bloggerSecrets = Utils.readJSONFile(bloggerSecretsFile);
        try {
            bloggerEmail = bloggerSecrets.getString("email");
            bloggerClientID = bloggerSecrets.getString("client_id");
            bloggerClientSecret = bloggerSecrets.getString("client_secret");
            bloggerRefreshToken = bloggerSecrets.getString("refresh_token");
        }catch(Exception e){e.printStackTrace();}

        try {
            youtubeAccounts = Utils.readJSONFile(youtubeAccountsFile);
            youtubeAccountsArray = youtubeAccounts.getJSONArray("accounts");
            youtubeAccountsArraySize = youtubeAccountsArray.length();
        }catch(Exception e){e.printStackTrace();}

        loadYoutubeSecrets();
    }
    public String getBloggerClientSecret() { return bloggerClientSecret;}
    public String getBloggerClientID() {return bloggerClientID; }
    public String getBloggerEmail(){return bloggerEmail;}
    public String getBloggerRefreshToken(){return bloggerRefreshToken;}

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
    public String getYoutubeRefreshToken(){return youtubeRefreshToken;}
    public boolean isAuthenticated(){return authenticated;}

    public int getUploads()
    {
        return uploads;
    }
    public void incrementUploads(int val)
    {
        uploads += val;
    }
    public void setUploads(int val)
    {
        uploads = val;
    }
    public int getArrayPosition(){return position;}
    public boolean next()
    {
        flush();
        position++;
        if(position < youtubeAccountsArraySize)
        {
            loadYoutubeSecrets();
            return true;
        }
        else
            return false;
    }
    public void flush()
    {
        try {
            String backupLoc = backupDirectory + "/backup_" + Utils.getCurrentDateTime() + ".json";
            Utils.copyFile(youtubeAccountsFile, backupLoc);

            youtubeAccounts.getJSONArray("accounts").getJSONObject(position).put("uploads", uploads);
            Utils.saveFile(youtubeAccountsFile, youtubeAccounts.toString());
        }catch(Exception e){e.printStackTrace();}
    }
    public void debugBlogger()
    {
        System.out.println(bloggerEmail);
        System.out.println(bloggerClientID);
        System.out.println(bloggerClientSecret + "\n");
    }
    public void debugYoutube()
    {
        System.out.println(youtubeEmail);
        System.out.println(youtubeClientID);
        System.out.println(youtubeClientSecret + "\n");
    }
    private void loadYoutubeSecrets()
    {
        try {
            JSONObject currentAcc = youtubeAccountsArray.getJSONObject(position);
            youtubeEmail = currentAcc.getString("email");
            youtubeClientID = currentAcc.getString("clientId");
            youtubeClientSecret = currentAcc.getString("clientSecret");
            youtubeRefreshToken = currentAcc.getString("refreshToken");
            uploads = currentAcc.getInt("uploads");
            authenticated = currentAcc.getBoolean("authenticated");
        }catch(Exception e){e.printStackTrace();}
    }
}
