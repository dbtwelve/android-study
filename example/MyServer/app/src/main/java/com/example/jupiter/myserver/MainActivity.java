package com.example.jupiter.myserver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ServerThread thread = new ServerThread();
                //thread.start();

                Intent intent = new Intent(getApplicationContext(), ServerService.class);
                startService(intent);
            }
        });
    }
    /*
    class ServerThread extends Thread {
        public void run() {
            int port = 5001;

            try {
                ServerSocket server = new ServerSocket(port);
                Log.d("ServerThread", "Server Start.");

                while (true) {
                    Socket socket = server.accept();

                    ObjectInputStream instream = new ObjectInputStream(socket.getInputStream());
                    Object input = instream.readObject();
                    Log.d("ServerThread", "Input : " + input);

                    ObjectOutputStream outstream = new ObjectOutputStream(socket.getOutputStream());
                    outstream.writeObject(input + "from server.");
                    outstream.flush();
                    Log.d("ServerThread" , "Output Send.");

                    socket.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    */

}
