package com.example.moovielab.ui.activity;

import android.os.Bundle;

import com.example.moovielab.R;
import com.example.moovielab.ui.activity.base.ToolbarActivity;

/**
 * Activity to display the app credits
 */
public class AboutActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.enableBackButton();
        toolbar.setTitle(R.string.about);
    }

    @Override
    protected int getToolbarLayout() {
        return R.layout.activity_about;
    }
}
