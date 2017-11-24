package com.project.pro112.hydrateam.thepolycoffee.models;

import java.io.Serializable;

/**
 * Created by Huyn_TQT on 11/24/2017.
 */

public class ArticleNews implements Serializable {
    private String title, link, image, content;

    public ArticleNews(String title, String link, String image) {
        this.title = title;
        this.link = link;
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
