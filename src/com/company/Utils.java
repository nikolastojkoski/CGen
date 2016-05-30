package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Nikola on 5/20/2016.
 */
public class Utils {

    public static String generateScreenshot(String templateNamePath, String topImageNamePath, String outputImageNamePath)
    {
        ImageBlender blender = new ImageBlender(templateNamePath, topImageNamePath);
        blender.execute();
        Utils.saveImage(blender.getImage(), outputImageNamePath);
        return outputImageNamePath;
    }
    public static BufferedImage resizeImage(BufferedImage img, int newW, int newH)
    {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);

        Graphics g = resizedImage.getGraphics();
        g.drawImage(tmp, 0, 0, null);

        return resizedImage;
    }
    public static void saveImage(BufferedImage img, String outputNamePath)
    {
        try {
            ImageIO.write(img, "JPEG", new File(outputNamePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveImage(String imageLoc, String outputNamePath)
    {
        try {
            BufferedImage img = ImageIO.read(new File(imageLoc));
            saveImage(img, outputNamePath);
        }catch(Exception e){e.printStackTrace();}
    }
    public static void eraseFile(String filename)
    {
        File f = new File(filename);
        if(f.exists())
        {
            f.delete();
        }
    }
    public static void makeDirectory(String directoryName)
    {
        File dir = new File(directoryName);
        dir.mkdir();
    }
    public static void sleep(int milliseconds)
    {
        try{
            Thread.sleep(milliseconds);
        }catch(Exception e){e.printStackTrace();}
    }

}
