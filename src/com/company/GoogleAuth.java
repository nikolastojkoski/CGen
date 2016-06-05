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

    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";

    public static Credential authorize(List<String> scopes, String credentialDatastore,
                                       String emailAccount, String CLIENT_ID, String CLIENT_SECRET) throws IOException
    {
        updateClientSecrets(CLIENT_ID, CLIENT_SECRET);

        Reader clientSecretReader = new InputStreamReader(GoogleAuth.class.getResourceAsStream("/client_secrets.json"));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);

        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://console.developers.google.com/project/_/apiui/credential "
                            + "into src/main/resources/client_secrets.json");
            System.exit(1);
        }

        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}

        final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName());
        buggyLogger.setLevel(java.util.logging.Level.SEVERE);

        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(System.getProperty("user.home") + "/.oauth-credentials/"
                                                                                        + credentialDatastore + "/" + emailAccount));
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(credentialDatastore);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialDataStore(datastore)
                .build();

        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(-1).build();

        // Authorize.
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
