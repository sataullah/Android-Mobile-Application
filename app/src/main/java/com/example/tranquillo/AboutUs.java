package com.example.tranquillo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class AboutUs extends AppCompatActivity {
    //Initialise variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void btn_back(View view)
    {
        Intent back_btn = new Intent(AboutUs.this,navigation_drawer.class);
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
        //Recreate activity
        recreate();
    }

    public void ClickProgress(View view)
    {
        //Redirect activity to Progress
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