package com.megha.sample;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.megha.sample.asynctask.FeedAsyncTask;
import com.megha.sample.util.ConnectionDetector;
import com.megha.sample.util.Constants;

/**
 * Created by MeghaV on 2/28/2015.
 * Update the feed data for respective feedtype and add it to the fragment.
 */
public class FeedActivity extends Fragment {

    private ListView mFeedListView;
    private String feedType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.activity_feed, container, false);
        Bundle bundle = this.getArguments();
        feedType = bundle.getString(getResources().getString(R.string.feed_type));
        mFeedListView = (ListView) view.findViewById(R.id.listView1);
        mFeedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Fragment detail = new DetailsActivity();
                Bundle bundle = new Bundle();
                FeedData fd = Constants.feedDataList.get(position);
                bundle.putSerializable(Constants.BUNDLE_SELECTED_FEED_DETAILS, fd);
                detail.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.mFrameLayout_ContentFrame, detail).addToBackStack("details").commit();
            }
        });
        ConnectionDetector cd = new ConnectionDetector(getActivity().getApplicationContext());
        if (cd.isConnectingToInternet()) {
            new FeedAsyncTask(getActivity(), mFeedListView, feedType).execute();
        } else {
            //Display dialog when error
            displayDialog(Constants.ERROR_KEYWORD, Constants.INTERNET_CONNECTION_ERROR_MESSAGE);
        }
        return view;
    }

    //Display dialog to convey message.
    private void displayDialog(String title, String message) {
        Dialog dialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
