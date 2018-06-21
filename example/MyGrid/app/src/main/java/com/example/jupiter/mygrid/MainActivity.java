package com.example.jupiter.mygrid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SingerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.gridView);



        adapter = new SingerAdapter();
        adapter.addItem(new Singeritem("Ali", "010-1000-1000", R.drawable.blue));
        adapter.addItem(new Singeritem("Tim", "010-1000-2000", R.drawable.red));
        adapter.addItem(new Singeritem("GirlsDay", "010-1000-3000", R.drawable.soccerball));
        adapter.addItem(new Singeritem("NoOne", "010-1000-4000", R.drawable.ic_launcher_background));
        adapter.addItem(new Singeritem("EveryOne", "010-1000-5000", R.drawable.ic_launcher_foreground));

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Singeritem item = (Singeritem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "selected : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });


    }

    class SingerAdapter extends BaseAdapter {
        ArrayList<Singeritem> items = new ArrayList<Singeritem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Singeritem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup viewGroup) {
            SingeritemView view1 = null;
            if (convertview == null) {
                view1 = new SingeritemView(getApplicationContext());
            } else {
                view1 = (SingeritemView) convertview;
            }

            Singeritem item = items.get(position);
            view1.setName(item.getName());
            view1.setMobile(item.getMobile());
            view1.setImage(item.getResId());

            return view1;
        }
    }

}
