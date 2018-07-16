package com.example.jupiter.mypush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView logOutput;

    EditText dataInput;
    TextView dataOutput;
    String regID;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logOutput = (TextView) findViewById(R.id.logOutput);
        dataInput = (EditText) findViewById(R.id.dataInput);
        dataOutput = (TextView) findViewById(R.id.dataOutput);

        getRegistrationID();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = dataInput.getText().toString().trim();
                send(data);
            }
        });

        queue = Volley.newRequestQueue(getApplicationContext());
    }

    public void getRegistrationID() {
        regID = FirebaseInstanceId.getInstance().getToken();
        println("Register ID -> " + regID);
    }

    public void send(String input) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("priority" , "high");

            JSONObject dataObj = new JSONObject();
            dataObj.put("contents", input);
            requestData.put("data", dataObj);

            JSONArray idArray = new JSONArray();
            idArray.put(0, regID);
            requestData.put("registration_ids", idArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sendData(requestData, new SendResponseListener() {
            @Override
            public void onRequestStarted() {
                println("onRequestStarted() called.");
            }

            @Override
            public void onRequestCompleted() {
                println("onRequestCompleted() called.");

            }

            @Override
            public void onRequestError(VolleyError error) {
                println("onRequestError() called.");

            }
        });
    }

    public interface SendResponseListener {
        public void onRequestStarted();
        public void onRequestCompleted();
        public void onRequestError(VolleyError error);
    }

    public void sendData(JSONObject requestData, final SendResponseListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://fcm.googleapis.com/fcm/send",
                requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onRequestCompleted();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onRequestError(error);
                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/json";//super.getBodyContentType();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //eturn super.getHeaders();
                Map<String,String> headers = new HashMap<String,String>();
                headers.put("Authorization", "key=AAAAQBtqlr4:APA91bHj95dkWupRaDZVSFbiYs_jkrYPc1dejkCVqGEhiLDzFrtpAYz6E0EaKbHj-x26lAD1X8qjEdrCxskS9SecCYAALt0geoGbYCWFB7eqyuLz2LrpxOdB-un156EyrQ6HhsoX_y-T6LM6PBsZDmRGgK5v6t9wQw");

                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //return super.getParams();
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }
        };

        request.setShouldCache(false);
        listener.onRequestStarted();
        queue.add(request);

        Intent passedIntent = getIntent();
        processIntent(passedIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        processIntent(intent);
    }

    public void processIntent(Intent intent) {
        if (intent != null) {
            String from = intent.getStringExtra("from");
            String contents = intent.getStringExtra("contents");

            println("DATA : " + from  + ", " + contents);
            dataOutput.setText("DATA : " + contents);
        }
    }

    public void println(String data) {
        logOutput.append(data + "\n");
    }
}
