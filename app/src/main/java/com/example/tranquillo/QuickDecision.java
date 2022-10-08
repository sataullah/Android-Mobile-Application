package com.example.tranquillo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.Random;

public class QuickDecision extends AppCompatActivity {
    //Initialise variable
    DrawerLayout drawerLayout;
    ImageView QuickDecImage;
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_decision);


        QuickDecImage = (ImageView) findViewById(R.id.quickdecision1);
        QuickDecImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotatePic();
            }
        });

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    private void rotatePic()
    {
        int i = random.nextInt(3)+1;
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.rotate);
        QuickDecImage.startAnimation(anim);
        switch (i)
        {
            case 1:
                QuickDecImage.setImageResource(R.drawable.qd_pic1);
                break;

            case 2:
                QuickDecImage.setImageResource(R.drawable.qd_pic2);
                break;

            case 3:
                QuickDecImage.setImageResource(R.drawable.qd_pic3);
                break;
        }
    }

    public void btn_back(View view)
    {
        Intent back_btn = new Intent(QuickDecision.this,navigation_drawer.class);
        startActivity(back_btn);
        finish();
    }

    public void ClickMenu(View view)
    {
        //Open drawer
        navigation_drawer.openDrawer(drawerLayout);
    }

    public void ClickToExit(View view){
        //Close drawer
        navigation_drawer.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view)
    {
        //Redirect activity to home
        navigation_drawer.redirectActivity(this,navigation_drawer.class);
    }

    public void ClickAboutUs(View view)
    {
        //Redirect activity to About Us page
        navigation_drawer.redirectActivity(this,AboutUs.class);
    }

    public void ClickMoodQuiz(View view)
    {
        //Redirect activity to Mood Quiz
        navigation_drawer.redirectActivity(this,MoodQuiz.class);
    }

    public void ClickProgress(View view)
    {
        //Redirect activity
        navigation_drawer.redirectActivity(this,Progress.class);
    }

    //Quotes
    public void ClickQuotes(View view)
    {
        //Redirect activity
        navigation_drawer.redirectActivity(this,Quotes.class);
    }

    //Anagram Game
    public void ClickGame(View view)
    {
        //Redirect activity
        navigation_drawer.redirectActivity(this,AnagramGame.class);
    }

    //Calm Music
    public void ClickMusic(View view)
    {
        //Redirect activity
        navigation_drawer.redirectActivity(this,CalmMusic.class);
    }

    //Quick Decision
    public void ClickDecision(View view)
    {
        //Recreate activity
        recreate();
    }

    //Notification
    public void ClickNotification(View view)
    {
        //Redirect activity
        navigation_drawer.redirectActivity(this, Notifications.class);
    }

    //Logout
    public void ClickLogout(View view)
    {
        //Close App
        navigation_drawer.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close Drawer
        navigation_drawer.closeDrawer(drawerLayout);
    }
}