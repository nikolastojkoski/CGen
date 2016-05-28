package com.company;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Nikola on 5/5/2016.
 */
public class ImageCompressor {

    private BufferedImage image;
    private int targetSize;
    private JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);

    public ImageCompressor(){}

    public void compressImage(BufferedImage image, int targetSize)
    {
        this.image = image;
        this.targetSize = targetSize;
        calculateParams();
    }
    public void compressImage(String namepath, int targetSize)
    {
        try {
            this.image = ImageIO.read(new File(namepath));
            this.targetSize = targetSize;
        }catch(Exception e){e.printStackTrace();}
    }

    public void saveImage(String outputDir, String filename)
    {
        makeDirectory(outputDir);
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        try {
            writer.setOutput(new FileImageOutputStream(new File(outputDir + "/" + filename + ".jpg")));
            writer.write(null, new IIOImage(image,null, null), jpegParams);
        }catch(Exception e){}
    }

    private void calculateParams()
    {
        float imageSize = getImageSize();
        if(imageSize > targetSize)
        {
            float compressionQuality = (targetSize/imageSize);
            jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(compressionQuality);
        }
    }

    private float getImageSize()
    {
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        try{
            ImageIO.write(image, "jpg", tmp);
            tmp.close();
        }catch(Exception e){}

        return (float)tmp.size();
    }

    private void makeDirectory(String directoryName)
    {
        File dir = new File(directoryName);
        dir.mkdir();
    }

}
