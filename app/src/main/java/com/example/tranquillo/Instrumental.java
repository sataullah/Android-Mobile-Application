package com.example.tranquillo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class Instrumental extends AppCompatActivity {
    //Initialise Variables
    TextView playerPosition, playerDuration;
    SeekBar seekbar;
    ImageView btRew, btPlay, btPause, btFf;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrumental);

        //Assign Variables
        playerPosition = (TextView)findViewById(R.id.player_position);
        playerDuration = (TextView)findViewById(R.id.player_duration);
        seekbar = (SeekBar)findViewById(R.id.seek_bar);
        btRew = (ImageView)findViewById(R.id.bt_rewind);
        btPlay = (ImageView)findViewById(R.id.bt_play);
        btPause = (ImageView)findViewById(R.id.bt_pause);
        btFf = (ImageView)findViewById(R.id.bt_fforward);

        //Initialise MediaPlayer
        mediaPlayer = MediaPlayer.create(this,R.raw.simplicity);

        //Initialise Runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                //Setting progress on seek bar
                seekbar.setProgress(mediaPlayer.getCurrentPosition());
                //Handler post delay for 0.5 seconds
                handler.postDelayed(this,500);
            }
        };

        //Get Duration of media player
        int duration = mediaPlayer.getDuration();
        //Convert millisecond to minute and second
        String sDuration = convertFormat(duration);
        //Set Duration on text view
        playerDuration.setText(sDuration);

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hide Play button
                btPlay.setVisibility(View.GONE);
                //Show Pause Button
                btPause.setVisibility(View.VISIBLE);
                //Start media player
                mediaPlayer.start();
                //Set max on seek bar
                seekbar.setMax(mediaPlayer.getDuration());
                //Start Handler
                handler.postDelayed(runnable,0);
            }
        });

        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hide Pause button
                btPause.setVisibility(View.GONE);
                //Show Play Button
                btPlay.setVisibility(View.VISIBLE);
                //Pause media player
                mediaPlayer.pause();
                //Stop Handler
                handler.removeCallbacks(runnable);
            }
        });

        btFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get current position of media player
                int currentPosition = mediaPlayer.getCurrentPosition();
                //Get duration of media player
                int duration = mediaPlayer.getDuration();
                //Check Condition
                if(mediaPlayer.isPlaying() && duration!= currentPosition)
                {
                    //When media player is playing and the duration is not equal to current position
                    //Fast forward for 5 seconds
                    currentPosition = currentPosition + 5000;
                    //Set the current position on textview
                    playerPosition.setText(convertFormat(currentPosition));
                    //Set Progress on Seek Bar
                    mediaPlayer.seekTo(currentPosition);
                }
            }
        });

        btRew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get current position of media player
                int currentPosition = mediaPlayer.getCurrentPosition();
                //Check Condition
                if(mediaPlayer.isPlaying() && currentPosition > 5000)
                {
                    //When media player is playing and the current position is greater than 5 seconds
                    //Rewind for 5 seconds
                    currentPosition = currentPosition - 5000;
                    //Get current position on text view
                    playerPosition.setText(convertFormat(currentPosition));
                    //Set progress on seek bar
                    mediaPlayer.seekTo(currentPosition);
                }
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Check Condition
                if(fromUser)
                {
                    //When dragging the seek bar thumb
                    //Set progress on seek bar
                    mediaPlayer.seekTo(progress);
                }
                //Set current position on textview
                playerPosition.setText(convertFormat(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Hide Pause Button
                btPause.setVisibility(View.GONE);
                //Show Play Button
                btPlay.setVisibility(View.VISIBLE);
                //Set media player to initial position
                mediaPlayer.seekTo(0);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration) {
        return String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(duration),TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    public void onclick_exit_btn(View view)
    {
        Intent exit_btn = new Intent(Instrumental.this,CalmMusic.class);
        startActivity(exit_btn);
        finish();
    }
}