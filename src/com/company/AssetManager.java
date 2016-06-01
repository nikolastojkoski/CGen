package com.company;
import java.io.File;

/**
 * Created by Nikola on 5/29/2016.
 */
public class AssetManager {

    private String[] gamePictures;
    private int currentPos = -1;
    private boolean FORMATTING_OK = true;
    public AssetManager(String directory)
    {
        gamePictures = new File(directory).list();
        checkFormatting();
    }
    public boolean next()
        {
        //if(currentPos >= 0)
            //TODO: add directory
            //Utils.eraseFile(gamePictures[currentPos]);

        currentPos++;

        if(currentPos < gamePictures.length)
            return true;
        return false;
    }
    public boolean isInputCorrect()
    {
        return FORMATTING_OK;
    }
    public String getImageFile()
    {
        if(currentPos >= gamePictures.length)
            return "null";
        return gamePictures[currentPos];
    }
    public String getGameName()
    {
        if(currentPos >= gamePictures.length)
            return "null";
        return gamePictures[currentPos].substring(0, gamePictures[currentPos].indexOf('.'));
    }
    public String getGameTitle()
    {
        if(currentPos >= gamePictures.length)
            return "null";

        StringBuilder builder = new StringBuilder(getGameName());
        for(int i = 0; i < builder.length(); i++)
        {
            if(i+1 < builder.length())
            {
                int first = Character.getType(builder.charAt(i));
                int second = Character.getType(builder.charAt(i+1));

                if((first == Character.LOWERCASE_LETTER && second == Character.UPPERCASE_LETTER) ||
                        (first == Character.LOWERCASE_LETTER && second == Character.DECIMAL_DIGIT_NUMBER) ||
                        (first == Character.DECIMAL_DIGIT_NUMBER && second == Character.UPPERCASE_LETTER) ||
                        (first == Character.DECIMAL_DIGIT_NUMBER && second == Character.LOWERCASE_LETTER)
                        )
                {
                    builder.insert(i+1, " ");
                    i++;
                }
                if(first == '-')
                    builder.setCharAt(first, ' '); //TODO: fix
            }
        }

        return builder.toString();
    }
    public int remainingAssets()
    {
        return Math.max(0, gamePictures.length - currentPos - 1);
    }
    private void checkFormatting()
    {
        for(int i=0;i<gamePictures.length;i++) {
            if (gamePictures[i].contains(" "))
            {
                FORMATTING_OK = false;
                break;
            }
        }
    }
}