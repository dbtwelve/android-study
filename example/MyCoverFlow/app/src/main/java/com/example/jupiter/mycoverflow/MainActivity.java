package com.example.jupiter.mycoverflow;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static int spacing = -45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CoverFlow coverFlow = (CoverFlow) findViewById(R.id.coverFlow);
        ImageAdapter adapter = new ImageAdapter();
        coverFlow.setAdapter(adapter);

        coverFlow.setSpacing(spacing);
        coverFlow.setSelection(2,true);
        coverFlow.setAnimationDuration(3000);

    }

    class ImageAdapter extends BaseAdapter {

        int[] items = {R.drawable.cherries, R.drawable.dove, R.drawable.seagull, R.drawable.seagull2, R.drawable.water};

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return items[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ImageView view = new ImageView(getApplicationContext());
            view.setImageResource(items[i]);
            view.setLayoutParams(new CoverFlow.LayoutParams(500, 280));
            view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            BitmapDrawable drawable = (BitmapDrawable) view.getDrawable();
            drawable.setAntiAlias(true);

            return view;
        }
    }
}
