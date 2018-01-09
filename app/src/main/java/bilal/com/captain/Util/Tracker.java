package bilal.com.captain.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by Android on 3/25/2017.
 */

public class Tracker implements LocationListener {

    // The minimum distance to change updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 20; // 10 meters 20

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 2; // 2 minutes

    // Declaring a Location Manager
    protected LocationManager locationManager;
    Location location;
    // Store LocationManager.GPS_PROVIDER or LocationManager.NETWORK_PROVIDER information
    private String provider_info;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;
    public double userLatitude, userLongitude;

    Context context;

    public Tracker(Context context){
        this.context = context;
        getLocation();
    }

    public void getLocation(){

        // Getting LocationManager object
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        } else {
            this.canGetLocation = true;
            // First get location from Network Provider
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("Network", "Network");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        onLocationChanged(location);
                        userLatitude = location.getLatitude();
                        userLongitude = location.getLongitude();
//                            Toast.makeText(mContext, "Longitude: "+ location.getLongitude() +"\n"+
//                                    "Latitude: "+ location.getLatitude(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
                if (location == null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            onLocationChanged(location);
                            userLatitude = location.getLatitude();
                            userLongitude = location.getLongitude();
//                                Toast.makeText(mContext, "Longitude: "+ location.getLongitude() +"\n"+
//                                        "Latitude: "+ location.getLatitude(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }

        if (location == null){
            // Creating an empty criteria object
            Criteria criteria = new Criteria();

            // Getting the name of the provider that meets the criteria
            provider_info = locationManager.getBestProvider(criteria, false);
            if (provider_info != null && !provider_info.equals("")) {

                // Get the location from the given provider
                location = locationManager.getLastKnownLocation(provider_info);

                locationManager.requestLocationUpdates(
                        provider_info,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this);

                if (location != null){
                    onLocationChanged(location);
                    userLatitude = location.getLatitude();
                    userLongitude = location.getLongitude();
//                        Toast.makeText(mContext, "Longitude: "+ location.getLongitude() +"\n"+
//                                "Latitude: "+ location.getLatitude(), Toast.LENGTH_SHORT).show();
                }
                else{
//                        Toast.makeText(mContext, "Location can't be retrieved", Toast.LENGTH_SHORT).show();
                }

            } else {
//                    Toast.makeText(mContext, "No Provider Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public double getLatitude(){
        return userLatitude;
    }

    public double getLongitude(){
        return userLongitude;
    }
    @Override
    public void onLocationChanged(Location location) {
        this.userLongitude = location.getLongitude();
        this.userLatitude = location.getLatitude();
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

    public boolean checkGPSStatus(){
        boolean status;
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        status = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return status;
    }

    public void showSettingsAlert() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);

        //Setting Dialog Title
        alertDialog.setTitle("Warning...");

        alertDialog.setCancelable(false);

        //Setting Dialog Message
        alertDialog.setMessage("GPS is disabled in your device. Would you like to enable it?");

        //On Pressing Setting button
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        //On pressing cancel button
//        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which)
//            {
//                dialog.cancel();
//            }
//        });

        alertDialog.show();
    }
}
