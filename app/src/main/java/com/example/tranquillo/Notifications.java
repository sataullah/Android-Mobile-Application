package com.example.tranquillo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Notifications extends AppCompatActivity {
    //Initialise variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void onclick_water_rem(View view)
    {
        Intent btn_water_rem = new Intent(Notifications.this,Water_notification.class);
        startActivity(btn_water_rem);
        finish();
    }

    public void onclick_screen_brk(View view)
    {
        Intent btn_screenb = new Intent(Notifications.this,ScreenBreak_notification.class);
        startActivity(btn_screenb);
        finish();
    }

    public void btn_back(View view)
    {
        Intent back_btn = new Intent(Notifications.this,navigation_drawer.class);
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
        //Redirect activity
        navigation_drawer.redirectActivity(this,QuickDecision.class);
    }

    //Notification
    public void ClickNotification(View view)
    {
        //Recreate activity
        recreate();
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