package com.example.jupiter.myanimation;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Drawable> imageList = new ArrayList<Drawable>();
    ImageView imageView;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);

        Resources res = getResources();
        imageList.add(res.getDrawable(R.drawable.emo_awkward));
        imageList.add(res.getDrawable(R.drawable.emo_face_food_savoring));
        imageList.add(res.getDrawable(R.drawable.emo_shut_up));
        imageList.add(res.getDrawable(R.drawable.emo_smiley));
        imageList.add(res.getDrawable(R.drawable.emo_wo));

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimThread thread = new AnimThread();
                thread.start();
            }
        });

    }

    class  AnimThread extends Thread {
        public void run() {
            int index = 0;
            for (int i = 0; i < 100; i++ ) {
                index = i % 5;
                final Drawable drawable = imageList.get(index);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageDrawable(drawable);
                    }
                });
                try {
                    Thread.sleep(500);
                } catch (Exception e) {}

            }
        }
    }
}
