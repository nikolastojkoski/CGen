package com.company;

import java.util.*;

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
        wikipediaFetcher.fetchSummary("https://en.wikipedia.org/wiki/Need_for_Speed_(2015_video_game)");
        System.out.println(wikipediaFetcher.getSummary());

        HtmlGenerator htmlGenerator = new HtmlGenerator();
        htmlGenerator.setDownloadLink("https://google.com");
        htmlGenerator.setImageLink(imgurUploader.getImageLink());
        htmlGenerator.setYoutubeID(youtubeSearch.getFirstResultID());
        htmlGenerator.setWikipediaSummary(wikipediaFetcher.getSummary());
        htmlGenerator.generate();
        System.out.println(htmlGenerator.getHtml());

        ApiAuthenticator authenticator = new ApiAuthenticator();
        authenticator.setRefreshToken(" < REFRESH TOKEN > ");
        authenticator.setClientID(" < CLIENT ID >");
        authenticator.setClientSecret(" < CLIENT SECRET > ");
        authenticator.execute();

        BloggerPost post = new BloggerPost(authenticator.getAccessToken());
        post.setBlogId("7459603265214871506");
        post.setContent(htmlGenerator.getHtml());
        post.setTitle("Test Post");
        post.upload();
        System.out.println(post.getPostUrl());

        VideoGenerator videoGenerator = new VideoGenerator();
        String[] imageNames = {"resources/MainTemplate.jpg","resources/TestPic.jpg","resources/MainTemplate.jpg","resources/TestPic.jpg"};
        videoGenerator.setInputImages(imageNames);
        videoGenerator.setInputSound("resources/sound.mp3");
        videoGenerator.setOutputFileName("test_output/testVideo.avi");
        videoGenerator.setSize(1280,720);
        videoGenerator.generate();

        //TODO: edit resources/client_secrets.json with new keys

        List<String> tags = Arrays.asList("helloo","my","nigga");
        YoutubeUploader uploader = new YoutubeUploader();
        uploader.setInputVideo("test_output/testVideo.avi");
        uploader.setVideoTitle("Test Video Title 123");
        uploader.setVideoDescription("test description asd");
        uploader.setVideoTags(tags);
        uploader.upload();

    }
}