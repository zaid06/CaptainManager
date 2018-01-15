package bilal.com.captain.mapActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

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

import bilal.com.captain.R;
import bilal.com.captain.Util.CustomToast;
import bilal.com.captain.Util.Tracker;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
//AIzaSyBz_uM4Zhlrp_tBIUECf5Wi19YiGwYMZ1o
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

        mUpdateLocation = (ImageButton) findViewById(R.id.button_update);
//        mUpdateLocation.setVisibility(View.GONE);
        mUpdateLocation.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("getting routes please wait");

        progressDialog.setCancelable(false);
        tracker = new Tracker(MapsActivity.this);

        bundle = getIntent().getExtras();
        if (bundle != null){
            popId = bundle.getString("popId");
        }

        getPopLocation();
        dialogBuilder();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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

        HashMap<String, String> userData;





        if (tracker.checkGPSStatus()){
            userLatitude = tracker.userLatitude;
            userLongitude = tracker.userLongitude;
//            LatLng pjpPosition = new LatLng(userLatitude, userLongitude);
//            mMap.addMarker(new MarkerOptions()
//                    .position(pjpPosition)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike))
//                    .title("Hello"));
//            userData.get("fname")

//            title("PJP Location")

//            try {
//                timer = new Timer();
//
//                timerTask = new TimerTask() {
//                    @Override
//                    public void run() {
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
////                                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
//
//
//                            }
//                        });
//                    }
//                };
//                //1800000
////                timer.schedule(timerTask, 3000 , 3000);
//
//                timer.schedule(timerTask, 10000, 10000);
//            } catch (Exception e) {
//
//                Log.d("exc", e.toString());
//
//            }

            LatLng pjpPosition = new LatLng(24.9072716, 67.0815411);
            Log.d("latLng", "onMapReady: "+tracker.getLatitude()+"  "+tracker.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(pjpPosition)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike))
                    .title("Hello"));


            mMap.moveCamera(CameraUpdateFactory.newLatLng(pjpPosition));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pjpPosition,
                    15));

            LatLng popPosition = new LatLng(24.9072716, 67.0815411);
            popMarker = mMap.addMarker(new MarkerOptions()
                    .position(popPosition)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_point))
                    .title("hello"));

//        title("POP Location")5


//        mMap.addPolyline(new PolylineOptions().add(new LatLng(userLatitude,userLongitude), new LatLng(latitude,longitude)).width(5).color(R.color.colorMain));

            LatLng origin = new LatLng(userLatitude,userLongitude);

            LatLng dest = new LatLng(24.9072716,67.0815411);

            String url = getUrl(origin,dest);


            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(origin);
            builder.include(dest);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
                    builder.build(), 250, 250, 0);

            mMap.moveCamera(cameraUpdate);




        }else{
            tracker.showSettingsAlert();
        }

        // Add a marker in Sydney and move the camera

//        mMap.animateCamera(cameraUpdate);

//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
//                builder.build(), 0, 250, 0);



//        mMap.moveCamera(CameraUpdateFactory.newLatLng(popPosition));

//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(popPosition,
//                15));

//        new RoutesAsync().execute(url);
    }

    private void dialogBuilder(){
        dialogLocationBuilder = new AlertDialog.Builder(this);
        dialogLocationBuilder.setTitle("Warning");
        dialogLocationBuilder.setMessage("Do you want to update POP Location?");
        dialogLocationBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (userLatitude != 0.0 && userLongitude != 0.0){
                    ContentValues cv = new ContentValues();
                    cv.put("latitude", String.valueOf(userLatitude));
                    cv.put("longitude", String.valueOf(userLongitude));

                    LatLng popUpdatedPosition = new LatLng(userLatitude, userLongitude);
                    popMarker.setPosition(popUpdatedPosition);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(popUpdatedPosition));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(popUpdatedPosition,
                            15));
                    Toast.makeText(MapsActivity.this, "Update Master Location", Toast.LENGTH_SHORT).show();
                }else{
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

    private void getPopLocation(){

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


    public String getUrl(LatLng origin, LatLng dest){

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
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            if(result != null) {
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

            }else {

                progressDialog.dismiss();

                CustomToast.showToast(MapsActivity.this,"Please Check your Internet Connection", MDToast.TYPE_INFO);

            }
        }

    }


}
