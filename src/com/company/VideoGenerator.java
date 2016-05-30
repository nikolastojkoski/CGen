package com.company;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Created by Nikola on 5/16/2016.
 */
public class VideoGenerator {

    private String[] inputImageNameDirs;
    private String inputSound;
    private String outputFileName = "myVideo.avi";
    private int width;
    private int height;
    private double FRAME_RATE = 0.16;

    public VideoGenerator(){}
    public void setInputImages(String[] inputImageNames)
    {
        this.inputImageNameDirs = inputImageNames;
    }
    public void setOutputFileName(String outputFileName)
    {
        this.outputFileName = outputFileName;
    }
    public void setInputSound(String inputSoundName)
    {
        this.inputSound = inputSoundName;
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
    public String getOutputFileName()
    {
        return outputFileName;
    }
    public void generate()
    {
        emptyFrameFolder();
        getImages();
        eraseOutputFile();

        String command = "ffmpeg -i " + inputSound +
                      " -framerate " + FRAME_RATE +
                      " -i videoGen/frame%d.jpg" +
                      " -c:v libx264 -vf fps=25 -pix_fmt yuv420p -c:a copy -shortest " +
                      outputFileName;

        try {
            Process r = Runtime.getRuntime().exec("cmd /c start /wait cmd.exe /K \"" + command + " && exit && echo end\"");
            r.waitFor();
        }catch(Exception e){e.printStackTrace();}

    }
    private void getImages()
    {
        BufferedImage current;
        for(int i=0;i<inputImageNameDirs.length;i++)
        {
            try{
                current = ImageIO.read(new File(inputImageNameDirs[i]));
                current = Utils.resizeImage(current, width, height);
                Utils.saveImage(current, "videoGen/frame"+i+".jpg");
                if(i == inputImageNameDirs.length - 1)
                    Utils.saveImage(current, "videoGen/frame"+(i+1)+".jpg");
            }catch(Exception e){e.printStackTrace();}
        }
    }
    private void emptyFrameFolder()
    {
        File folder = new File("videoGen");
        String[] contents = folder.list();
        for(String f: contents)
        {
            File currentFile = new File(folder.getPath(), f);
            currentFile.delete();
        }
    }
    private void eraseOutputFile()
    {
        Utils.eraseFile(outputFileName);
    }
}