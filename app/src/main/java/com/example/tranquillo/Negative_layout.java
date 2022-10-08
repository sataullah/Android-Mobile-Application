package com.example.tranquillo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Negative_layout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negative_layout);
    }

    public void btn_back(View view)
    {
        Intent back_btn = new Intent(Negative_layout.this,Positive_layout.class);
        startActivity(back_btn);
        finish();
    }

    public void onclick_finish(View view)
    {
        Intent finish_btn = new Intent(Negative_layout.this,navigation_drawer.class);
        startActivity(finish_btn);
        finish();
    }
}