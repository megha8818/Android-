package com.megha.sample.util;

import com.megha.sample.FeedData;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;

/**
 * Created by Megha on 3/1/2015.
 * Constant Values used in project.
 */
public class Constants {

    public static String URL_CONNECT = "https://api.parse.com/1/classes/Update/";
    public static String BASE_URL_IMAGE = "http://files.appadmin.njasap.com/";
    public static String HEADER_X_PARSE_REST_API_KEY = "X-Parse-Rest-API-Key";
    public static String HEADER_X_PARSE_REST_API_KEY_VALUE = "k6Lgv0EqcYOnJAUfSLBXk5C5tZYuA3Yt3pk72nYU";
    public static String HEADER_X_PARSE_APPLICATION_ID = "X-Parse-Application-Id";
    public static String HEADER_X_PARSE_APPLICATION_ID_VALUE = "sGmOpZxKtx7DyyT9wc33LYsl9N27s1t8bpXPdHyp";
    public static String HEADER_X_CONTENT_TYPE = "Content-Type";
    public static String HEADER_X_CONTENT_TYPE_VALUE = "application/json";

    public static String TAG_RESULTS = "results";
    public static String TAG_IMAGE = "image";
    public static String TAG_PUBLISHED = "published";
    public static String TAG_TITLE = "title";
    public static String TAG_SUBTITLE = "subtitle";
    public static String TAG_TEXT = "text";
    public static String TAG_TYPE = "type";

    public static Multimap<String,FeedData> feedDataMap = ArrayListMultimap.create();
    public static ArrayList<FeedData> feedDataList = new ArrayList<>();

    public static final String INTERNET_CONNECTION_ERROR_MESSAGE = "No internet connection. Please connect to internet and try again.";
    public static final String JSON_PARSE_ERROR_MESSAGE = "Error retrieving data.";
    public static final String ERROR_KEYWORD = "Error";
    public static String DIALOG_MESSAGE = "Loading Feeds...";

    public static final String BUNDLE_SELECTED_FEED_DETAILS = "SelectedFeedDetails";

}
