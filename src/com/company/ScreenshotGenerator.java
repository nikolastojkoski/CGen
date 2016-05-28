package com.company;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Nikola on 5/2/2016.
 */
public class ScreenshotGenerator
{

    private String inputDirectoryName;
    private String outputDirectoryName;
    private String templateNamePath;

    public ScreenshotGenerator(String inputDirectoryName, String outputDirectoryName, String templateNamePath)
    {
        this.inputDirectoryName = inputDirectoryName;
        this.outputDirectoryName = outputDirectoryName;
        this.templateNamePath = templateNamePath;
    }
    public void execute()
    {
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
                ImageBlender blender = new ImageBlender(templateNamePath, topImageNamePath);
                blender.execute();
                BufferedImage img = blender.getImage();

                /** Save */
                makeDirectory(outputDirectoryName);
                ImageUtils.saveImage(img, outputDirectoryName + "/" + fileName.substring(0, fileName.indexOf(".")) + "_screenshot.jpg");
            }

        }

    }

    private void makeDirectory(String directoryName)
    {
        File dir = new File(directoryName);
        dir.mkdir();
    }
    private boolean isImage(File cFile)
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
