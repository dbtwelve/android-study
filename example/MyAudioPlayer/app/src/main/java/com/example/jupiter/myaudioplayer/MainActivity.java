package com.example.jupiter.myaudioplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String url = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

    MediaPlayer player;

    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio();

            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseAudio();

            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeAudio();

            }
        });

        Button button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAudio();

            }
        });


    }

    public void playAudio() {
        try {
            closePlayer();

            player = new MediaPlayer();
            player.setDataSource(url);
            player.prepare();
            player.start();

            Toast.makeText(this, "Play Start.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseAudio() {
        if (player != null) {
            position = player.getCurrentPosition();
            player.pause();
            Toast.makeText(this, "Paused.", Toast.LENGTH_LONG).show();
        }
    }

    public void resumeAudio() {
        if (player != null && !player.isPlaying()) {
            player.seekTo(position);
            player.start();
            Toast.makeText(this, "Resumed.", Toast.LENGTH_LONG).show();

        }
    }

    public void stopAudio() {
        if (player != null && player.isPlaying()) {
            player.stop();
            Toast.makeText(this, "Stopped.", Toast.LENGTH_LONG).show();
        }
    }

    public void closePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }


}
