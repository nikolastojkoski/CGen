package com.company;

import com.google.api.client.auth.oauth2.Credential;
import com.google.common.collect.Lists;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args)
    {
        System.out.println("Main/main");

        final int UPLOAD_LIMIT = 6;
        final String inputDirectory = "input_pictures";
        final String outputDirectory = "output";
        final String downloadLinkBase = "http://adtrack123.pl/go.php?a_aid=56ce33fd8d518&&fn=";
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

        final List<String> bloggerScopes = Lists.newArrayList("https://www.googleapis.com/auth/blogger");
        final List<String> youtubeScopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");

        AssetManager assetManager = new AssetManager(inputDirectory);
        AccountManager accountManager = new AccountManager();

        ImgurUploader imgurUploader = new ImgurUploader(IMGUR_API_KEY);
        YoutubeSearch youtubeSearch = new YoutubeSearch(YT_SEARCH_API_KEY);
        GoogleSearch googleSearch = new GoogleSearch();
        WikipediaFetcher wikipediaFetcher = new WikipediaFetcher();
        BitlyShortener bitlyShortener = new BitlyShortener(BITLY_ACCOUNT, BITLY_API_KEY);
        HtmlGenerator htmlGenerator = new HtmlGenerator();
        VideoGenerator videoGenerator = new VideoGenerator();

        Credential bloggerCredential = null;
        boolean BLOGGER_AUTHORIZED = false;

        boolean noFreeAccounts = false;
        if(assetManager.isInputCorrect() == false)
        {
            System.out.println("INCORRECT INPUT FORMAT");
            return;
        }
        while(assetManager.next())
        {
            while((accountManager.getUploads() >= UPLOAD_LIMIT || !accountManager.isAuthenticated()))
            {
                if(accountManager.next() == false)
                {
                    noFreeAccounts = true;
                    break;
                }
            }
            if(noFreeAccounts)
                break;

            System.out.println(accountManager.getYoutubeEmail());

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

            try {
                bitlyShortener.shorten(URLEncoder.encode(downloadLinkBase + gameTitleQuery + "+Setup", "UTF-8"));
                System.out.println("Bitly short URL: " + bitlyShortener.getShortUrl());
            }catch (Exception e){e.printStackTrace();}

            System.out.println();

            htmlGenerator.setDownloadLink(bitlyShortener.getShortUrl());
            htmlGenerator.setImageLink(imgurUploader.getImageLink());
            htmlGenerator.setYoutubeID(youtubeSearch.getFirstResultID());
            htmlGenerator.setWikipediaSummary(wikipediaFetcher.getSummary());
            htmlGenerator.generate();
            htmlGenerator.saveHtmlFile(gameOutputDirectory + "/" + gameName + ".html");

            /*** AUTHORIZE BLOGGER AND YOUTUBE ****/
            if(!BLOGGER_AUTHORIZED || bloggerCredential.getExpiresInSeconds() < 50)
            {
                System.out.println("Attempting to authorize blogger...");
                bloggerCredential = GoogleAuth.authorize_full(bloggerScopes, "uploadPost", accountManager.getBloggerEmail(),
                        accountManager.getBloggerClientID(), accountManager.getBloggerClientSecret(), false);
                BLOGGER_AUTHORIZED = true;
            }

            String BLOGGER_ACCESS_TOKEN = bloggerCredential.getAccessToken();
            if(bloggerCredential.getExpiresInSeconds() < 0)
            {
                System.out.println("Unable to authorize blogger account!");
                System.out.println("Blogger Expiration: " + bloggerCredential.getExpiresInSeconds());
                break;
            }

            Credential youtubeCredential = GoogleAuth.authorize_full(youtubeScopes, "uploadvideo", accountManager.getYoutubeEmail(),
                    accountManager.getYoutubeClientID(), accountManager.getYoutubeClientSecret(), false);

            if(youtubeCredential.getExpiresInSeconds() < 0)
            {
                System.out.println("Unable to authorize youtube account!");
                System.out.println("Youtube Expiration: " + bloggerCredential.getExpiresInSeconds());
                break;
            }
            /*********************************************/

            System.out.println("Attempting to post to Blogger");
            BloggerPost bloggerPost = new BloggerPost(BLOGGER_ACCESS_TOKEN);
            bloggerPost.setBlogId(BLOG_ID);
            bloggerPost.setTitle(gameTitle + " Free Download PC");
            //bloggerPost.setContent("test Content");
            bloggerPost.setContent(htmlGenerator.getHtml());
            //bloggerPost.addParameters("isDraft=true"); //todo:delete
            if(bloggerPost.upload())
                System.out.println("Blogger Post Upload Success!");
            else
            {
                System.out.println("Error uploading blogger post!");
                break;
            }

            System.out.println();

            String screenshotNamePath = Utils.generateScreenshot(screenshotTemplate, gameImage, gameOutputDirectory + "/" + gameName + "ScreenShot.jpg");
            System.out.println("Generating video...");
            String[] videoScreens = {screenshotNamePath, gameImage, HowToInstallScreen, WebsiteLogoScreen};
            videoGenerator.setInputImages(videoScreens);
            videoGenerator.setInputSound(soundFile);
            videoGenerator.setSize(videoWidth, videoHeight);
            videoGenerator.setOutputFileName(gameOutputDirectory + "/" + gameName + ".avi");
            videoGenerator.generate();

            System.out.println("Attempting to upload video");
            List<String> tags = Arrays.asList(gameTitle,"free","games","downloads","recent","game","video game","pc","computer",
                    "mac","windows","download","full game","free games pc","free game download");
            YoutubeUploader uploader = new YoutubeUploader(youtubeCredential);

            uploader.setInputVideo(videoGenerator.getOutputFileName());
            uploader.setVideoTitle("How to Download " + gameTitle + " for FREE (PC)");
            uploader.setVideoDescription("Link to download: " + bloggerPost.getPostUrl());
            //uploader.setVideoDescription("Test Description");
            uploader.setVideoTags(tags);
            uploader.upload();
            accountManager.incrementUploads(1);

            Utils.saveFile(gameOutputDirectory + "/youtubeInfo.json",
                   Utils.makeJSONObject("title." + gameTitle, "description." + bloggerPost.getPostUrl()).toString());

        }
        accountManager.flush();
    }
}