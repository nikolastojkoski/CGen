package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args)
    {
        String directoryName = "Test_output";
        String templateNamePath = "resources/MainTemplate.jpg";
        String topImageNamePath = "resources/TestPNG.png";

        ImageBlender blender = new ImageBlender(templateNamePath, topImageNamePath);
        blender.execute();
        BufferedImage img = blender.getImage();

        makeDirectory(directoryName);
        saveImage(img, directoryName + "/screenshot.jpg");

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
}
