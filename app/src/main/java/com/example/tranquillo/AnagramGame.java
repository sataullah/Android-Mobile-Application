package com.example.tranquillo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class AnagramGame extends AppCompatActivity {
    //Initialise variable
    DrawerLayout drawerLayout;

    private TextView place_find;
    private EditText word_entered;
    private Button check_btn;
    private Button next_btn;
    private Button btn;
    private String wordToFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anagram_game);

        place_find = (TextView) findViewById(R.id.place_to_find);
        word_entered = (EditText) findViewById(R.id.word_entered);
        check_btn = (Button) findViewById(R.id.btn_chk);
        next_btn = (Button) findViewById(R.id.btn_next);

        newGame();

        check_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == check_btn ){
                    validate();

                }
                else if (view == next_btn){
                    newGame();

                }
            }
        });

        next_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == check_btn ){
                    validate();

                }
                else if (view == next_btn){
                    newGame();

                }
            }
        });

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
    }


    private void validate() {
        String w = word_entered.getText().toString();

        if(wordToFind.equals(w)){
           Toast toast = Toast.makeText(this,"Congratulations! You Spelled the Word Correctly",Toast.LENGTH_LONG);
            View view = toast.getView();
            TextView text = view.findViewById(android.R.id.message);
            view.setBackground(getDrawable(R.drawable.rounded_toast));
            text.setTextColor(Color.parseColor("#522749"));
            text.setTextSize(21);
            text.setTypeface(Typeface.create("serif",Typeface.BOLD));
            toast.show();
            newGame();
        }
        else
        {
           Toast toast = Toast.makeText(this,"Please Retry!",Toast.LENGTH_LONG);
            View view = toast.getView();
            TextView text = view.findViewById(android.R.id.message);
            view.setBackground(getDrawable(R.drawable.rounded_toast));
            text.setTextColor(Color.parseColor("#522749"));
            text.setTextSize(21);
            text.setTypeface(Typeface.create("serif",Typeface.BOLD));
            toast.show();
        }
    }

    private void newGame() {
        wordToFind = Anagram_Class.randomPlace();
        String placeshuffled = Anagram_Class.shufflePlace(wordToFind);
        place_find.setText(placeshuffled);
        word_entered.setText("");
    }

    public void btn_back(View view)
    {
        Intent back_btn = new Intent(AnagramGame.this,navigation_drawer.class);
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
        //Recreate activity
        recreate();
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