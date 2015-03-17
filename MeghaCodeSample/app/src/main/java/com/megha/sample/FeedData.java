package com.megha.sample;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Megha on 2/28/2015.
 * Fields to store feed data retrieved from JSON response.
 */
public class FeedData implements Serializable {
    private String feedImage;
    private String feedPublishedDate;
    private String feedTitle;
    private String feedSubTitle;
    private String feedText;
    private String feedType;

    public FeedData(){
            super();
        }

    public FeedData(String feedImage, String feedPublishedDate, String feedTitle, String feedSubTitle, String feedText, String feedType) {
        this.feedImage = feedImage;
        this.feedPublishedDate = feedPublishedDate;
        this.feedTitle = feedTitle;
        this.feedSubTitle = feedSubTitle;
        this.feedText = feedText;
        this.feedType = feedType;
    }

    public String getFeedImage() {
        return feedImage;
    }

    public void setFeedImage(String feedImage) {
        this.feedImage = feedImage;
    }

    public String getFeedPublishedDate() {
        SimpleDateFormat parseDate = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat monthDayYearformatter = new SimpleDateFormat(
                "MMMM dd, yyyy", Locale.US);
        try {
            return monthDayYearformatter.format(parseDate.parse(feedPublishedDate));
        } catch (ParseException e) {
           return "";
        }
    }

    public void setFeedPublishedDate(String feedPublishedDate) {
        this.feedPublishedDate = feedPublishedDate;
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public String getFeedSubTitle() {
        return feedSubTitle;
    }

    public void setFeedSubTitle(String feedSubTitle) {
        this.feedSubTitle = feedSubTitle;
    }

    public String getFeedText() {
        return feedText;
    }

    public void setFeedText(String feedText) {
        this.feedText = feedText;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }
}
