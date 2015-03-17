package com.apppartner.androidprogrammertest.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.asynctask.ImageDownloaderTask;
import com.apppartner.androidprogrammertest.models.ChatData;
import com.apppartner.androidprogrammertest.util.Constants;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import customwidget.CustomImageView;

/**
 * Created on 12/23/14.
 *
 * @author Megha
 */
public class ChatsArrayAdapter extends ArrayAdapter<ChatData>
{
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public ChatsArrayAdapter(Context context, List<ChatData> objects,  DisplayImageOptions options, ImageLoader imageLoader)
    {
        super(context, 0, objects);
        this.options = options;
        this.imageLoader = imageLoader;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ChatCell chatCell = new ChatCell();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.cell_chat, parent, false);

        chatCell.usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
        chatCell.messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        chatCell.userImageView = (CustomImageView) convertView.findViewById(R.id.mImageView_ChatImage);
        chatCell.userImageView.setCornerRadius(15);
        chatCell.userImageView.setBorderWidth(2);
        chatCell.userImageView.setOval(true);
        chatCell.userImageView.setBorderColor(Color.parseColor("#e1ebe9"));
        chatCell.userImageView.setRoundBackground(true);

        ChatData chatData = getItem(position);
        chatCell.usernameTextView.setText(chatData.username);
        chatCell.messageTextView.setText(chatData.message);
        //Universal Image loader library used.
        //https://github.com/nostra13/Android-Universal-Image-Loader
        imageLoader.displayImage(chatData.avatarURL,  chatCell.userImageView, options);
        // Retrieves an image specified by the URL, displays it in the UI.
//        if (chatCell.userImageView != null) {
//            new ImageDownloaderTask(chatCell.userImageView).execute(chatData.avatarURL);
//        }
        return convertView;
    }

    private static class ChatCell
    {
        TextView usernameTextView;
        TextView messageTextView;
        CustomImageView userImageView;
    }
}
