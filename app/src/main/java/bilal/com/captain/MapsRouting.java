package bilal.com.captain;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bilal.com.captain.Util.Tracker;
import bilal.com.captain.mapActivity.DataParser;
import bilal.com.captain.mapActivity.MapsActivity;

public class MapsRouting  extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private ImageButton mUpdateLocation;
    AlertDialog.Builder dialogLocationBuilder;
    AlertDialog dialogLocation;
    Tracker tracker;
    ImageView backbutton;
    Bundle bundle = new Bundle();
    double startLatitude, startLongitude;
    double endLatitude, endLongitude;
    String popId;
    Marker popMarker;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_routing);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("getting routes please wait");

        progressDialog.setCancelable(false);

        StartRide startRide = GlobalVariables.startRide;

        tracker = new Tracker(MapsRouting.this);

        startLatitude = startRide.getStartlatitude();

        startLongitude = startRide.getStartlongitude();

        endLatitude = startRide.getEndlatitude();

        endLongitude = startRide.getEndlongitude();

        bundle = getIntent().getExtras();
        if (bundle != null){
            popId = bundle.getString("popId");
        }

        dialogBuilder();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        backbutton = (ImageView) findViewById(R.id.maproutesbackbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

        if (tracker.checkGPSStatus()){
            startLatitude = tracker.userLatitude;
            startLongitude = tracker.userLongitude;
            LatLng pjpPosition = new LatLng(startLatitude, startLongitude);
            mMap.addMarker(new MarkerOptions()
                    .position(pjpPosition)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_point))
                    .title("Start"));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(pjpPosition));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pjpPosition,
                    15));
        }else{
            tracker.showSettingsAlert();
        }

        LatLng popPosition = new LatLng(endLatitude, endLongitude);
        popMarker = mMap.addMarker(new MarkerOptions()
                .position(popPosition)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_point))
                .title("End"));

        LatLng origin = new LatLng(startLatitude,startLongitude);

        LatLng dest = new LatLng(endLatitude,endLongitude);

        String url = getUrl(origin,dest);


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(origin);
        builder.include(dest);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(
                builder.build(), 250, 250, 0);

        mMap.moveCamera(cameraUpdate);

        new RoutesAsync().execute(url);
    }

    private void dialogBuilder(){
        dialogLocationBuilder = new AlertDialog.Builder(this);
        dialogLocationBuilder.setTitle("Warning");
        dialogLocationBuilder.setMessage("Do you want to update POP Location?");
        dialogLocationBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (startLatitude != 0.0 && startLongitude != 0.0){
                    ContentValues cv = new ContentValues();
                    cv.put("latitude", String.valueOf(startLatitude));
                    cv.put("longitude", String.valueOf(startLongitude));
                    LatLng popUpdatedPosition = new LatLng(startLatitude, startLongitude);
                    popMarker.setPosition(popUpdatedPosition);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(popUpdatedPosition));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(popUpdatedPosition,
                            15));
                    Toast.makeText(MapsRouting.this, "Update Master Location", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MapsRouting.this, "Error Get Location", Toast.LENGTH_SHORT).show();
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

    public String getUrl(LatLng origin, LatLng dest){

        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+origin.latitude+","+origin.longitude+"&destination="+dest.latitude+","+dest.longitude+"&%20waypoints=optimize:true%7C17.3916642,78.4346606%7C17.3865225743848,78.4374928101897%7C17.3813395082035,78.4382585808635&sensor=false&key=AIzaSyBz_uM4Zhlrp_tBIUECf5Wi19YiGwYMZ1o";

        return url;
    }

    // Todo download url

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

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

            String data = "";

            try {
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

            parserTask.execute(result);

        }
    }
//TODo parser task

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {

            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            if(result != null) {

                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<>();
                    lineOptions = new PolylineOptions();


                    List<HashMap<String, String>> path = result.get(i);

                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    lineOptions.addAll(points);
                    lineOptions.width(10);
                    lineOptions.color(R.color.colorMain);

                    Log.d("onPostExecute", "onPostExecute lineoptions decoded");

                }

                if (lineOptions != null) {
                    mMap.addPolyline(lineOptions);
                } else {
                    Log.d("onPostExecute", "without Polylines drawn");
                }

            }else {

                progressDialog.dismiss();

                Toast.makeText(MapsRouting.this, "", Toast.LENGTH_SHORT).show();

            }
        }

    }

}
