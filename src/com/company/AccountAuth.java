package com.company;

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


    public static void main(String[] args)
    {
        System.out.println("AccountAuth/main");

        final String accountListFile = "resources/youtubeAccounts.json";

        Utils.makeFile(accountListFile);
        JSONObject object = new JSONObject();

        try {

            String text = new String(Files.readAllBytes(Paths.get(accountListFile)), StandardCharsets.UTF_8);

            if(text.length() > 0)
                object = new JSONObject(text);
            else
            {
                List<Account> accountList = null;
                object.put("accounts", new JSONArray(accountList));
            }
        }catch(Exception e){e.printStackTrace();}

        Utils.saveFile(accountListFile, object.toString());

    }
    public class Account{
        public String email;
        public String clientID;
        public String clientSecret;
        public boolean AUTHENTICATED;
        public int uploads;
    }

}
