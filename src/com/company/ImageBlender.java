package com.company;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

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
    private int resizeX = 640;
    private int resizeY = 360;
    private int blendPositionX = 198;
    private int blendPositionY = 25;

    public ImageBlender(String botLocation, String topLocation)
    {
        this.botLocation = botLocation;
        this.topLocation = topLocation;
    }
    public void setResize(int x, int y)
    {
        resizeX = x;
        resizeY = y;
    }
    public void setBlendPosition(int x, int y)
    {
        blendPositionX = x;
        blendPositionY = y;
    }
    public BufferedImage getImage()
    {
        return blended;
    }

    public void execute()
    {
        loadImages();
        top = resize(top, resizeX, resizeY);

        blended = new BufferedImage(bottom.getWidth(), bottom.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = blended.getGraphics();
        g.drawImage(bottom,0,0,null);
        g.drawImage(top, blendPositionX, blendPositionY, null);
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

            if(topLocation.toLowerCase().endsWith(".png"))
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
