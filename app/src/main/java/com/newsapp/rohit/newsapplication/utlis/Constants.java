package com.newsapp.rohit.newsapplication.utlis;

/**
 * Created by ROHIT on 5/17/2018.
 */

public class Constants {

    public static final String BASE_URL = "https://newsapi.org/v2/top-headlines?";
    public static final String API_KEY = "57cd9041e77b49cb9506bb4b26e26f53";
    public static final String NEWS_SOURCE = BASE_URL + "country=us&category=business&apiKey=" + API_KEY;
    public static final String NEWS_SOURCE_ID = BASE_URL + "sources=";
    public static final String PLUS_API_KEY = "&apiKey=" + API_KEY;

    public static final String SOURCE_ID = "source_id";
    public static final String SOURCE_URL = "source_url";
    public static final String SOURCE_NAME = "source_name";

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko)" +
            " Chrome/55.0.2883.87 Safari/537.36";
}
