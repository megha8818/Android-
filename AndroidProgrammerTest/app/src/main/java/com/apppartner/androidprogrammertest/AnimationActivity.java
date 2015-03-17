package com.apppartner.androidprogrammertest;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View.OnDragListener;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import com.apppartner.androidprogrammertest.util.Constants;
import customwidget.CustomTextView;

/**
 * Animate the App partner logo
 */

public class AnimationActivity extends Activity implements OnDragListener, View.OnLongClickListener, View.OnTouchListener
{
    private Button mButton_FadeAnimation = null;
    private ImageView mImageView_AppPartnerLogo = null;
    private Animation animFadeinFadeOut;
    private Toolbar mToolbarTop = null;
    private CustomTextView mCustomTextView_HeaderText = null;
    private ImageButton mImageButton_BackButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        //Fade button
        mButton_FadeAnimation = (Button) findViewById(R.id.mButton_Animation_FadeButton);
        animFadeinFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in_out);

        mImageView_AppPartnerLogo = (ImageView) findViewById(R.id.mImageView_AppPartnerLogo);
        //Setting Listners to logo image and its layout.
        findViewById(R.id.mLinearLayout_AnimationDetailsHolder).setOnDragListener(this);
        mImageView_AppPartnerLogo.setOnDragListener(this);
        mImageView_AppPartnerLogo.setOnLongClickListener(this);
        mToolbarTop = (Toolbar) findViewById(R.id.toolbar);
        mCustomTextView_HeaderText = (CustomTextView) mToolbarTop.findViewById(R.id.mCustomTextView_HeaderText);
        mCustomTextView_HeaderText.setText(Constants.ANIMATION_HEADER_TEXT);
        mImageButton_BackButton = (ImageButton) mToolbarTop.findViewById(R.id.mImageButton_BackButton);

        //Listener to fade logo on click
        mButton_FadeAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView_AppPartnerLogo.startAnimation(animFadeinFadeOut);
            }
        });

        //Back button to navigate through views
        mImageButton_BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View imageView, MotionEvent event)
    {
        return clipImageDragged(imageView);
    }

    @Override
    public boolean onLongClick(View imageView)
    {
        return clipImageDragged(imageView);
    }

    @Override
    public boolean onDrag(View destinationView, DragEvent dragEvent) {
        View draggedImageView = (View) dragEvent.getLocalState();
        //Loading rotate animations.
        Animation animRotateWithZoom = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate_with_zoom);
        // Handles each of the expected events
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // Determines if this View can accept the dragged data
                if (dragEvent.getClipDescription()
                        .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    // returns true to indicate that the View can accept the dragged data.
                    return true;
                }
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                return true;

            case DragEvent.ACTION_DROP:
                  /* the listener receives this action type when
                  drag shadow released over the target view
            the action only sent here if ACTION_DRAG_STARTED returned true
            return true if successfully handled the drop else false*/
                ViewGroup draggedImageViewParentLayout
                        = (ViewGroup) draggedImageView.getParent();
                draggedImageViewParentLayout.removeView(draggedImageView);
                LinearLayout bottomLinearLayout = (LinearLayout) destinationView;
                bottomLinearLayout.addView(draggedImageView);
                draggedImageView.setVisibility(View.VISIBLE);
                mImageView_AppPartnerLogo.startAnimation(animRotateWithZoom);
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                //After Drag ended. Restore the image with an animation.
                if (!dragEvent.getResult()) {
                    draggedImageView.setVisibility(View.VISIBLE);
                    mImageView_AppPartnerLogo.startAnimation(animRotateWithZoom);
                }
                return true;

            default:
                break;
        }
        return false;
    }

    //Create the shadow of the image to be dragged and make the image invisible.
    public boolean clipImageDragged(View imageView){
        ClipData clipData = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
        imageView.startDrag(clipData, shadowBuilder, imageView, 0);
        imageView.setVisibility(View.INVISIBLE);
        return true;
    }
}
