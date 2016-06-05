package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Nikola on 6/5/2016.
 */
public class AccountAuth {


    public static void main(String[] args)
    {
        System.out.println("AccountAuth/main");

        boolean NEW_CHANGES = false;
        final String accountListFile = "resources/youtubeAccounts.json";
        Utils.makeFile(accountListFile);


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

                obj.put("email", Utils.getInput("Email"));
                obj.put("clientId", Utils.getInput("Client_ID"));
                obj.put("clientSecret", Utils.getInput("Client_Secret"));
                obj.put("authenticated", false);
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
                System.out.println("authenticated: " + obj.getBoolean("authenticated"));
                System.out.println("uploads: " + obj.getInt("uploads"));
            }

        }catch (Exception e){e.printStackTrace();}

        if(NEW_CHANGES) {
            Utils.saveFile(accountListFile, object.toString());
        }

    }

}
