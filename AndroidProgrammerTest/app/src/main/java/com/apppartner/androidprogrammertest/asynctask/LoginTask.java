package com.apppartner.androidprogrammertest.asynctask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import com.apppartner.androidprogrammertest.MainActivity;
import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.util.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Megha on 2/21/2015.
 *
 * Asynchronous code which connects to the server and pulls response and parse it.
 *
 */
public class LoginTask extends
        AsyncTask<String, Void, JSONObject> {

    private Context context;
    private long apiCallMilliseconds;

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        //Post the request and process the response.
        try {
            return postAndProcessJSON(params[0],params[1]);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }


    @Override
    protected void onPostExecute(JSONObject result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
       try {
           //Parse the response and display the data.
           if (result.getString(Constants.TAG_CODE).equals(Constants.SUCCESS_KEYWORD)) {
               displayDialog(Constants.LOGIN_KEYWORD,LoginSuccessMessage(result.getString(Constants.TAG_CODE),result.getString(Constants.TAG_MESSAGE),apiCallMilliseconds));
           }else{
               displayDialog(Constants.LOGIN_KEYWORD, Constants.LOGIN_ERROR_MESSAGE);
           }
       }catch (JSONException e) {
               displayDialog(Constants.LOGIN_KEYWORD, "Error Parsing JSON data");
       }
     }

    //Post request to http://dev.apppartner.com/AppPartnerProgrammerTest/scripts/login.php with user entered
    //username and password. If everything goes well process the response.
    private JSONObject postAndProcessJSON(String userName, String password)  throws JSONException {

        StringBuilder builder = new StringBuilder();
        JSONObject json = null;
        try {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Constants.LOGIN_URL_CONNECT);
            List<BasicNameValuePair> nameValuePairs = new
                    ArrayList<BasicNameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair(Constants.USERNAME_KEYWORD,
                    userName));
            nameValuePairs.add(new BasicNameValuePair(Constants.PASSWORD_KEYWORD,
                    password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
                    "UTF-8"));
            long startTime = System.nanoTime();
            HttpResponse response = httpclient.execute(httppost);
            long endTime = System.nanoTime();
            apiCallMilliseconds = (endTime - startTime)/1000000;
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            //Check the status. If 200 then success.
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
            json = new JSONObject(builder.toString());
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
        return json;
    }

    //Login message for login activity
    public static final String LoginSuccessMessage(String codeData, String messageData, Long apiCallMilliseconds)
    {
        return "Code : " + codeData
                + "\n Message : " + messageData
                + "\n Time for API call : " + Long.toString(apiCallMilliseconds)+ " milliseconds";
    }

    //Displays dialog to convey message to the user.
    private void displayDialog(String title, String message) {
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
        dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }
}

