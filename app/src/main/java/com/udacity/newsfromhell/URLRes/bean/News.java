package com.udacity.newsfromhell.URLRes.bean;

public class News{
    public String title;
    public String link;
    public String img_url;

    @Override
    public String toString() {
        return "News{" +
                "img_url='" + img_url + '\'' +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}