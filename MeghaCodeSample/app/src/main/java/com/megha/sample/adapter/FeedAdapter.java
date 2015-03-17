package com.megha.sample.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.megha.sample.FeedData;
import com.megha.sample.R;
import com.megha.sample.customwidget.CustomTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Megha on 2/28/2015.
 *
 * Adapter to load the data to listview.
 *
 */
public class FeedAdapter extends ArrayAdapter<FeedData> {

    Context context;
    int layoutResourceId;
    ArrayList<FeedData> feedDataList = null;
    DisplayImageOptions options;
    ImageLoader imageLoader;

    public FeedAdapter(Context context, int layoutResourceId, ArrayList<FeedData> feedDataList,
                       DisplayImageOptions options, ImageLoader imageLoader) {
        super(context, layoutResourceId, feedDataList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.feedDataList = feedDataList;
        this.options = options;
        this.imageLoader = imageLoader;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FeedHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new FeedHolder();
            holder.feedImage = (ImageView)row.findViewById(R.id.mImageView_FeedImage);
            holder.feedTitle = (CustomTextView)row.findViewById(R.id.mTextView_FeedTitle);
            holder.feedSubTitle = (CustomTextView)row.findViewById(R.id.mTextView_FeedSubTitle);
            holder.feedPublishedDate = (CustomTextView)row.findViewById(R.id.mTextView_FeedPublishedDate);
            row.setTag(holder);
        }
        else
        {
            holder = (FeedHolder)row.getTag();
        }

        FeedData feedData = feedDataList.get(position);
        holder.feedTitle.setText(feedData.getFeedTitle());
        holder.feedSubTitle.setText(feedData.getFeedSubTitle());
        holder.feedPublishedDate.setText(feedData.getFeedPublishedDate());
        imageLoader.displayImage(feedData.getFeedImage(),holder.feedImage, options);
        return row;
    }

    static class FeedHolder
    {
        ImageView feedImage;
        CustomTextView feedTitle;
        CustomTextView feedSubTitle;
        CustomTextView feedPublishedDate;
    }
}