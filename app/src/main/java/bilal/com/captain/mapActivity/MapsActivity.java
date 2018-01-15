package bilal.com.captain.mapActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.google.android.gms.location.LocationListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bilal.com.captain.MainActivity;
import bilal.com.captain.R;
import bilal.com.captain.Util.CustomToast;
import bilal.com.captain.Util.InternetConnection;
import bilal.com.captain.Util.Tracker;
import bilal.com.captain.models.IncomeModel;

//<<<<<<< HEAD
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
////AIzaSyBz_uM4Zhlrp_tBIUECf5Wi19YiGwYMZ1o
//=======
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    com.ebanx.swipebtn.SwipeButton swipeButton;

    LocationManager locationManager;
    String provider;

    GoogleApiClient mGoogleApiClient;
    //>>>>>>> cb52230c19749ab9d9dfd7aaf01a26e540d4a17c
    private GoogleMap mMap;
    private ImageButton mUpdateLocation;

    AlertDialog.Builder dialogLocationBuilder;
    AlertDialog dialogLocation;
    Tracker tracker;
    Bundle bundle = new Bundle();
    double latitude, longitude;
    double userLatitude, userLongitude;
    String popId;
    Marker popMarker;

    ProgressDialog progressDialog;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        tracker = new Tracker(MapsActivity.this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        swipeButton = (com.ebanx.swipebtn.SwipeButton) findViewById(R.id.swipeend);

        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                openAlert();
            }
        });

//        mUpdateLocation = (ImageButton) findViewById(R.id.button_update);
////        mUpdateLocation.setVisibility(View.GONE);
//        mUpdateLocation.setOnClickListener(this);
//
//        progressDialog = new ProgressDialog(this);
//
//        progressDialog.setMessage("getting routes please wait");
//
//        progressDialog.setCancelable(false);
//        tracker = new Tracker(MapsActivity.this);
//
//        bundle = getIntent().getExtras();
//        if (bundle != null) {
//            popId = bundle.getString("popId");
//        }
//
//        getPopLocation();
//        dialogBuilder();
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);


//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        provider = locationManager.getBestProvider(new Criteria(), false);

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(provider, 400, 1, this);

    }

    private void openAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MapsActivity.this);
        final View view = getLayoutInflater().inflate(R.layout.end_ride_layout, null);

        final RadioButton cash = (RadioButton) view.findViewById(R.id.cash);
        final RadioButton wallet = (RadioButton) view.findViewById(R.id.wallet);
        final RadioButton credit = (RadioButton) view.findViewById(R.id.credit);

        TextView submit = (TextView) view.findViewById(R.id.submit2);

        final EditText enteramount = (EditText) view.findViewById(R.id.enter_amount);


        alert.setView(view);
        final AlertDialog dialog = alert.create();
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();

        cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    enteramount.setVisibility(view.VISIBLE);
                }
            }
        });

        wallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    enteramount.setVisibility(view.VISIBLE);
                }
            }
        });

        credit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    enteramount.setVisibility(view.GONE);
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetConnection.internetConnectionAvailable(2000)) {
                    if (credit.isChecked()) {

                    } else if (cash.isChecked()) {

                        try {
                            String currtime = (DateFormat.format("dd-MM-yyyy", new java.util.Date()).toString());

                            int cash = Integer.valueOf(enteramount.getText().toString().trim());

                            String key = FirebaseDatabase.
                                        getInstance().
                                        getReference().
                                        child("Cash").
                                        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                        push().getKey();

                            IncomeModel incomeModel = new IncomeModel(cash,key,currtime,String.valueOf(tracker.getLatitude()),String.valueOf(tracker.getLongitude()));

                            FirebaseDatabase.
                                    getInstance().
                                    getReference().
                                    child("Cash").
                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                    child(incomeModel.getKey()).setValue(incomeModel);

                            dialog.dismiss();

                            finish();

                            CustomToast.showToast(MapsActivity.this,"Submitted", MDToast.TYPE_SUCCESS);

//                            startActivity(new Intent(MapsActivity.this, MainActivity.class));

                        }catch (Exception e){

                            Log.d("error", "onClick: "+e);

                        }
                    }else if(wallet.isChecked()){

                        try {
                            String currtime = (DateFormat.format("dd-MM-yyyy", new java.util.Date()).toString());

                            int wallet = Integer.valueOf(enteramount.getText().toString().trim());

                            String key = FirebaseDatabase.
                                    getInstance().
                                    getReference().
                                    child("Wallet").
                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                    push().getKey();

                            IncomeModel incomeModel = new IncomeModel(wallet,key,currtime,String.valueOf(tracker.getLatitude()),String.valueOf(tracker.getLongitude()));

                            FirebaseDatabase.
                                    getInstance().
                                    getReference().
                                    child("Wallet").
                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                    child(incomeModel.getKey()).setValue(incomeModel);

                            dialog.dismiss();

                            finish();

                            CustomToast.showToast(MapsActivity.this,"Submitted", MDToast.TYPE_SUCCESS);

//                            startActivity(new Intent(MapsActivity.this, MainActivity.class));

                        }catch (Exception e){

                            Log.d("error", "onClick: "+e);

                        }

                    }
                    else {

                        CustomToast.showToast(MapsActivity.this,"Please Check Any One Of The Above",MDToast.TYPE_ERROR);

                    }
                }else {

                    CustomToast.showToast(MapsActivity.this,"Please Check Internet Connection",MDToast.TYPE_ERROR);

                }
            }


        });


    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_update:
                dialogLocation = dialogLocationBuilder.create();
                dialogLocation.show();
                break;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    // Add a marker in Sydney and move the camera

