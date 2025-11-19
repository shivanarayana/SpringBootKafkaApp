package com.howtodoinjava.kafka;

public class UrlHealthResult {
    private String url;
    private String status;

    public UrlHealthResult(String url, String status) {
        this.url = url;
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }
}

