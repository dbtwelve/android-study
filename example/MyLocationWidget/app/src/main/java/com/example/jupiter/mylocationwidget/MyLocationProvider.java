package com.example.jupiter.mylocationwidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;

public class MyLocationProvider extends AppWidgetProvider {
    public static double ycoord;
    public static double xcoord;
    public static final String TAG = "GPSLocationService";
    static String packageName ="";

    static Context tempContext;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            String url = "geo:" + ycoord + ", " + xcoord;
            String query = ycoord + ", " + xcoord + "(MyLocation)";
            String encodedQuery = Uri.encode(query);
            String urlStr = url + "?q=" + encodedQuery + "&z=15";
            Uri uri = Uri.parse(urlStr);

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);


            tempContext = context;
            packageName = context.getPackageName();

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(packageName, R.layout.mylocation);
            views.setOnClickPendingIntent(R.id.txtInfo, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        context.startService(new Intent(context, GPSLocationService.class));
    }

    private static void updateCoordinates(double latitude, double longitude) {
        Geocoder coder = new Geocoder(tempContext);
        List<Address> addresses = null;
        String info = "";

        Context context;

        Log.d(TAG, "updateCoordinates() called.");

        try {
            addresses = coder.getFromLocation(latitude, longitude, 2);

            if (null != addresses && addresses.size() > 0) {
                int addressCount = addresses.get(0).getMaxAddressLineIndex();

                if (-1 != addressCount) {
                    for (int index = 0; index <= addressCount; ++index) {
                        info += addresses.get(0).getAddressLine(index);

                        if (index < addressCount)
                            info += ", ";
                    }
                } else {
                    info += addresses.get(0).getFeatureName() + ", "
                            + addresses.get(0).getSubAdminArea() + ", "
                            + addresses.get(0).getAdminArea();
                }
            }

            Log.d(TAG, "Address : " + addresses.get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        coder = null;
        addresses = null;

        if (info.length() <= 0) {
            info = "[내 위치] " + latitude + ", " + longitude
                    + "\n터치하면 지도로 볼 수 있습니다.";
        } else {
            info += ("\n" + "[내 위치] " + latitude + ", " + longitude + ")");
            info += "\n터치하면 지도로 볼 수 있습니다.";
        }

        RemoteViews views = new RemoteViews(packageName, R.layout.mylocation);

        views.setTextViewText(R.id.txtInfo, info);

        ComponentName thisWidget = new ComponentName(tempContext, MyLocationProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(tempContext);
        manager.updateAppWidget(thisWidget, views);

        xcoord = longitude;
        ycoord = latitude;
        Log.d(TAG, "coordinates : " + latitude + ", " + longitude);

    }

    public static class GPSLocationService extends Service {
        LocationManager manager;
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                ycoord = location.getLatitude();
                xcoord = location.getLongitude();
                updateCoordinates(ycoord,xcoord);
                stopSelf();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        @Override
        public void onCreate() {
            super.onCreate();

            manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startListening();

            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public void onDestroy() {
            stopListening();

            super.onDestroy();
        }

        public void startListening() {

            final Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);

            final String bestProvider = manager.getBestProvider(criteria, true);
            if (bestProvider != null && bestProvider.length() > 0) {
                manager.requestLocationUpdates(bestProvider,500,10, listener);
            } else {
                final List<String> providers = manager.getProviders(true);

                for (final String provider : providers) {
                    manager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            10000,
                            0,
                            listener

                    );
                }
            }


        }

        public void stopListening() {

        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }
}
