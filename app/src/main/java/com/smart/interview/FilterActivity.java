package com.smart.interview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smart.interview.main.MainActivity;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiter);
        startMainUI();
    }
    private void startMainUI() {
        Intent uiIntent = new Intent(this, MainActivity.class);
        startActivity(uiIntent);
    }
}
