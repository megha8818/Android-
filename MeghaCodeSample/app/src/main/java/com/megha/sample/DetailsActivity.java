package com.megha.sample;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.megha.sample.customwidget.CustomTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Megha on 2/28/2015.
 * update the display UI and replace it in the main fragment.
 */
public class DetailsActivity extends Fragment{
    private ScrollView mScrollView_FeedDetails;
    private CustomTextView mCustomTextView_FeedType;
    private CustomTextView mCustomTextView_FeedTitle;
    private CustomTextView mCustomTextView_FeedPublishedDate;
    private CustomTextView mCustomTextView_FeedSubTitle;
    private CustomTextView mCustomTextView_FeedDescription;
    private ImageView mImageView_FeedImage;
    private FeedData selectedFeedData;
    ImageLoader imageLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bn = this.getArguments();
        selectedFeedData = (FeedData) bn.getSerializable("SelectedFeedDetails");
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.activity_details, container, false);

        mCustomTextView_FeedType = (CustomTextView) view.findViewById(R.id.mTextView_FeedType);
        mCustomTextView_FeedTitle = (CustomTextView) view.findViewById(R.id.mTextView_Details_FeedTitle);
        mCustomTextView_FeedPublishedDate = (CustomTextView) view.findViewById(R.id.mTextView_Details_FeedPublishDate);
        mCustomTextView_FeedSubTitle = (CustomTextView) view.findViewById(R.id.mTextView_Details_FeedSubtitle);
        mCustomTextView_FeedDescription = (CustomTextView) view.findViewById(R.id.mTextView_Details_FeedContent);
        mImageView_FeedImage = (ImageView) view.findViewById(R.id.mImageView_Details_FeedImage);
        mScrollView_FeedDetails = (ScrollView) view.findViewById(R.id.mScrollView_FeedDetailsHolder);

        mCustomTextView_FeedType.setText(selectedFeedData.getFeedType());
        mCustomTextView_FeedTitle.setText(selectedFeedData.getFeedTitle());
        mCustomTextView_FeedPublishedDate.setText(selectedFeedData.getFeedPublishedDate());
        mCustomTextView_FeedDescription.setText(selectedFeedData.getFeedText());
        mCustomTextView_FeedSubTitle.setText(selectedFeedData.getFeedSubTitle());
        imageLoader.displayImage(selectedFeedData.getFeedImage(),mImageView_FeedImage);
        //mScrollView_FeedDetails.setOn
        return view;
    }


}
