package com.example.tranquillo;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Progress extends AppCompatActivity {

    //Initialise variable
    DrawerLayout drawerLayout;
    LineChart lineChart;

    DatabaseReference databaseReference;
    List<Entry> chartEntries;
    Map<Float, String> xAxisLabels;
    Map<Float, String> yAxisLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
        lineChart = (LineChart) findViewById(R.id.graph);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Mood Quiz").child(uid);

        yAxisLabels = new HashMap<>();
        yAxisLabels.put(1f, "Tired");
        yAxisLabels.put(2f, "Gloomy");
        yAxisLabels.put(3f, "Anxious");
        yAxisLabels.put(4f, "Sad");
        yAxisLabels.put(5f, "Calm");
        yAxisLabels.put(6f, "Angry");
        yAxisLabels.put(7f, "Happy ");
        yAxisLabels.put(8f, "Fear");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -24 * 6);
        //adding -24 means reducing a day
        //*6 so reduced 6 days from present
        adaptGraphWithStartingDate(calendar);
    }

    Float getYAxisValueForMood(String mood) {
        for (Map.Entry<Float, String> entry : yAxisLabels.entrySet()) {
            if (entry.getValue().equals(mood))
                return entry.getKey();
        }
        return null;
    }

    void adaptGraphWithStartingDate(Calendar startDate) {
        xAxisLabels = new HashMap<>();
        chartEntries = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            String date = format.format(startDate.getTime());
            getMoodFromDB(i, date);
            startDate.add(Calendar.HOUR_OF_DAY, 24);
        }
    }

    void getMoodFromDB(int x, String date) {
        xAxisLabels.put(Float.valueOf(x), date);
        databaseReference
                .child(date)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        try {
                            moodinputholder moodinputholder = dataSnapshot.getValue(moodinputholder.class);
                            Float y = getYAxisValueForMood(moodinputholder.getMood());
                            if (y != null) {
                                chartEntries.add(new Entry(Float.valueOf(x), y));
                                plotGraph();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Progress.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    void plotGraph() {
        LineDataSet dataSet = new LineDataSet(chartEntries, "Moods");
        dataSet.setDrawCircleHole(false);
        dataSet.setCircleRadius(8f);
        dataSet.setCircleColor(getResources().getColor(R.color.custom_circle_color, getTheme()));
        dataSet.setLineWidth(4f);
        dataSet.setColor(getResources().getColor(R.color.custom_line_color, getTheme()));
        dataSet.setDrawValues(false);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
        lineChart.getLegend().setEnabled(false);
        lineChart.setNoDataText("No Data Found");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelRotationAngle(-90f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(7f);
        xAxis.setTextSize(16f);
        Typeface typeface = ResourcesCompat.getFont(Progress.this,R.font.cantata_one);
        xAxis.setTypeface(typeface);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (xAxisLabels.get(value) != null)
                    return xAxisLabels.get(value);
                else
                    return " ";
            }

            @Override
            public String getPointLabel(Entry entry) {
                return "";
            }
        });

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawGridLines(true);
        yAxis.setGranularity(1f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(8f);
        yAxis.setTextSize(16f);
        yAxis.setTypeface(typeface);
        yAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (yAxisLabels.get(value) != null)
                    return yAxisLabels.get(value);
                else
                    return "";
            }
        });

        lineChart.getAxisRight().setEnabled(false);

        lineChart.setExtraLeftOffset(16f);
        lineChart.invalidate();
        lineChart.refreshDrawableState();
        lineChart.resetViewPortOffsets();
        lineChart.fitScreen();
        lineChart.setExtraOffsets(24f,0f,0f,120f);
    }

    public void btn_back(View view) {
        Intent back_btn = new Intent(Progress.this, navigation_drawer.class);
        startActivity(back_btn);
        finish();
    }

    public void ClickMenu(View view) {
        //Open drawer
        navigation_drawer.openDrawer(drawerLayout);
    }

    public void ClickToExit(View view) {
        //Close drawer
        navigation_drawer.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view) {
        //Redirect activity to home
        navigation_drawer.redirectActivity(this, navigation_drawer.class);
    }

    public void ClickAboutUs(View view) {
        //Redirect activity to About Us page
        navigation_drawer.redirectActivity(this, AboutUs.class);
    }

    public void ClickMoodQuiz(View view) {
        //Redirect activity to MoodQuiz
        navigation_drawer.redirectActivity(this, MoodQuiz.class);
    }

    public void ClickProgress(View view) {
        //Recreate activity
        recreate();
    }

    //Quotes
    public void ClickQuotes(View view) {
        //Redirect activity
        navigation_drawer.redirectActivity(this, Quotes.class);
    }

    //Anagram Game
    public void ClickGame(View view) {
        //Redirect activity
        navigation_drawer.redirectActivity(this, AnagramGame.class);
    }

    //Calm Music
    public void ClickMusic(View view) {
        //Redirect activity
        navigation_drawer.redirectActivity(this, CalmMusic.class);
    }

    //Quick Decision
    public void ClickDecision(View view) {
        //Redirect activity
        navigation_drawer.redirectActivity(this, QuickDecision.class);
    }

    //Notification
    public void ClickNotification(View view) {
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
