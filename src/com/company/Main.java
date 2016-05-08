package com.company;

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
        imgurUploader.upload("resources/TestPic.jpg");
        System.out.println(imgurUploader.getImageLink());

        GoogleSearch googleSearch = new GoogleSearch();
        googleSearch.search("Need+for+speed+2015+site:wikipedia.org");
        System.out.println(googleSearch.getFirstResultLink());

        WikipediaFetcher wikipediaFetcher = new WikipediaFetcher();
        System.out.println(wikipediaFetcher.getSummary("https://en.wikipedia.org/wiki/Need_for_Speed_(2015_video_game)"));

    }
}
