package com.company;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Nikola on 5/5/2016.
 */
public class ImageCompressor {


    public ImageCompressor(){}

    public static void compressImage(BufferedImage image, int targetSize, String outputNamePath)
    {
        compress(image, outputNamePath, targetSize);
    }
    public static void compressImage(String namepath, int targetSize, String outputNamePath)
    {
        try {
            BufferedImage image = ImageIO.read(new File(namepath));
            compress(image, outputNamePath, targetSize);
        }catch(Exception e){e.printStackTrace();}
    }
    private static void compress(BufferedImage image, String outputNamePath, int targetSize)
    {
        //TODO:FIX





    }

}
