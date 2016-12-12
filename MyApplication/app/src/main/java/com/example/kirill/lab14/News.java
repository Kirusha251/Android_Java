package com.example.kirill.lab14;

import android.net.Uri;

/**
 * Created by KIrill on 12.12.2016.
 */
public class News {
    private String content;
    private Uri url;

    public News() {
    }

    public News(String content, Uri url) {
        this.content = content;
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }
}
