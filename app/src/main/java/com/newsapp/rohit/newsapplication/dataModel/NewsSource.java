package com.newsapp.rohit.newsapplication.dataModel;

/**
 * Created by ROHIT on 5/17/2018.
 * DataModel for news source
 */

public class NewsSource {

    private String mId;
    private String mImageUrl;
    private String mName;

    public NewsSource(String mId,String mImageUrl,String mName) {
        this.mImageUrl = mImageUrl;
        this.mId = mId;
        this.mName = mName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }
}
