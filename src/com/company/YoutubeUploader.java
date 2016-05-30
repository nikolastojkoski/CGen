package com.company;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.common.collect.Lists;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import jdk.nashorn.internal.runtime.ECMAErrors;
import org.json.JSONObject;

/**
 * Created by Nikola on 5/29/2016.
 */
public class YoutubeUploader {

    private YouTube youtube;
    private static final String VIDEO_FILE_FORMAT = "video/*";
    private String VIDEO_FILENAME = "sample-video.mp4";
    private String videoTitle;
    private String videoDescription;
    private List<String> videoTags;
    private String userAccount;
    private String CLIENT_ID;
    private String CLIENT_SECRET;

    public YoutubeUploader(String userAccount, String clientId, String clientSecret) {
        this.userAccount = userAccount;
        this.CLIENT_ID = clientId;
        this.CLIENT_SECRET = clientSecret;
    }

    public void setInputVideo(String inputVideo) {
        VIDEO_FILENAME = inputVideo;
    }

    public void setVideoTitle(String title) {
        this.videoTitle = title;
    }

    public void setVideoDescription(String description) {
        this.videoDescription = description;
    }

    public void setVideoTags(List<String> tags) {
        this.videoTags = tags;
    }

    public void upload() {
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");

        try {
            Credential credential = GoogleAuth.authorize(scopes, "uploadvideo", userAccount, CLIENT_ID, CLIENT_SECRET);

            youtube = new YouTube.Builder(GoogleAuth.HTTP_TRANSPORT, GoogleAuth.JSON_FACTORY, credential).setApplicationName(
                    "youtube-cmdline-uploadvideo-sample").build();

            System.out.println("Uploading: " + VIDEO_FILENAME);

            VideoStatus status = new VideoStatus();
            status.setPrivacyStatus("public");

            VideoSnippet snippet = new VideoSnippet();
            snippet.setTitle(videoTitle);
            snippet.setDescription(videoDescription);
            snippet.setTags(videoTags);

            Video videoObjectDefiningMetadata = new Video();
            videoObjectDefiningMetadata.setStatus(status);
            videoObjectDefiningMetadata.setSnippet(snippet);

            InputStream inputStream = new FileInputStream(VIDEO_FILENAME);
            InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT, inputStream);

            YouTube.Videos.Insert videoInsert = youtube.videos()
                    .insert("snippet,statistics,status", videoObjectDefiningMetadata, mediaContent);

            MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();
            uploader.setDirectUploadEnabled(false);

            MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    switch (uploader.getUploadState()) {
                        case INITIATION_STARTED:
                            System.out.println("Initiation Started");
                            break;
                        case INITIATION_COMPLETE:
                            System.out.println("Initiation Completed");
                            System.out.println("Uploading...");
                            break;
                        case MEDIA_IN_PROGRESS:
                            System.out.println("Upload in progress");
                            System.out.println("Upload percentage: " + uploader.getProgress());
                            break;
                        case MEDIA_COMPLETE:
                            System.out.println("Upload Completed!");
                            break;
                        case NOT_STARTED:
                            System.out.println("Upload Not Started!");
                            break;
                    }
                }
            };
            uploader.setProgressListener(progressListener);

            // Call the API and upload the video.
            Video returnedVideo = videoInsert.execute();

            System.out.println("Upload Success, videoID: " + returnedVideo.getId());
            System.out.println("Link to video: " + "https://www.youtube.com/watch?v=" + returnedVideo.getId());

        }catch(Exception e){e.printStackTrace();} /*catch (GoogleJsonResponseException e) {
            System.out.println(e.getMessage());
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }*/

    }

}
