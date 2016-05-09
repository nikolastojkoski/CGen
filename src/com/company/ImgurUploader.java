package com.company;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Nikola on 5/7/2016.
 */
public class ImgurUploader {

    private String clientID;
    private File imageFile;
    private String base = "https://api.imgur.com/3/image";
    private String data;
    private String response;
    private String link;

    public ImgurUploader(String clientID)
    {
        this.clientID = clientID;
    }
    public void upload(String namepath)
    {
        this.imageFile = new File(namepath);
        getDataString();
        execute();
        extractLink();
    }
    public String getImageLink()
    {
        return link;
    }
    private void extractLink()
    {
        try{
            JSONObject jsonObject = new JSONObject(response);
            link = jsonObject.getJSONObject("data").getString("link").replace("\\", "");
        }catch(Exception e){e.printStackTrace();}
    }
    private void execute()
    {
        HttpsURLConnection connection;
        OutputStreamWriter writer;
        BufferedReader reader;
        try{
            URL url = new URL(base);

            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "Client-ID " + clientID);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            response = "";
            while(true)
            {
                String line = reader.readLine();
                if(line != null)
                    response += line;
                else
                    break;
            }
        }catch(Exception e){e.printStackTrace();}
    }
    private void getDataString()
    {
        try{
            BufferedImage image = ImageIO.read(imageFile);
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArray);
            byte[] byteImage = byteArray.toByteArray();
            String imageData = Base64.encode(byteImage);
            data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imageData, "UTF-8");

        }catch(Exception e){e.printStackTrace();}

    }

}
