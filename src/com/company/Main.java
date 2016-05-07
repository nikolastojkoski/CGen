package com.company;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args)
    {

        String inputDirectoryName = "input_pictures";
        String outputDirectoryName = "output_screenshots";
        String templateNamePath = "resources/MainTemplate.jpg";

        ScreenshotGenerator screenshotGenerator = new ScreenshotGenerator(inputDirectoryName, outputDirectoryName, templateNamePath);
        screenshotGenerator.execute();

        YoutubeSearch youtubeSearch = new YoutubeSearch("AIzaSyDrTVLJKtzt9Sm0pba75XmL0u4F1n7zGgg");
        youtubeSearch.searchVideo("far+cry+3+game+trailer");
        System.out.println(youtubeSearch.getFirstResultID());

        BitlyShortener bitlyShortener = new BitlyShortener("ravenmind", "R_149d89614d124e9d9dd58606f26cb296");
        System.out.println(bitlyShortener.getShortUrl("http://gamedownloads2016.com"));

        ImageCompressor imageCompressor = new ImageCompressor();
        imageCompressor.compressImage("resources/TestPic.jpg", 100000);
        imageCompressor.saveImage("test_output", "compressedImage");

        ImgurUploader imgurUploader = new ImgurUploader("cb98c2f98ad76fa");
        File imageFile = new File("resources/TestPic.jpg");
        imgurUploader.upload(imageFile);
        System.out.println(imgurUploader.getImageLink());

    }
}