//        mMap.animateCamera(cameraUpdate);

//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
//                builder.build(), 0, 250, 0);


//        mMap.moveCamera(CameraUpdateFactory.newLatLng(popPosition));

//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(popPosition,
//                15));

//        new RoutesAsync().execute(url);


    private void dialogBuilder() {
        dialogLocationBuilder = new AlertDialog.Builder(this);
        dialogLocationBuilder.setTitle("Warning");
        dialogLocationBuilder.setMessage("Do you want to update POP Location?");
        dialogLocationBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (userLatitude != 0.0 && userLongitude != 0.0) {
                    ContentValues cv = new ContentValues();
                    cv.put("latitude", String.valueOf(userLatitude));
                    cv.put("longitude", String.valueOf(userLongitude));

                    LatLng popUpdatedPosition = new LatLng(userLatitude, userLongitude);
                    popMarker.setPosition(popUpdatedPosition);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(popUpdatedPosition));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(popUpdatedPosition,
                            15));
                    Toast.makeText(MapsActivity.this, "Update Master Location", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapsActivity.this, "Error Get Location", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialogLocationBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogLocation.hide();
            }
        });
    }

    private void getPopLocation() {

    }

    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }


    public String getUrl(LatLng origin, LatLng dest) {

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;


    }

    // Todo download url

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onLocationChanged(Location location) {

        Location mLastLocation;
        Marker mCurrLocationMarker = null;

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();

            //    mMarker.setPosition(new LatLon(latLng));
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_point));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
//                    15));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        }


    }

//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //Todo getting map routs


    private class RoutesAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }
//TODo parser task

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.show();
        }

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            if (result != null) {
                // Traversing through all the routes
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<>();
                    lineOptions = new PolylineOptions();

                    // Fetching i-th route
                    List<HashMap<String, String>> path = result.get(i);

                    // Fetching all the points in i-th route
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    // Adding all the points in the route to LineOptions
                    lineOptions.addAll(points);
                    lineOptions.width(10);
                    lineOptions.color(Color.BLUE);

                    Log.d("onPostExecute", "onPostExecute lineoptions decoded");

                }

                // Drawing polyline in the Google Map for the i-th route
                if (lineOptions != null) {
                    mMap.addPolyline(lineOptions);

                    progressDialog.dismiss();
                } else {
                    Log.d("onPostExecute", "without Polylines drawn");

                    progressDialog.dismiss();
                }

            } else {

                progressDialog.dismiss();

                CustomToast.showToast(MapsActivity.this, "Please Check your Internet Connection", MDToast.TYPE_INFO);

            }
        }

    }


}
