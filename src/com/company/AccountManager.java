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
    private int pos = 1;

    private String youtubeEmail;
    private String youtubeClientID;
    private String youtubeClientSecret;
    private String youtubeRefreshToken;
    private int uploads = 0;
    private boolean authenticated;
    private boolean youtubeCredentialRefreshed = false;

    private String bloggerEmail;
    private String bloggerClientID;
    private String bloggerClientSecret;
    private String bloggerRefreshToken;
    private boolean bloggerCredentialRefreshed = false;


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
    public boolean isBloggerRefreshed(){return bloggerCredentialRefreshed;}
    public void setBloggerRefreshed(boolean value){bloggerCredentialRefreshed = value;}

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
    public boolean isYoutubeRefreshed(){return youtubeCredentialRefreshed;}
    public void setYoutubeRefreshed(boolean value){youtubeCredentialRefreshed = value;}
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
    public boolean next()
    {
        flush();
        youtubeCredentialRefreshed = false;
        pos++;
        if(pos < youtubeAccountsArraySize)
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

            youtubeAccounts.getJSONArray("accounts").getJSONObject(pos).put("uploads", uploads);
            Utils.saveFile(youtubeAccountsFile, youtubeAccounts.toString());
        }catch(Exception e){e.printStackTrace();}
    }
    private void loadYoutubeSecrets()
    {
        try {
            JSONObject currentAcc = youtubeAccountsArray.getJSONObject(pos);
            youtubeEmail = currentAcc.getString("email");
            youtubeClientID = currentAcc.getString("clientId");
            youtubeClientSecret = currentAcc.getString("clientSecret");
            youtubeRefreshToken = currentAcc.getString("refreshToken");
            uploads = currentAcc.getInt("uploads");
            authenticated = currentAcc.getBoolean("authenticated");
        }catch(Exception e){e.printStackTrace();}
    }
}
