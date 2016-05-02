package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args)
    {

        String inputDirectoryName = "input_pictures";
        String outputDirectoryName = "output_screenshots";
        String templateNamePath = "resources/MainTemplate.jpg";


        File cDir = new File(inputDirectoryName);
        File[] listOfFiles = cDir.listFiles();

        int conversionProgress = 0;

        for(File cFile : listOfFiles)
        {
            if(cFile.isFile() && isImage(cFile))
            {
                System.out.println(++conversionProgress + "/" + listOfFiles.length);

                String topImageNamePath = inputDirectoryName + "/" + cFile.getName();
                String fileName = cFile.getName();

                /** Convert */
                ImageBlender blender = new ImageBlender(templateNamePath, topImageNamePath); //Add blendposition, resize
                blender.execute();
                BufferedImage img = blender.getImage();

                /** Save */
                makeDirectory(outputDirectoryName);
                saveImage(img, outputDirectoryName + "/" + fileName.substring(0, fileName.indexOf(".")) + "_screenshot.jpg");

            }
        }

    }

    public static void saveImage(BufferedImage img, String namepath)
    {
        try {
            ImageIO.write(img, "JPEG", new File(namepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void makeDirectory(String directoryName)
    {
        File dir = new File(directoryName);
        dir.mkdir();
    }
    public static boolean isImage(File cFile)
    {
        String fileName = cFile.getName().toLowerCase();
        String[] allowed = {".jpg", ".jpeg", ".png"};
        for(String type: allowed)
        {
            if(fileName.endsWith(type))
                return true;
        }
        return false;
    }
}
