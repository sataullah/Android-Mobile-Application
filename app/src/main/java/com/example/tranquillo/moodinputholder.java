package com.example.tranquillo;

public class moodinputholder {
   String  CurrentDate;
   String Mood;

   public moodinputholder(){
       //empty public constructor required for dataSnapshot.getValue(moodinputholder.class);
   }

    public moodinputholder(String currentDate, String mood) {
        CurrentDate = currentDate;
        Mood = mood;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public String getMood() {
        return Mood;
    }

    public void setMood(String mood) {
        Mood = mood;
    }
}
