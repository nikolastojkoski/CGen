package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Nikola on 5/20/2016.
 */
public class ImageUtils {

    public static BufferedImage resize(BufferedImage img, int newW, int newH)
    {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);

        Graphics g = resizedImage.getGraphics();
        g.drawImage(tmp, 0, 0, null);

        return resizedImage;
    }
    public static void saveImage(BufferedImage img, String namepath)
    {
        try {
            ImageIO.write(img, "JPEG", new File(namepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
