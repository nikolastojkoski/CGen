package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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
    public static void makeFile(String fileDirName)
    {
        File file = new File(fileDirName);
        try {
            file.createNewFile();
        }catch(Exception e){e.printStackTrace();}
    }
    public static void sleep(int milliseconds)
    {
        try{
            Thread.sleep(milliseconds);
        }catch(Exception e){e.printStackTrace();}
    }
    public static void saveFile(String outputNameDir, String data)
    {
        try{
            FileWriter writer = new FileWriter(outputNameDir);
            writer.write(data);
            writer.flush();
            writer.close();
        }catch(Exception e){e.printStackTrace();}
    }
    public static void copyFile(String source, String sink)
    {
        File sourceFile = new File(source);
        File sinkFile = new File(sink);

        try {
            Files.copy(sourceFile.toPath(), sinkFile.toPath());
        }catch(Exception e){e.printStackTrace();}

    }
    public static String getInput(String question)
    {
        Scanner reader = new Scanner(System.in);
        System.out.println(question + ": ");
        return reader.next();
    }
    public static String getCurrentDateTime()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
