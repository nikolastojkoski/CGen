package com.company;

import com.google.api.client.auth.oauth2.Credential;
import com.google.common.collect.Lists;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Nikola on 6/5/2016.
 */
public class AccountAuth {

    static final String accountListFile = "resources/youtubeAccounts.json";
    static final String backupDirectory = "resources/backups";
    static final String credentialDatastore = "uploadvideo";

    public static void main(String[] args)
    {
        System.out.println("AccountAuth/main");

        boolean NEW_CHANGES = false;

        Utils.makeFile(accountListFile);
        Utils.makeDirectory(backupDirectory);

        JSONObject object = new JSONObject();
        try {
            String text = new String(Files.readAllBytes(Paths.get(accountListFile)), StandardCharsets.UTF_8);

            if(text.length() > 0)
                object = new JSONObject(text);
            else
                object.put("accounts", new JSONArray());

            while(true)
            {
                //todo: Use faster input
                char response = Utils.getInput("Add new account? (y/n)").charAt(0);
                if(response == 'n' || response == '0') {
                    break;
                }

                NEW_CHANGES = true;
                JSONObject obj = new JSONObject();

                String EMAIL = Utils.getInput("Email");
                String CLIENT_ID = Utils.getInput("Client_ID");
                String CLIENT_SECRET = Utils.getInput("Client_Secret");

                List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");
                Credential credential = GoogleAuth.authorize(scopes, credentialDatastore, EMAIL, CLIENT_ID, CLIENT_SECRET, true);

                obj.put("email", EMAIL);
                obj.put("clientId", CLIENT_ID);
                obj.put("clientSecret", CLIENT_SECRET);
                obj.put("authenticated", true);
                obj.put("refreshToken", credential.getRefreshToken());
                obj.put("uploads", 0);
                object.getJSONArray("accounts").put(obj);
            }

        }catch(Exception e){e.printStackTrace();}

        try {
            JSONArray accountArray = object.getJSONArray("accounts");
            for(int i=0; i < accountArray.length(); i++)
            {
                JSONObject obj = accountArray.getJSONObject(i);
                System.out.println("\nAT Position: " + i + ": ");
                System.out.println("email: " + obj.getString("email"));
                System.out.println("clientId: " + obj.getString("clientId"));
                System.out.println("clientSecret: " + obj.getString("clientSecret"));
                System.out.println("refreshtoken: " + obj.getString("refreshToken"));
                System.out.println("authenticated: " + obj.getBoolean("authenticated"));
                System.out.println("uploads: " + obj.getInt("uploads"));
            }

        }catch (Exception e){e.printStackTrace();}

        if(NEW_CHANGES)
        {
            String backupLoc = backupDirectory + "/backup_" + Utils.getCurrentDateTime() + ".json";
            Utils.copyFile(accountListFile, backupLoc);
            Utils.saveFile(accountListFile, object.toString());
        }

    }
    public static Credential reAuthorizeAccount(int position)
    {
        try {
            String text = new String(Files.readAllBytes(Paths.get(accountListFile)), StandardCharsets.UTF_8);

            JSONObject object = new JSONObject(text);
            JSONObject account = object.getJSONArray("accounts").getJSONObject(position);

            String EMAIL = account.getString("email");
            String CLIENT_ID = account.getString("clientId");
            String CLIENT_SECRET = account.getString("clientSecret");

            String datastoreLocation = System.getProperty("user.home") + "/.oauth-credentials/" + credentialDatastore + "/" + EMAIL;
            Utils.deleteDirectoryContents(datastoreLocation);
            Utils.deleteFile(datastoreLocation);

            List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");
            Credential credential = GoogleAuth.authorize(scopes, credentialDatastore, EMAIL, CLIENT_ID, CLIENT_SECRET, true);

            account.put("refreshToken", credential.getRefreshToken());
            object.getJSONArray("accounts").put(position, account);

            String backupLoc = backupDirectory + "/backup_" + Utils.getCurrentDateTime() + ".json";
            Utils.copyFile(accountListFile, backupLoc);
            Utils.saveFile(accountListFile, object.toString());

            return credential;

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static Credential reAuthorizeBloggerAccount(String bloggerEmail)
    {
        // todo: read and edit blogger client secret file in /resources/
        return null;
    }

}
