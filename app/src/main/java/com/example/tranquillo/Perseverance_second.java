package com.example.tranquillo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Perseverance_second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perseverance_second);
    }

    public void onclick_back_Pers(View view)
    {
        Intent btn_back = new Intent(Perseverance_second.this,Perseverance_first.class);
        startActivity(btn_back);
        finish();
    }

    public void onclick_next_pers(View view)
    {
        Intent next_btn = new Intent(Perseverance_second.this,Perseverance_third.class);
        startActivity(next_btn);
        finish();
    }
}