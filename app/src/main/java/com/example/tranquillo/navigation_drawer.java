package com.example.tranquillo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class navigation_drawer extends AppCompatActivity {
    //Initalise Variable
    private Button btn;
    TextView txtview;
    DrawerLayout drawerLayout;
    FirebaseDatabase db_firebase;
    DatabaseReference databaseusers;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        txtview = findViewById(R.id.user_name);

        db_firebase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseusers = db_firebase.getReference("User");
        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference username = databaseusers.child(id).child("userName");
        
        //Assign Variable
        drawerLayout = findViewById(R.id.drawer_layout);

        username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getValue().toString();
                txtview.setText("Hello,"+ " " + username +"!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Intent for Quick Decision
    public void onclick_qudecision(View view)
    {
        Intent qd_btn = new Intent(navigation_drawer.this,QuickDecision.class);
        startActivity(qd_btn);
        finish();
    }

    //Intent for Mood Quiz
    public void onclick_mquiz(View view)
    {
        Intent mq_btn = new Intent(navigation_drawer.this,MoodQuiz.class);
        startActivity(mq_btn);
        finish();
    }

    //Intent for calm music
    public void onclick_cmusic(View view)
    {
        Intent cm_btn = new Intent(navigation_drawer.this,CalmMusic.class);
        startActivity(cm_btn);
        finish();
    }

    //Intent for Anagram Game
    public void onclick_angrame(View view)
    {
        Intent ag_btn = new Intent(navigation_drawer.this,AnagramGame.class);
        startActivity(ag_btn);
        finish();
    }

    public void ClickMenu(View view){
        //Open Drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickToExit(View view){
        //Close Drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //Close drawer layout
        //Check Condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            //When drawer is open, close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //Home
    public void ClickHome(View view)
    {
        //Recreate activity
        recreate();
    }

    //About Us
    public void ClickAboutUs(View view)
    {
        //Redirect activity to About Us page
        navigation_drawer.redirectActivity(this,AboutUs.class);
    }

    //Mood Quiz
    public void ClickMoodQuiz(View view)
    {
        //Redirect activity
        redirectActivity(this,MoodQuiz.class);
    }

    //Progress
    public void ClickProgress(View view)
    {
        //Redirect activity
        redirectActivity(this,Progress.class);
    }

    //Quotes
    public void ClickQuotes(View view)
    {
        //Redirect activity
        redirectActivity(this,Quotes.class);
    }

    //Anagram Game
    public void ClickGame(View view)
    {
        //Redirect activity
        redirectActivity(this,AnagramGame.class);
    }

    //Calm Music
    public void ClickMusic(View view)
    {
        //Redirect activity
        redirectActivity(this,CalmMusic.class);
    }

    //Quick Decision
    public void ClickDecision(View view)
    {
        //Redirect activity
        redirectActivity(this,QuickDecision.class);
    }

    //Notification
    public void ClickNotification(View view)
    {
        //Redirect activity
        redirectActivity(this, Notifications.class);
    }

    //Logout
    public void ClickLogout(View view)
    {
        logout(this);
    }

    public static void logout(Activity activity)
    {
        //Initialise Alert Dialog Box
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Set title
        builder.setTitle("Logout");
        //Set message
        builder.setMessage("Are you sure you want to logout?");
        //Positive Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Finish Activity
                activity.finishAffinity();
                //Exit App
                System.exit(0);
            }
        });
        //Negative No Button
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss Dialog Box
                dialog.dismiss();
            }
        });
        //Show Dialog Box
        builder.show();
    }

    public static void redirectActivity(Activity activity,Class aclass) {
        //Initialise Intent
        Intent intent = new Intent(activity,aclass);
        //Set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Start Activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Close drawer
        closeDrawer(drawerLayout);
    }
}