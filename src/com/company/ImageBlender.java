package com.company;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.nio.Buffer;
import javax.imageio.*;
import javax.swing.*;

/**
 * Created by Nikola on 4/27/2016.
 */
public class ImageBlender
{
    private String botLocation;
    private String topLocation;
    private BufferedImage bottom;
    private BufferedImage top;
    private BufferedImage blended;

    public ImageBlender(String botLocation, String topLocation)
    {
        this.botLocation = botLocation;
        this.topLocation = topLocation;

    }

    public BufferedImage getImage()
    {
        return blended;
    }

    public void execute()
    {
        loadImages();
        top = resize(top, 640, 360);

        blended = new BufferedImage(bottom.getWidth(), bottom.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = blended.getGraphics();
        g.drawImage(bottom,0,0,null);
        g.drawImage(top, 198, 25,null);
    }

    private BufferedImage resize(BufferedImage img, int newW, int newH)
    {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);

        Graphics g = resizedImage.getGraphics();
        g.drawImage(tmp, 0, 0, null);

        return resizedImage;
    }

    private void loadImages()
    {
        try
        {
            bottom = ImageIO.read(new File(botLocation));
            top = ImageIO.read(new File(topLocation));

            if(topLocation.toLowerCase().contains(".png"))
            {
                top = convert_PNG_to_JPG(top);
            }
        }
        catch (IOException e) {
            System.out.println("Error loading images! ");
            e.printStackTrace();
        }
    }

    private BufferedImage convert_PNG_to_JPG(BufferedImage pngImage)
    {
        BufferedImage jpgImage = new BufferedImage(pngImage.getWidth(), pngImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        Graphics g = jpgImage.getGraphics();
        g.drawImage(pngImage, 0, 0, Color.WHITE, null);

        return jpgImage;
    }

}
