package com.example.jupiter.mysocket;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientThread thread = new ClientThread();
                thread.start();
            }
        });
    }

    class ClientThread extends Thread {
        public void run() {
            String host = "localhost";
            int port = 5001;
            try {
                Socket socket = new Socket(host, port);

                ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
                outStream.writeObject("Hello!");
                outStream.flush();
                Log.d("ClientThread", "Send Server.");

                ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                final Object input = instream.readObject();
                Log.d("ClientThread", "Receive Data : " + input);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Receive Data : " + input);
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
