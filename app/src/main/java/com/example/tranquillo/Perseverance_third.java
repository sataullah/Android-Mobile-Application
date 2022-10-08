package com.example.tranquillo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Perseverance_third extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perseverance_third);
    }

    public void onclick_back_pers(View view)
    {
        Intent back_btn = new Intent(Perseverance_third.this,Perseverance_second.class);
        startActivity(back_btn);
        finish();
    }

    public void onclick_exit(View view)
    {
        Intent exit_btn = new Intent(Perseverance_third.this,Quotes.class);
        startActivity(exit_btn);
        finish();
    }
}