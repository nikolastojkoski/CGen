package com.company;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Nikola on 5/3/2016.
 */
public class ApiConnection {

    private String query;
    private String response = "";

    public ApiConnection() {
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getResponse() {
        execute();
        return response;
    }

    private void execute() {
        HttpsURLConnection httpsURLConnection;
        BufferedReader reader;
        InputStream inputStream;

        try {
            URL url = new URL(query);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();

            inputStream = httpsURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while (true) {
                String line = reader.readLine();
                if (line != null)
                    response += line;
                else
                    break;
            }

            httpsURLConnection.disconnect();
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}