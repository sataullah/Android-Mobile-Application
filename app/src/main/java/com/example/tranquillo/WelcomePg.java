package com.example.tranquillo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomePg extends AppCompatActivity {
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_pg);
    }

    public void onclick_login(View view)
    {
        Intent btn_login = new Intent(WelcomePg.this,Login.class);
        startActivity(btn_login);
        finish();
    }

    public void onclick_signup(View view)
    {
        Intent btn_signup = new Intent(WelcomePg.this,SignUp.class);
        startActivity(btn_signup);
        finish();
    }
}