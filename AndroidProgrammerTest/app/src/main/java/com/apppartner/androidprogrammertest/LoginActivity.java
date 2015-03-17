package com.apppartner.androidprogrammertest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.apppartner.androidprogrammertest.asynctask.LoginTask;
import com.apppartner.androidprogrammertest.util.ConnectionDetector;
import com.apppartner.androidprogrammertest.util.Constants;

import customwidget.CustomEditText;
import customwidget.CustomTextView;

public class LoginActivity extends ActionBarActivity
{
    private CustomEditText mEditText_Login_Username = null;
    private CustomEditText mEditText_Login_Password = null;
    private Button mButton_Login_LoginButton = null;
    private Toolbar mToolbarTop = null;
    private CustomTextView mCustomTextView_HeaderText = null;
    private ImageButton mImageButton_BackButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditText_Login_Username = (CustomEditText) findViewById(R.id.mEditText_Login_UserName);
        mEditText_Login_Password = (CustomEditText) findViewById(R.id.mEditText_Login_Password);
        mButton_Login_LoginButton = (Button) findViewById(R.id.mButton_Login_LoginButton);
        //Access tool bar and set custom header text
        mToolbarTop = (Toolbar) findViewById(R.id.toolbar);
        mCustomTextView_HeaderText = (CustomTextView) mToolbarTop.findViewById(R.id.mCustomTextView_HeaderText);
        mCustomTextView_HeaderText.setText(Constants.LOGIN_HEADER_TEXT);
        mImageButton_BackButton = (ImageButton) mToolbarTop.findViewById(R.id.mImageButton_BackButton);

        mImageButton_BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Login button when clicked validate input and connect to server
        //parse data and display it.
        mButton_Login_LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs(mEditText_Login_Username.getText().toString(), mEditText_Login_Password.getText().toString()))
                {
                    //Check for internet connection. If success, connect to server with data.
                    ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                    if(cd.isConnectingToInternet()) {
                        new LoginTask(LoginActivity.this)
                                .execute(mEditText_Login_Username.getText().toString(), mEditText_Login_Password.getText().toString());
                    }else{
                        //Display dialog when error
                        displayDialog(Constants.LOGIN_KEYWORD, Constants.INTERNET_CONNECTION_ERROR_MESSAGE);
                    }
                }else{
                    //Display dialog when error
                        displayDialog(Constants.LOGIN_KEYWORD, Constants.LOGIN_ERROR_MESSAGE);
                }
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

    //Validate user inputs
    private boolean validateInputs(String userName, String password) {
        if(userName.equals("") || password.equals("") || !userName.equals(Constants.USERNAME) || !password.equals(Constants.PASSWORD))
        {
            return false;
        }else{
            return true;
        }
    }

    //Display dialog to convey message.
    private void displayDialog(String title, String message) {
        Dialog dialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }
}
