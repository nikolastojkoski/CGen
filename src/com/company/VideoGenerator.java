package com.company;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

/**
 * Created by Nikola on 5/16/2016.
 */
public class VideoGenerator {

    private String[] inputImageNameDirs;
    private String outputFileName = "myVideo.mp4";
    private int width;
    private int height;
    private double FRAME_RATE = 20;
    private int RUNTIME = 20;

    private static Map<String, File> imageMap = new HashMap<String, File>();

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
    public void setRuntime(int runtime)
    {
        RUNTIME = runtime;
    }
    public void convert()
    {
        IMediaWriter writer = ToolFactory.makeWriter(outputFileName);
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4, width/2, height/2);

        for(int i=0;i<inputImageNameDirs.length;i++)
        {
            String imageName = inputImageNameDirs[i];
            try{
                BufferedImage currentImage = convertToType(ImageIO.read(new File(imageName)), BufferedImage.TYPE_3BYTE_BGR);
                writer.encodeVideo(0, currentImage, 300*i, TimeUnit.MILLISECONDS);
            }catch(Exception e){e.printStackTrace();}
        }
        writer.close();
        System.out.println("Video Created!");

    }

    private BufferedImage convertToType(BufferedImage sourceImage, int targetType)
    {
        BufferedImage image;
        if (sourceImage.getType() == targetType)
            image = sourceImage;
        else
        {
            image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }
        return image;
    }


}