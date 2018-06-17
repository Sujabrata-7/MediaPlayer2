package com.example.android.mediaplayer;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.android.mediaplayer.R.raw.*;

public class MainActivity extends AppCompatActivity {

    SeekBar seek_bar;
    Button play_button, pause_button,prev_button,next_button;
    MediaPlayer player;
    TextView text_shown,tx1,tx2;
    int[] songs=new int[3];
    int position=0;
    Handler seekHandler = new Handler();
    int startTime;
    int totalTime;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getInit();
        load_song();
        player=MediaPlayer.create(getApplicationContext(),songs[position]);
        seek_bar.setMax(player.getDuration());
        seekUpdation();
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_shown.setText("Playing...");
                player.start();

            }
        });
        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_shown.setText("Paused...");
                player.pause();
            }
        });
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isPlaying()){
                    player.stop(); // method to stop the object
                }
                // condition to start first if continuously pressing next button
                if(position==2)
                {
                    position=0;  // setting to zero to first song
                }
                else{
                    position++;     // increment operator if press next
                }
                // create is public method of MediaPlayer class accepting two parameters Context and reference to next song
                player = MediaPlayer.create(getApplicationContext(), songs[position]);
                seek_bar.setMax(player.getDuration());
                seekUpdation();
                player.start();
            }
        });
        prev_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isPlaying()){
                    player.stop(); // method to stop the object
                }
                // condition to start first if continuously pressing prev button
                if(position==0){
                    position=0;  // setting to zero to first song
                }
                else{
                    position--; //decrement operator if press prev
                }
                // create is public method of MediaPlayer class accepting two parameters Context and reference to previous song
                player = MediaPlayer.create(getApplicationContext(), songs[position]);
                seek_bar.setMax(player.getDuration());
                seekUpdation();
                player.start();
            }
        });
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged=progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(progressChanged);
            }
        });
    }

    public void getInit() {
        seek_bar = findViewById(R.id.seek_bar);
        play_button = findViewById(R.id.play_button);
        pause_button = findViewById(R.id.pause_button);
        prev_button= findViewById(R.id.prev_button);
        next_button= findViewById(R.id.next_button);
        text_shown = findViewById(R.id.text_shown);
        tx1= findViewById(R.id.textView2);
        tx2= findViewById(R.id.textView3);

    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    public void seekUpdation() {
        seek_bar.setProgress(player.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
        startTime = player.getCurrentPosition();
        totalTime= player.getDuration();
        tx1.setText(String.format("%d :, %d ",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) startTime)))
        );
        tx2.setText(String.format("%d :, %d ",
                TimeUnit.MILLISECONDS.toMinutes((long) totalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) totalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) totalTime)))
        );
    }


    public void load_song(){
        songs[0]=R.raw.music_play;
        songs[1]=R.raw.musci;
        songs[2]=R.raw.music_play2;
    }

}