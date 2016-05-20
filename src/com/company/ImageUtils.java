package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;

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






}
