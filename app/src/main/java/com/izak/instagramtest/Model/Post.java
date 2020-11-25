package com.izak.instagramtest.Model;

public class Post {
    private String postid;
    private String postimage;
    private String description;
    private String publisher;
    private String timeend;
    private String inputmoney;
    private String tag;
    private String nowmoney;

    public Post(String postid, String postimage, String description, String publisher, String timeend, String inputmoney,String tag, String nowmoney) {
        this.postid = postid;
        this.postimage = postimage;
        this.description = description;
        this.publisher = publisher;
        this.timeend = timeend;
        this.inputmoney = inputmoney;
        this.tag = tag;
        this.nowmoney = nowmoney;
    }

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) { this.timeend = timeend; }

    public String getInputmoney() { return inputmoney; }

    public void setInputmoney(String inputmoney) { this.inputmoney = inputmoney; }

    public String getTag() { return tag; }

    public void setTag(String tag) { this.tag = tag; }

    public String getNowmoney() { return nowmoney; }

    public void setNowmoney(String nowmoney) { this.nowmoney = nowmoney; }
}
