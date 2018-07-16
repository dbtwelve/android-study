package com.example.jupiter.mysensor;

import android.content.Context;
import android.hardware.Sensor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SensorListAdapter extends ArrayAdapter<Sensor> {
    Context context;
    List<Sensor> items;

    public SensorListAdapter(@NonNull Context context, int resource, @NonNull List<Sensor> objects) {
        super(context, resource, objects);

        this.context = context;
        this.items = objects;
    }

    public int getCount() {
        return ((items != null) ? items.size():0);
    }

    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sensor_item, null);
        } else {
            view = convertView;
        }

        Sensor sensor = items.get(position);
        TextView textView = (TextView) view.findViewById(R.id.textView1);
        TextView textView2 = (TextView) view.findViewById(R.id.textView2);
        TextView textView3 = (TextView) view.findViewById(R.id.textView3);

        String sensorName = sensor.getName();
        String sensorVendor = sensor.getVendor();
        int sensorVersion = sensor.getVersion();

        textView.setText("Sensor Name : " + sensorName);
        textView2.setText("Sensor Vendor : " +  sensorVendor);
        textView3.setText("Sensor Version : " + sensorVersion);

        return view;
    }
}
