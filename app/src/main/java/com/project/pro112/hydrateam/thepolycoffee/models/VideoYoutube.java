package com.project.pro112.hydrateam.thepolycoffee.models;

import java.io.Serializable;

/**
 * Created by Huyn_TQT on 11/27/2017.
 */

public class VideoYoutube implements Serializable {
    private String mTitleVideo, mThumbnailsVideo, mIDVideo;

    public VideoYoutube(String mTitleVideo, String mThumbnailsVideo, String mIDVideo) {
        this.mTitleVideo = mTitleVideo;
        this.mThumbnailsVideo = mThumbnailsVideo;
        this.mIDVideo = mIDVideo;
    }

    public String getmTitleVideo() {
        return mTitleVideo;
    }

    public void setmTitleVideo(String mTitleVideo) {
        this.mTitleVideo = mTitleVideo;
    }

    public String getmThumbnailsVideo() {
        return mThumbnailsVideo;
    }

    public void setmThumbnailsVideo(String mThumbnailsVideo) {
        this.mThumbnailsVideo = mThumbnailsVideo;
    }

    public String getmIDVideo() {
        return mIDVideo;
    }

    public void setmIDVideo(String mIDVideo) {
        this.mIDVideo = mIDVideo;
    }
}
