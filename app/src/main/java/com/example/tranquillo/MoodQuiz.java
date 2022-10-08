package com.example.tranquillo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MoodQuiz extends AppCompatActivity {
    //Initialise variable
    AppCompatButton btn_next;
    RadioGroup moodsgroup;
    RadioButton tired, gloomy, anxious, sad, calm, angry, happy, fear;
    RadioButton MoodOption;
    TextView c_date;
    DrawerLayout drawerLayout;
    FirebaseDatabase db_firebase;
    DatabaseReference reference;
    public FirebaseAuth mAuth;
    int i;

    String strMood, userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_quiz);

        Calendar calendar = Calendar.getInstance();
        //String currentdate = DateFormat.getDateInstance().format(calendar.getTime());
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String currentdate = format.format(calendar.getTime());

        TextView textviewdate = findViewById(R.id.text_view_date);
        textviewdate.setText(currentdate);

        btn_next = findViewById(R.id.next_btn);
        moodsgroup = findViewById(R.id.moods_group);
        c_date = findViewById(R.id.text_view_date);


        tired = findViewById(R.id.tired_rb);
        gloomy = findViewById(R.id.gloomy_rb);
        anxious = findViewById(R.id.anxious_rb);
        sad = findViewById(R.id.sad_rb);
        calm = findViewById(R.id.calm_rb);
        angry = findViewById(R.id.angry_rb);
        happy = findViewById(R.id.happy_rb);
        fear = findViewById(R.id.fear_rb);

        String datec = c_date.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        userid = mAuth.getCurrentUser().getUid();
        db_firebase = FirebaseDatabase.getInstance();
        reference = db_firebase.getReference("Mood Quiz");


        moodsgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                MoodOption = moodsgroup.findViewById(checkedId);

                switch (checkedId){
                    case R.id.tired_rb:
                        Toast toast_t = Toast.makeText(MoodQuiz.this, "Mood Selected: Tired", Toast.LENGTH_SHORT);
                        View view = toast_t.getView();
                        TextView text = view.findViewById(android.R.id.message);
                        view.setBackground(getDrawable(R.drawable.rounded_toast));
                        text.setTextColor(Color.parseColor("#522749"));
                        text.setTextSize(21);
                        text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                        toast_t.show();
                        strMood = MoodOption.getText().toString();
                        break;
                    case R.id.gloomy_rb:
                        Toast toast_g = Toast.makeText(MoodQuiz.this, "Mood Selected: Gloomy", Toast.LENGTH_SHORT);
                        View view_g = toast_g.getView();
                        TextView text_g = view_g.findViewById(android.R.id.message);
                        view_g.setBackground(getDrawable(R.drawable.rounded_toast));
                        text_g.setTextColor(Color.parseColor("#522749"));
                        text_g.setTextSize(21);
                        text_g.setTypeface(Typeface.create("serif",Typeface.BOLD));
                        toast_g.show();
                        strMood = MoodOption.getText().toString();
                        break;
                    case R.id.anxious_rb:
                        Toast toast_a = Toast.makeText(MoodQuiz.this, "Mood Selected: Anxious", Toast.LENGTH_SHORT);
                        View view_a = toast_a.getView();
                        TextView text_a = view_a.findViewById(android.R.id.message);
                        view_a.setBackground(getDrawable(R.drawable.rounded_toast));
                        text_a.setTextColor(Color.parseColor("#522749"));
                        text_a.setTextSize(21);
                        text_a.setTypeface(Typeface.create("serif",Typeface.BOLD));
                        toast_a.show();
                        strMood = MoodOption.getText().toString();
                        break;
                    case R.id.sad_rb:
                        Toast toast_s = Toast.makeText(MoodQuiz.this, "Mood Selected: Sad", Toast.LENGTH_SHORT);
                        View view_s = toast_s.getView();
                        TextView text_s = view_s.findViewById(android.R.id.message);
                        view_s.setBackground(getDrawable(R.drawable.rounded_toast));
                        text_s.setTextColor(Color.parseColor("#522749"));
                        text_s.setTextSize(21);
                        text_s.setTypeface(Typeface.create("serif",Typeface.BOLD));
                        toast_s.show();
                        strMood = MoodOption.getText().toString();
                        break;
                    case R.id.calm_rb:
                        Toast toast_c = Toast.makeText(MoodQuiz.this, "Mood Selected: Calm", Toast.LENGTH_SHORT);
                        View view_c = toast_c.getView();
                        TextView text_c = view_c.findViewById(android.R.id.message);
                        view_c.setBackground(getDrawable(R.drawable.rounded_toast));
                        text_c.setTextColor(Color.parseColor("#522749"));
                        text_c.setTextSize(21);
                        text_c.setTypeface(Typeface.create("serif",Typeface.BOLD));
                        toast_c.show();
                        strMood = MoodOption.getText().toString();
                        break;
                    case R.id.angry_rb:
                        Toast toast = Toast.makeText(MoodQuiz.this, "Mood Selected: Angry", Toast.LENGTH_SHORT);
                        View view_an = toast.getView();
                        TextView text_an = view_an.findViewById(android.R.id.message);
                        view_an.setBackground(getDrawable(R.drawable.rounded_toast));
                        text_an.setTextColor(Color.parseColor("#522749"));
                        text_an.setTextSize(21);
                        text_an.setTypeface(Typeface.create("serif",Typeface.BOLD));
                        toast.show();
                        strMood = MoodOption.getText().toString();
                        break;
                    case R.id.happy_rb:
                        Toast toast_h = Toast.makeText(MoodQuiz.this, "Mood Selected: Happy", Toast.LENGTH_SHORT);
                        View view_h = toast_h.getView();
                        TextView text_h = view_h.findViewById(android.R.id.message);
                        view_h.setBackground(getDrawable(R.drawable.rounded_toast));
                        text_h.setTextColor(Color.parseColor("#522749"));
                        text_h.setTextSize(21);
                        text_h.setTypeface(Typeface.create("serif",Typeface.BOLD));
                        toast_h.show();
                        strMood = MoodOption.getText().toString();
                        break;
                    case R.id.fear_rb:
                        Toast toast_f = Toast.makeText(MoodQuiz.this, "Mood Selected: Fear", Toast.LENGTH_SHORT);
                        View view_f = toast_f.getView();
                        TextView text_f = view_f.findViewById(android.R.id.message);
                        view_f.setBackground(getDrawable(R.drawable.rounded_toast));
                        text_f.setTextColor(Color.parseColor("#522749"));
                        text_f.setTextSize(21);
                        text_f.setTypeface(Typeface.create("serif",Typeface.BOLD));
                        toast_f.show();
                        strMood = MoodOption.getText().toString();
                        break;
                    default:
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected_id = moodsgroup.getCheckedRadioButtonId();
                MoodOption = findViewById(selected_id);
                String flag = MoodOption.getText().toString();
                final moodinputholder holder = new moodinputholder(datec,flag);
                i=0;

                if(tired.isChecked())
                {
                    userid = mAuth.getCurrentUser().getUid();
                    reference.child(userid).child(currentdate).setValue(holder);
                    Toast toast = Toast.makeText(MoodQuiz.this, "Mood Successfully recorded", Toast.LENGTH_SHORT);
                    View view_t = toast.getView();
                    TextView text = view_t.findViewById(android.R.id.message);
                    view_t.setBackground(getDrawable(R.drawable.rounded_toast));
                    text.setTextColor(Color.parseColor("#522749"));
                    text.setTextSize(21);
                    text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                    toast.show();
                    startActivity(new Intent(getApplicationContext(),Positive_layout.class));
                }
                else if(gloomy.isChecked())
                {
                    userid = mAuth.getCurrentUser().getUid();
                    reference.child(userid).child(currentdate).setValue(holder);
                    Toast toast = Toast.makeText(MoodQuiz.this, "Mood Successfully recorded", Toast.LENGTH_SHORT);
                    View view_g = toast.getView();
                    TextView text = view_g.findViewById(android.R.id.message);
                    view_g.setBackground(getDrawable(R.drawable.rounded_toast));
                    text.setTextColor(Color.parseColor("#522749"));
                    text.setTextSize(21);
                    text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                    toast.show();
                    startActivity(new Intent(getApplicationContext(),Positive_layout.class));
                }
                else if(anxious.isChecked())
                {
                    userid = mAuth.getCurrentUser().getUid();
                    reference.child(userid).child(currentdate).setValue(holder);
                    Toast toast = Toast.makeText(MoodQuiz.this, "Mood Successfully recorded", Toast.LENGTH_SHORT);
                    View view_a = toast.getView();
                    TextView text = view_a.findViewById(android.R.id.message);
                    view_a.setBackground(getDrawable(R.drawable.rounded_toast));
                    text.setTextColor(Color.parseColor("#522749"));
                    text.setTextSize(21);
                    text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                    toast.show();
                    startActivity(new Intent(getApplicationContext(),Positive_layout.class));
                }
                else if(sad.isChecked())
                {
                    userid = mAuth.getCurrentUser().getUid();
                    reference.child(userid).child(currentdate).setValue(holder);
                    Toast toast = Toast.makeText(MoodQuiz.this, "Mood Successfully recorded", Toast.LENGTH_SHORT);
                    View view_s = toast.getView();
                    TextView text = view_s.findViewById(android.R.id.message);
                    view_s.setBackground(getDrawable(R.drawable.rounded_toast));
                    text.setTextColor(Color.parseColor("#522749"));
                    text.setTextSize(21);
                    text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                    toast.show();
                    startActivity(new Intent(getApplicationContext(),Positive_layout.class));
                }
                else if(calm.isChecked())
                {
                    userid = mAuth.getCurrentUser().getUid();
                    reference.child(userid).child(currentdate).setValue(holder);
                    Toast toast = Toast.makeText(MoodQuiz.this, "Mood Successfully recorded", Toast.LENGTH_SHORT);
                    View view_c = toast.getView();
                    TextView text = view_c.findViewById(android.R.id.message);
                    view_c.setBackground(getDrawable(R.drawable.rounded_toast));
                    text.setTextColor(Color.parseColor("#522749"));
                    text.setTextSize(21);
                    text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                    toast.show();
                    startActivity(new Intent(getApplicationContext(),Positive_layout.class));
                }
                else if(angry.isChecked())
                {
                    userid = mAuth.getCurrentUser().getUid();
                    reference.child(userid).child(currentdate).setValue(holder);
                    Toast toast = Toast.makeText(MoodQuiz.this, "Mood Successfully recorded", Toast.LENGTH_SHORT);
                    View viewa = toast.getView();
                    TextView text = viewa.findViewById(android.R.id.message);
                    viewa.setBackground(getDrawable(R.drawable.rounded_toast));
                    text.setTextColor(Color.parseColor("#522749"));
                    text.setTextSize(21);
                    text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                    toast.show();
                    startActivity(new Intent(getApplicationContext(),Positive_layout.class));
                }
                else if(happy.isChecked())
                {
                    userid = mAuth.getCurrentUser().getUid();
                    reference.child(userid).child(currentdate).setValue(holder);
                    Toast toast = Toast.makeText(MoodQuiz.this, "Mood Successfully recorded", Toast.LENGTH_SHORT);
                    View view_h = toast.getView();
                    TextView text = view_h.findViewById(android.R.id.message);
                    view_h.setBackground(getDrawable(R.drawable.rounded_toast));
                    text.setTextColor(Color.parseColor("#522749"));
                    text.setTextSize(21);
                    text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                    toast.show();
                    startActivity(new Intent(getApplicationContext(),Positive_layout.class));
                }
                else if(fear.isChecked())
                {
                    userid = mAuth.getCurrentUser().getUid();
                    reference.child(userid).child(currentdate).setValue(holder);
                    Toast toast = Toast.makeText(MoodQuiz.this, "Mood Successfully recorded", Toast.LENGTH_SHORT);
                    View view_f = toast.getView();
                    TextView text = view_f.findViewById(android.R.id.message);
                    view_f.setBackground(getDrawable(R.drawable.rounded_toast));
                    text.setTextColor(Color.parseColor("#522749"));
                    text.setTextSize(21);
                    text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                    toast.show();
                    startActivity(new Intent(getApplicationContext(),Positive_layout.class));
                }
                else
                {
                    Toast toast = Toast.makeText(MoodQuiz.this, "Please Select a mood", Toast.LENGTH_SHORT);
                    View view_e = toast.getView();
                    TextView text = view_e.findViewById(android.R.id.message);
                    view_e.setBackground(getDrawable(R.drawable.rounded_toast));
                    text.setTextColor(Color.parseColor("#522749"));
                    text.setTextSize(21);
                    text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                    toast.show();
                }

            }
        });


        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

    }

    /*public void showToast(View view)
    {

    }*/

    public void btn_back(View view)
    {
        Intent back_btn = new Intent(MoodQuiz.this,navigation_drawer.class);
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
        //Recreate activity
        recreate();
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