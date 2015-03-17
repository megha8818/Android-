package com.megha.sample.asynctask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.ListView;
import com.megha.sample.FeedData;
import com.megha.sample.R;
import com.megha.sample.adapter.FeedAdapter;
import com.megha.sample.util.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Megha on 2/28/2015.
 * Connect to server pull the JSON response parse and display it on the UI
 */
public class FeedAsyncTask extends
        AsyncTask<Void, Void, Boolean> {

    private Context context;
    private ListView feedListView;
    private String feedType;
    private ProgressDialog dialog;

    public FeedAsyncTask(Context context, ListView feedListView, String FeedType) {
        this.context = context;
        dialog = new ProgressDialog(context);
        this.feedListView = feedListView;
        this.feedType = FeedType;
    }

    //Show waiting dialog
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        dialog.setMessage(Constants.DIALOG_MESSAGE);
        dialog.show();
    }

    //COnnect to server and pull response data
    @Override
    protected Boolean doInBackground(Void... params) {
        if(Constants.feedDataList == null || Constants.feedDataList.isEmpty()) {
            String response = postJSON();
            try {
                return processJson(response);
            } catch (JSONException e) {
                return false;
            }
        }else {
            return true;
        }
    }

    //If everything goes well update the UI with data else display error message
    @Override
    protected void onPostExecute(Boolean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if(result){
            if (feedType.equals(context.getResources().getString(R.string.feed_All))){
                updateViewWithFeedData(context.getResources().getString(R.string.feed_All));
            }else if (feedType.equals(context.getResources().getString(R.string.feed_position_report))){
                updateViewWithFeedData(context.getResources().getString(R.string.feed_position_report));
            }else if (feedType.equals(context.getResources().getString(R.string.feed_rally_point))){
                updateViewWithFeedData(context.getResources().getString(R.string.feed_rally_point));
            }else if (feedType.equals(context.getResources().getString(R.string.feed_leading_edge))){
                updateViewWithFeedData(context.getResources().getString(R.string.feed_leading_edge));
            }
        }else{
            displayDialog(Constants.ERROR_KEYWORD, Constants.JSON_PARSE_ERROR_MESSAGE);
        }
    }

    //Connect to server URl and pull data.
    private String postJSON() {
        StringBuilder builder = new StringBuilder();
        try {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(Constants.URL_CONNECT);
            httpGet.setHeader("Accept", "application/json"); // or application/jsonrequest
            httpGet.setHeader(Constants.HEADER_X_PARSE_REST_API_KEY,
                   Constants.HEADER_X_PARSE_REST_API_KEY_VALUE);
            httpGet.setHeader(Constants.HEADER_X_PARSE_APPLICATION_ID,
                    Constants.HEADER_X_PARSE_APPLICATION_ID_VALUE);
            httpGet.setHeader(Constants.HEADER_X_CONTENT_TYPE, Constants.HEADER_X_CONTENT_TYPE_VALUE);
            HttpResponse response = httpclient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
        return builder.toString();
    }

    //Parse the JSON response
    public Boolean processJson(String response)
            throws JSONException {
        Constants.feedDataList = new ArrayList<>();
        JSONObject json = new JSONObject(response);
        JSONArray resultsFeedArray = json
                    .getJSONArray(Constants.TAG_RESULTS);
         for (int i = 0; i < resultsFeedArray.length(); i++) {
             JSONObject feedDataObject = resultsFeedArray.getJSONObject(i);
             FeedData feedData = new FeedData(
                             Constants.BASE_URL_IMAGE + feedDataObject.getString(Constants.TAG_IMAGE),
                             feedDataObject.getString(Constants.TAG_PUBLISHED),
                             feedDataObject.getString(Constants.TAG_TITLE),
                             feedDataObject.has(Constants.TAG_SUBTITLE)? feedDataObject.getString(Constants.TAG_SUBTITLE):"No Subtitle",
                             feedDataObject.getString(Constants.TAG_TEXT),
                             feedDataObject.getString(Constants.TAG_TYPE));
             Constants.feedDataMap.put(feedDataObject.getString(Constants.TAG_TYPE),feedData);
             Constants.feedDataList.add(feedData);
            }
        return true;
    }

    //Update UI for particular feed type.
    private void updateViewWithFeedData(String feedType){
        ArrayList<FeedData> feedDataListForType = new ArrayList<>();
        if(feedType.equals(context.getResources().getString(R.string.feed_All))){
            feedDataListForType = Constants.feedDataList;
        }else{
            feedDataListForType.addAll(Constants.feedDataMap.get(feedType));
        }
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheOnDisc(true)
                .considerExifParams(true)
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        FeedAdapter adapter = new FeedAdapter(context,
                R.layout.listview_cell_row, feedDataListForType, options, imageLoader);
        feedListView.setAdapter(adapter);
    }

    //Display dialog to convey message.
    private void displayDialog(String title, String message) {
        Dialog dialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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