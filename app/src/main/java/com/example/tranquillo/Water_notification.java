package com.example.tranquillo;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Water_notification extends AppCompatActivity {

    ProgressBar loadingBar;
    LinearLayout scheduleLayout;
    AppCompatButton mainButton;
    TextView mondayTv, tuesdayTv, wednesdayTv, thursdayTv, fridayTv, saturdayTv, sundayTv;

    DatabaseReference dbRef;
    WaterNotificationData notificationData;
    private java.util.Locale Locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_notification);
        initLayout();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Drink Water").child(uid);

        loadingBar.setVisibility(View.VISIBLE);
        scheduleLayout.setVisibility(View.GONE);
        dbRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                notificationData = new WaterNotificationData(Water_notification.this, dataSnapshot);
                updateUI();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.setVisibility(View.GONE);
                Toast.makeText(Water_notification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    void initLayout() {
        loadingBar = (ProgressBar) findViewById(R.id.loading);
        scheduleLayout = (LinearLayout) findViewById(R.id.schedule_layout);
        mainButton = (AppCompatButton) findViewById(R.id.main_button);
        mondayTv = (TextView) findViewById(R.id.monday_timing);
        tuesdayTv = (TextView) findViewById(R.id.tuesday_timing);
        wednesdayTv = (TextView) findViewById(R.id.wednesday_timing);
        thursdayTv = (TextView) findViewById(R.id.thursday_timing);
        fridayTv = (TextView) findViewById(R.id.friday_timing);
        saturdayTv = (TextView) findViewById(R.id.saturday_timing);
        sundayTv = (TextView) findViewById(R.id.sunday_timing);
    }

    void updateUI() {
        updateTimeInUI(mondayTv, notificationData.getMondayStart(), notificationData.getMondayEnd());
        updateTimeInUI(tuesdayTv, notificationData.getTuesdayStart(), notificationData.getTuesdayEnd());
        updateTimeInUI(wednesdayTv, notificationData.getWednesdayStart(), notificationData.getWednesdayEnd());
        updateTimeInUI(thursdayTv, notificationData.getThursdayStart(), notificationData.getThursdayEnd());
        updateTimeInUI(fridayTv, notificationData.getFridayStart(), notificationData.getFridayEnd());
        updateTimeInUI(saturdayTv, notificationData.getSaturdayStart(), notificationData.getSaturdayEnd());
        updateTimeInUI(sundayTv, notificationData.getSundayStart(), notificationData.getSundayEnd());

        setupMainButton();

        loadingBar.setVisibility(View.GONE);
        scheduleLayout.setVisibility(View.VISIBLE);
    }

    void updateTimeInUI(TextView textView, int startTime, int endTime) {
        final SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm aaa", Locale.getDefault());
        String msg = "";
        if (startTime == -1)
        {
            msg = "Off";
        }
        else
            {
            int minute = startTime % 100;
            int hour = startTime / 100;
            String hourString, minuteString, endString = "AM";

            if (minute < 10) minuteString = "0" + minute;
            else minuteString = String.valueOf(minute);

            if (hour > 12) {
                hour -= 12;
                endString = "PM";
            }
            if (hour < 10) hourString = "0" + hour;
            else hourString = String.valueOf(hour);

            msg = hourString + ":" + minuteString + endString;

            minute = endTime % 100;
            hour = endTime / 100;
            endString = "AM";

            if (minute < 10) minuteString = "0" + minute;
            else minuteString = String.valueOf(minute);

            if (hour > 12) {
                hour -= 12;
                endString = "PM";
            }
            if (hour < 10) hourString = "0" + hour;
            else hourString = String.valueOf(hour);

            msg = msg + " - " + hourString + ":" + minuteString + endString;
        }
        textView.setText(msg);
    }

    void setupMainButton() {
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> options = new ArrayList<>();
                options.add("Schedule Notifications");
                options.add("Disable Notifications");

                new AlertDialog.Builder(Water_notification.this)
                        .setTitle("Manage Notifications")
                        .setItems(options.toArray(new CharSequence[options.size()]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) showEnableDialog();
                                if (i == 1) showDisableDialog();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        });
    }

    void showEnableDialog() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Everyday");
        options.add("Monday");
        options.add("Tuesday");
        options.add("Wednesday");
        options.add("Thursday");
        options.add("Friday");
        options.add("Saturday");
        options.add("Sunday");

        new AlertDialog.Builder(Water_notification.this)
                .setTitle("Select Day")
                .setItems(options.toArray(new CharSequence[options.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showStartTimeDialog(i);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    void showStartTimeDialog(int option) {
        TimePickerDialog.OnTimeSetListener startTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int startHour, int startMinute) {
                int startTime = (startHour * 100) + startMinute;
                showEndTimeDialog(option, startTime);
            }
        };
        TimePickerDialog startTimeDialog = new TimePickerDialog(Water_notification.this,
                startTimeListener,
                0,
                0,
                DateFormat.is24HourFormat(getBaseContext()));
        startTimeDialog.setTitle("Select Start Time");
        startTimeDialog.setCancelable(false);
        startTimeDialog.show();
    }

    void showEndTimeDialog(int option, int startTime) {
        TimePickerDialog.OnTimeSetListener endTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int endHour, int endMinute) {
                int endTime = endHour * 100 + endMinute;
                setTime(option, startTime, endTime);
            }
        };
        TimePickerDialog endTimeDialog = new TimePickerDialog(Water_notification.this,
                endTimeListener,
                0,
                0,
                DateFormat.is24HourFormat(getBaseContext()));
        endTimeDialog.setTitle("Select End Time");
        endTimeDialog.setCancelable(false);
        endTimeDialog.show();
    }

    void setTime(int option, int startTime, int endTime) {
        if(startTime>endTime){
            new AlertDialog.Builder(this)
                    .setTitle("Invalid")
                    .setMessage("Start Time cannot be greater than End Time")
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create()
                    .show();
            return;
        }
        boolean setAll = false;
        switch (option) {
            case 0: {
                setAll = true;
            }
            case 1: {
                notificationData.setMondayStart(startTime);
                notificationData.setMondayEnd(endTime);
                if (!setAll) break;
            }
            case 2: {
                notificationData.setTuesdayStart(startTime);
                notificationData.setTuesdayEnd(endTime);
                if (!setAll) break;
            }
            case 3: {
                notificationData.setWednesdayStart(startTime);
                notificationData.setWednesdayEnd(endTime);
                if (!setAll) break;
            }
            case 4: {
                notificationData.setThursdayStart(startTime);
                notificationData.setThursdayEnd(endTime);
                if (!setAll) break;
            }
            case 5: {
                notificationData.setFridayStart(startTime);
                notificationData.setFridayEnd(endTime);
                if (!setAll) break;
            }
            case 6: {
                notificationData.setSaturdayStart(startTime);
                notificationData.setSaturdayEnd(endTime);
                if (!setAll) break;
            }
            case 7: {
                notificationData.setSundayStart(startTime);
                notificationData.setSundayEnd(endTime);
                if (!setAll) break;
            }
        }
        pushNewData();
    }

    void showDisableDialog() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Everyday");
        options.add("Monday");
        options.add("Tuesday");
        options.add("Wednesday");
        options.add("Thursday");
        options.add("Friday");
        options.add("Saturday");
        options.add("Sunday");

        new AlertDialog.Builder(Water_notification.this)
                .setTitle("Select Day")
                .setItems(options.toArray(new CharSequence[options.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTime(i);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    void deleteTime(int option) {
        boolean setAll = false;
        switch (option) {
            case 0: {
                setAll = true;
            }
            case 1: {
                notificationData.setMondayStart(-1);
                notificationData.setMondayEnd(-1);
                if (!setAll) break;
            }
            case 2: {
                notificationData.setTuesdayStart(-1);
                notificationData.setTuesdayEnd(-1);
                if (!setAll) break;
            }
            case 3: {
                notificationData.setWednesdayStart(-1);
                notificationData.setWednesdayEnd(-1);
                if (!setAll) break;
            }
            case 4: {
                notificationData.setThursdayStart(-1);
                notificationData.setThursdayEnd(-1);
                if (!setAll) break;
            }
            case 5: {
                notificationData.setFridayStart(-1);
                notificationData.setFridayEnd(-1);
                if (!setAll) break;
            }
            case 6: {
                notificationData.setSaturdayStart(-1);
                notificationData.setSaturdayEnd(-1);
                if (!setAll) break;
            }
            case 7: {
                notificationData.setSundayStart(-1);
                notificationData.setSundayEnd(-1);
                if (!setAll) break;
            }
        }
        pushNewData();
    }

    void pushNewData() {
        dbRef.setValue(notificationData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        notificationData.updateLocalData(Water_notification.this);
                        Toast toast = Toast.makeText(Water_notification.this, "Success", Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        TextView text = view.findViewById(android.R.id.message);
                        view.setBackground(getDrawable(R.drawable.rounded_toast));
                        text.setTextColor(Color.parseColor("#522749"));
                        text.setTextSize(21);
                        text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                        toast.show();
                        updateUI();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               Toast toast = Toast.makeText(Water_notification.this, e.getMessage(), Toast.LENGTH_SHORT);
                View view = toast.getView();
                TextView text = view.findViewById(android.R.id.message);
                view.setBackground(getDrawable(R.drawable.rounded_toast));
                text.setTextColor(Color.parseColor("#522749"));
                text.setTextSize(21);
                text.setTypeface(Typeface.create("serif",Typeface.BOLD));
                toast.show();
                e.printStackTrace();
                startActivity(new Intent(Water_notification.this, Water_notification.class));
                finish();
            }
        });
    }

    public void onclick_back(View view) {
        Intent btn_back = new Intent(Water_notification.this, Notifications.class);
        startActivity(btn_back);
        finish();
    }
}