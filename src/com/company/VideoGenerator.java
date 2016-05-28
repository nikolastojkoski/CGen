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
    private String projectLocation;

    public VideoGenerator(String projectLocation)
    {
        this.projectLocation = projectLocation;
    }
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
    public void generate()
    {
        emptyFrameFolder();
        getImages();
        eraseOutputFile();

        String[] commands = new String[2];
        commands[0] = "cd " + projectLocation;
        commands[1] = "ffmpeg -i " + inputSound +
                      " -framerate " + FRAME_RATE +
                      " -i videoGen/frame%d.jpg" +
                      " -c:v libx264 -vf fps=25 -pix_fmt yuv420p -c:a copy -shortest " +
                      outputFileName;

        //TODO: Execute in CMD

    }
    private void getImages()
    {
        System.out.println("inputSize: " + inputImageNameDirs.length);
        BufferedImage current;
        for(int i=0;i<inputImageNameDirs.length;i++)
        {
            try{
                current = ImageIO.read(new File(inputImageNameDirs[i]));
                current = ImageUtils.resize(current, width, height);
                ImageUtils.saveImage(current, "videoGen/frame"+i+".jpg");
                System.out.println("videoGen/frame"+i+".jpg");
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
        File f = new File(outputFileName);
        if(f.exists())
        {
            f.delete();
        }
    }
}