package com.company;

import org.jcodec.api.awt.SequenceEncoder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Created by Nikola on 5/16/2016.
 */
public class VideoGenerator {

    private String[] inputImageNameDirs;
    private String outputFileName = "myVideo.mp4";
    private BufferedImage[] images;
    private int width;
    private int height;
    private double FRAME_RATE = 20;

    public VideoGenerator(){}
    public void setInputImages(String[] inputImageNames)
    {
        this.inputImageNameDirs = inputImageNames;
    }
    public void setOutputFileName(String outputFileName)
    {
        this.outputFileName = outputFileName;
    }
    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
    public void setFrameRate(double frameRate)
    {
        FRAME_RATE = frameRate;
    }
    public void generate()
    {
        getImages();
        try {
            File file = new File(outputFileName);
            SequenceEncoder encoder = new SequenceEncoder(file);
            for(int i=0;i<images.length;i++)
            {
                BufferedImage image = images[i];
                for(int j=0;j<144;j++)
                    encoder.encodeImage(image);
            }
            encoder.finish();
        } catch (IOException e) {e.printStackTrace();}

    }
    private void getImages()
    {
        images = new BufferedImage[inputImageNameDirs.length];
        for(int i=0;i<inputImageNameDirs.length;i++)
        {
            try{
                images[i] = ImageIO.read(new File(inputImageNameDirs[i]));
                images[i] = ImageUtils.resize(images[i], width, height);
            }catch(Exception e){}
        }
    }
}