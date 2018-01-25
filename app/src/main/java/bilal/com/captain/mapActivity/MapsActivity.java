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
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import bilal.com.captain.models.YearModel;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    com.ebanx.swipebtn.SwipeButton swipeButton;
    GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    AlertDialog.Builder dialogLocationBuilder;
    AlertDialog dialogLocation;
    Tracker tracker;
    double latitude, longitude;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle bundle = getIntent().getExtras();

        key = bundle.getString("key");

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
    }

    String cash_type = "Credit";

    private void openAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MapsActivity.this);
        final View view = getLayoutInflater().inflate(R.layout.end_ride_layout, null);

        final LinearLayout cash = (LinearLayout) view.findViewById(R.id.cash);
        final LinearLayout wallet = (LinearLayout) view.findViewById(R.id.wallet);
        final LinearLayout credit = (LinearLayout) view.findViewById(R.id.credit);

        final TextView tv_credit = (TextView) view.findViewById(R.id.tv_credit);

        final TextView tv_cash = (TextView) view.findViewById(R.id.tv_cash);

        final TextView tv_wallet = (TextView) view.findViewById(R.id.tv_wallet);

        TextView submit = (TextView) view.findViewById(R.id.submit2);

        final EditText enteramount = (EditText) view.findViewById(R.id.enter_amount);


        alert.setView(view);
        final AlertDialog dialog = alert.create();
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.MapDialogTheme;
        dialog.show();

        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                credit.setBackgroundColor(getResources().getColor(R.color.lightGray));

                tv_credit.setTextColor(getResources().getColor(R.color.colorBlack));

                cash.setBackgroundColor(getResources().getColor(R.color.themeColor));

                tv_cash.setTextColor(getResources().getColor(R.color.colorWhite));

                wallet.setBackgroundColor(getResources().getColor(R.color.themeColor));

                tv_wallet.setTextColor(getResources().getColor(R.color.colorWhite));

                enteramount.setVisibility(View.GONE);

                cash_type = tv_credit.getText().toString().trim();

            }
        });

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                credit.setBackgroundColor(getResources().getColor(R.color.themeColor));

                tv_credit.setTextColor(getResources().getColor(R.color.colorWhite));

                cash.setBackgroundColor(getResources().getColor(R.color.lightGray));

                tv_cash.setTextColor(getResources().getColor(R.color.colorBlack));

                wallet.setBackgroundColor(getResources().getColor(R.color.themeColor));

                tv_wallet.setTextColor(getResources().getColor(R.color.colorWhite));

                enteramount.setVisibility(View.VISIBLE);

                cash_type = tv_cash.getText().toString().trim();

            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                credit.setBackgroundColor(getResources().getColor(R.color.themeColor));

                tv_credit.setTextColor(getResources().getColor(R.color.colorWhite));

                cash.setBackgroundColor(getResources().getColor(R.color.themeColor));

                tv_cash.setTextColor(getResources().getColor(R.color.colorWhite));

                wallet.setBackgroundColor(getResources().getColor(R.color.lightGray));

                tv_wallet.setTextColor(getResources().getColor(R.color.colorBlack));

                enteramount.setVisibility(View.VISIBLE);

                cash_type = tv_wallet.getText().toString().trim();


            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cash_type.equals("")){

                    dialog.dismiss();

                    CustomToast.showToast(MapsActivity.this,"Select From Tab",MDToast.TYPE_ERROR);

                    return;

                }
                if (InternetConnection.internetConnectionAvailable(2000)) {
                    if (cash_type.equals("Credit")) {

                        saveIntoDb(0,"credit");

                        dialog.dismiss();

                        finish();

                        CustomToast.showToast(MapsActivity.this,"Submitted", MDToast.TYPE_SUCCESS);


                    } else if (cash_type.equals("Cash")) {

                        int amount = Integer.valueOf(enteramount.getText().toString().trim());

                        saveIntoDb(amount,"cash");

                        dialog.dismiss();

                        finish();

                        CustomToast.showToast(MapsActivity.this,"Submitted", MDToast.TYPE_SUCCESS);


                    }else if(cash_type.equals("Wallet")){

                        int amount = Integer.valueOf(enteramount.getText().toString().trim());

                        saveIntoDb(amount,"wallet");

                        dialog.dismiss();

                        finish();

                        CustomToast.showToast(MapsActivity.this,"Submitted", MDToast.TYPE_SUCCESS);

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

    private void saveIntoDb(int amount,String type){

        try {
            String currtime = (DateFormat.format("dd-MM-yyyy", new java.util.Date()).toString());

            String monthly = (DateFormat.format("MM-yyyy", new java.util.Date()).toString());

            String year = (DateFormat.format("yyyy", new java.util.Date()).toString());

            String key = FirebaseDatabase.
                    getInstance().
                    getReference().
                    child("Income").
                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                    push().getKey();

            IncomeModel incomeModel = new IncomeModel(amount,key,type,currtime,String.valueOf(tracker.getLatitude()),String.valueOf(tracker.getLongitude()),monthly,year);


            FirebaseDatabase.
                    getInstance().
                    getReference().
                    child("Income").
                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                    child(incomeModel.getKey()).setValue(incomeModel);

            FirebaseDatabase.
                    getInstance().
                    getReference().
                    child("Riding").
                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                    child(MapsActivity.this.key).
                    child("endlatitude").
                    setValue(tracker.getLatitude());

            FirebaseDatabase.
                    getInstance().
                    getReference().
                    child("Riding").
                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                    child(MapsActivity.this.key).
                    child("endlongitude").
                    setValue(tracker.getLongitude());
//                            startActivity(new Intent(MapsActivity.this, MainActivity.class));

        }catch (Exception e){

            Log.d("error", "onClick: "+e);

        }


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
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

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
        MapsActivity.this.latitude = location.getLatitude();
        MapsActivity.this.longitude = location.getLongitude();
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

}
