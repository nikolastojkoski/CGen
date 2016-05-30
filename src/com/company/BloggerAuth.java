package com.company;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * Created by Nikola on 5/10/2016.
 */
public class BloggerAuth {

    private String REFRESH_TOKEN;
    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String ACCESS_TOKEN;

    public BloggerAuth(){}
    public void setParams(String clientId, String clientSecret, String refreshToken)
    {
        CLIENT_ID = clientId;
        CLIENT_SECRET = clientSecret;
        REFRESH_TOKEN = refreshToken;
    }
    public String getAccessToken()
    {
        return ACCESS_TOKEN;
    }
    public void execute()
    {
        try {
            GoogleCredential credentials = new GoogleCredential.Builder()
                    .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
                    .setJsonFactory(new JacksonFactory()).setTransport(new NetHttpTransport()).build()
                    .setRefreshToken(REFRESH_TOKEN);

            credentials.refreshToken();
            ACCESS_TOKEN = credentials.getAccessToken();

        }catch(Exception e){e.printStackTrace();}
    }
}