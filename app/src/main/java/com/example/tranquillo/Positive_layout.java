package com.example.tranquillo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Positive_layout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positive_layout);
    }

    public void btn_back(View view)
    {
        Intent back_btn = new Intent(Positive_layout.this,MoodQuiz.class);
        startActivity(back_btn);
        finish();
    }

    public void onclick_next(View view)
    {
        Intent next_btn = new Intent(Positive_layout.this,Negative_layout.class);
        startActivity(next_btn);
        finish();
    }
}