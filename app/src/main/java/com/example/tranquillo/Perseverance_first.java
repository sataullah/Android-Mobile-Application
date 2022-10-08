package com.example.tranquillo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Perseverance_first extends AppCompatActivity {
    private ImageView next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perseverance_first);

        next_btn = (ImageView)findViewById(R.id.next);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nb_pers = new Intent(Perseverance_first.this,Perseverance_second.class);
                startActivity(nb_pers);
                finish();
            }
        });
    }

    public void onclick_back(View view)
    {
        Intent back_btn = new Intent(Perseverance_first.this,Quotes.class);
        startActivity(back_btn);
        finish();
    }


}