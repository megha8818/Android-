package com.megha.sample;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

//Implements navigation drawer with options to load the data.
public class MainActivity extends ActionBarActivity {
    private Toolbar mToolbar;
    private  DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    protected  ListView mListView_LeftDrawer;
    private ArrayAdapter<String> navigationDrawerAdapter;
    private String[] leftSliderData = new String[4] ;
    protected FrameLayout mFrameLayout_ContentFrame;
    protected static int selectedPosition;
    private boolean isDrawerOptionSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDrawer();
    }

    //Intialize the view by retrieving fields
    private void initView() {
        mListView_LeftDrawer = (ListView) findViewById(R.id.mListView_LeftDrawer);
        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mFrameLayout_ContentFrame = (FrameLayout)findViewById(R.id.mFrameLayout_ContentFrame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        leftSliderData = getResources().getStringArray(R.array.navigation_array);
        navigationDrawerAdapter=new ArrayAdapter<String>( MainActivity.this, android.R.layout.simple_list_item_1, leftSliderData);
        mListView_LeftDrawer.setAdapter(navigationDrawerAdapter);

        mListView_LeftDrawer.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                isDrawerOptionSelected = true;
                selectedPosition = position;
                mDrawerLayout.closeDrawer(mListView_LeftDrawer);
            }
        });
    }

    //Initialize navigation drawer.
    private void initDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(isDrawerOptionSelected) {
                    openActivity(selectedPosition);
                    isDrawerOptionSelected = false;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        openActivity(0);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Update the fragment with respective UI for feed type.
    protected void openActivity(int position) {
        switch (position) {
            case 0:
                updateFragmentBasedOnFeedType(getResources().getString(R.string.feed_All));
                break;

            case 1:
                updateFragmentBasedOnFeedType(getResources().getString(R.string.feed_position_report));
                break;

            case 2:
                updateFragmentBasedOnFeedType(getResources().getString(R.string.feed_rally_point));
                break;

            case 3:
                updateFragmentBasedOnFeedType(getResources().getString(R.string.feed_leading_edge));
                break;

            default:
                break;
        }
    }

    private void updateFragmentBasedOnFeedType(String feedType){
        Fragment feedActivity = new FeedActivity();
        Bundle bundle = new Bundle();
        bundle.putString("FeedType", feedType);
        feedActivity.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.mFrameLayout_ContentFrame,feedActivity).commit();
    }

    public ListView getLeftDrawerList() {
        return mListView_LeftDrawer;
    }

    public void setLeftDrawerList(ListView leftDrawerList) {
        this.mListView_LeftDrawer = leftDrawerList;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.mDrawerLayout = drawerLayout;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}