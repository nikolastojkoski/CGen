package com.company.Deprecated;

import com.company.GoogleAuth;
import com.company.Utils;
import com.google.api.client.auth.oauth2.Credential;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Nikola on 6/11/2016.
 */
public class AuthTester {

    public static void main(String[] args)
    {
        final String api = "youtube";
        final boolean FORCE_REFRESH = false;

        String email = Utils.getInput("email");
        String clientID = Utils.getInput("clientId");
        String clientSecret = Utils.getInput("clientSecret");
        //String refreshToken;

        Credential credential = null;
        if(api == "youtube")
        {
            List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");
            credential = GoogleAuth.authorize_full(scopes, "uploadvideo", email, clientID, clientSecret, FORCE_REFRESH);
            System.out.println(credential.getRefreshToken());
            System.out.println(credential.getExpiresInSeconds());
        }
        else
        {
            List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/blogger");
            credential = GoogleAuth.authorize_full(scopes, "uploadPost", email, clientID, clientSecret, FORCE_REFRESH);
            System.out.println(credential.getRefreshToken());
            System.out.println(credential.getExpiresInSeconds());
        }
    }

}
