package com.company;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Nikola on 5/29/2016.
 */
public class GoogleAuth {

    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    public static Credential authorize_full(List<String> scopes, String credentialDatastore,
                                    String emailAccount, String CLIENT_ID, String CLIENT_SECRET, boolean FORCE_REFRESH)
    {
        Credential credential = null;
        try {
            for(int i=0;i<4;i++)
            {
                credential = authorize(scopes, credentialDatastore, emailAccount, CLIENT_ID, CLIENT_SECRET, i%2 == 0);
                if (credential.getExpiresInSeconds() < 50 || FORCE_REFRESH)
                {
                    try {
                        credential.refreshToken();
                        break;
                    } catch (Exception e) {}
                }
                else
                {
                    break;
                }
            }
        } catch (Exception e) {e.printStackTrace();}
        return credential;
    }
    public static Credential authorize(List<String> scopes, String credentialDatastore,
                                       String emailAccount, String CLIENT_ID, String CLIENT_SECRET,
                                       boolean OFFLINE_ACCESS) throws IOException
    {
        updateClientSecrets(CLIENT_ID, CLIENT_SECRET);
        final String dataStoreLocation = System.getProperty("user.home") + "/.oauth-credentials/" + credentialDatastore + "/" + emailAccount;

        final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName());
        buggyLogger.setLevel(java.util.logging.Level.SEVERE);

        Reader clientSecretReader = new InputStreamReader(GoogleAuth.class.getResourceAsStream("/client_secrets.json"));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);

        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(dataStoreLocation));
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(credentialDatastore);

        GoogleAuthorizationCodeFlow flow = null;
        if(OFFLINE_ACCESS)
        {
            System.out.println("GoogleAuth/ Offline Access");
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialDataStore(datastore).setAccessType("offline")
                    .build();
        }
        else
        {
            System.out.println("GoogleAuth/ Online Access");
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialDataStore(datastore)
                    .build();
        }

        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(-1).build();

        return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
    }
    private static void updateClientSecrets(String CLIENT_ID, String CLIENT_SECRET)
    {
        try {
            String text = new String(Files.readAllBytes(Paths.get("resources/client_secrets.json")), StandardCharsets.UTF_8);
            JSONObject object = new JSONObject(text);
            object.getJSONObject("installed").put("client_id", CLIENT_ID);
            object.getJSONObject("installed").put("client_secret", CLIENT_SECRET);

            Utils.saveFile("resources/client_secrets.json", object.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
