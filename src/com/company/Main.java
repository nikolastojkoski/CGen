package com.company;

import com.google.api.client.auth.oauth2.Credential;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args)
    {
        final int UPLOAD_LIMIT = 5;
        final String inputDirectory = "input_pictures";
        final String outputDirectory = "output";
        final String downloadLinkBase = "http://adtrack123.pl/go.php?a_aid=56ce33fd8d518&fn=";

        final String screenshotTemplate = "resources/MainTemplate.jpg";
        final String HowToInstallScreen = "resources/HowToInstall.jpg";
        final String WebsiteLogoScreen = "resources/WebsiteLogo.jpg";
        final String soundFile = "resources/sound.mp3";
        final int videoWidth = 1280;
        final int videoHeight = 720;

        final String BLOG_ID = "7459603265214871506";
        final String IMGUR_API_KEY = "cb98c2f98ad76fa";
        final String YT_SEARCH_API_KEY = "AIzaSyDrTVLJKtzt9Sm0pba75XmL0u4F1n7zGgg";
        final String BITLY_ACCOUNT = "ravenmind";
        final String BITLY_API_KEY = "R_149d89614d124e9d9dd58606f26cb296";

        AssetManager assetManager = new AssetManager(inputDirectory);
        AccountManager accountManager = new AccountManager();

        ImgurUploader imgurUploader = new ImgurUploader(IMGUR_API_KEY);
        YoutubeSearch youtubeSearch = new YoutubeSearch(YT_SEARCH_API_KEY);
        GoogleSearch googleSearch = new GoogleSearch();
        WikipediaFetcher wikipediaFetcher = new WikipediaFetcher();
        BitlyShortener bitlyShortener = new BitlyShortener(BITLY_ACCOUNT, BITLY_API_KEY);
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        VideoGenerator videoGenerator = new VideoGenerator();
        BloggerAuth bloggerAuth = new BloggerAuth();

        boolean noFreeAccounts = false;
        while(assetManager.next())
        {
            while(accountManager.getUploads() >= UPLOAD_LIMIT)
            {
                if(accountManager.next() == false)
                {
                    noFreeAccounts = true;
                    break;
                }
            }
            if(noFreeAccounts)
                break;

            String gameTitle =  assetManager.getGameTitle();
            String gameTitleQuery = assetManager.getGameTitle().replace(' ', '+');
            String gameName = assetManager.getGameName();
            String gameImage = inputDirectory + "/" + assetManager.getImageFile();
            String gameOutputDirectory = outputDirectory + "/" + gameName;

            System.out.println("gameTitle: " + gameTitle);
            System.out.println("gameTitleQuery: " + gameTitleQuery);
            System.out.println("gameName: " + gameName);
            System.out.println("gameImage: " + gameImage);
            System.out.println("gameOutputDirectory: " + gameOutputDirectory);
            System.out.println();

            Utils.makeDirectory(outputDirectory);
            Utils.makeDirectory(gameOutputDirectory);
            Utils.saveImage(gameImage, gameOutputDirectory + "/" + gameName + ".jpg");

            System.out.println("Uploading to imgur...");
            imgurUploader.upload(gameOutputDirectory + "/" + gameName + ".jpg");
            System.out.println("ImgurLink: " + imgurUploader.getImageLink());

            System.out.println("YoutubeSearch Query: " + gameTitleQuery + "+game+trailer");
            youtubeSearch.searchVideo(gameTitleQuery + "+game+trailer");
            System.out.println("YoutubeTrailerID: " + youtubeSearch.getFirstResultID());

            System.out.println("GoogleSearch Query: " + gameTitleQuery + "+game+site:wikipedia.org");
            googleSearch.search(gameTitleQuery + "+game+site:wikipedia.org");
            System.out.println("Google FirstResultLink: " + googleSearch.getFirstResultLink());

            System.out.println("Wikipedia Query: " + googleSearch.getFirstResultLink());
            wikipediaFetcher.fetchSummary(googleSearch.getFirstResultLink());
            System.out.println("Wikipedia Summary: " + wikipediaFetcher.getSummary());

            System.out.println(downloadLinkBase + gameTitleQuery + "+Setup");
            bitlyShortener.shorten(downloadLinkBase + gameTitleQuery + "+Setup");
            System.out.println("Bitly short URL: " + bitlyShortener.getShortUrl());

            htmlGenerator.setDownloadLink(bitlyShortener.getShortUrl());
            htmlGenerator.setImageLink(imgurUploader.getImageLink());
            htmlGenerator.setYoutubeID(youtubeSearch.getFirstResultID());
            htmlGenerator.setWikipediaSummary(wikipediaFetcher.getSummary());
            htmlGenerator.generate();
            htmlGenerator.saveHtmlFile(gameOutputDirectory + "/" + gameName + ".html");

            String bloggerPostUrl = null;
            List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/blogger");
            try {
                Credential credential = GoogleAuth.authorize(scopes, "uploadPost", "nikolastojkoski3@gmail.com");
                System.out.println(credential.getAccessToken());
                //TODO: insert post with blogger builder
                BloggerPost bloggerPost = new BloggerPost(credential.getAccessToken());
                bloggerPost.setBlogId(BLOG_ID);
                bloggerPost.setTitle(gameTitle + " Free Download PC");
                bloggerPost.setContent(htmlGenerator.getHtml());
                bloggerPost.upload();
                bloggerPostUrl = bloggerPost.getPostUrl();

            }catch(Exception e){e.printStackTrace();}

            String screenshotNamePath = Utils.generateScreenshot(screenshotTemplate, gameImage, gameOutputDirectory + "/" + gameName + "ScreenShot.jpg");

            String[] videoScreens = {screenshotNamePath, gameImage, HowToInstallScreen, WebsiteLogoScreen};
            videoGenerator.setInputImages(videoScreens);
            videoGenerator.setInputSound(soundFile);
            videoGenerator.setSize(videoWidth, videoHeight);
            videoGenerator.setOutputFileName(gameOutputDirectory + "/" + gameName + ".avi");
            videoGenerator.generate();
            Utils.sleep(5000);

            List<String> tags = Arrays.asList(gameTitle,"free","games","downloads","recent","game","video game","pc","computer",
                    "mac","windows","download","full game","free games pc","free game download");
            YoutubeUploader uploader = new YoutubeUploader(accountManager.getEmail(),accountManager.getYoutubeClientID(), accountManager.getYoutubeClientSecret());
            uploader.setInputVideo(videoGenerator.getOutputFileName());
            uploader.setVideoTitle("How to Download " + gameTitle + " for FREE (PC)");
            uploader.setVideoDescription("Link to download: " + bloggerPostUrl);
            uploader.setVideoTags(tags);
            uploader.upload();
            accountManager.incrementUploads(1);

        }
    }
}