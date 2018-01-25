package bilal.com.captain.activityIncomeDetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import bilal.com.captain.MapsRouting;
import bilal.com.captain.R;
import bilal.com.captain.StartRide;
import bilal.com.captain.adapters.IncomeAdapter;
import bilal.com.captain.models.IncomeModel;

public class IncomeDetailActivity extends AppCompatActivity {

    ListView listView;

    ImageView backbutton;

    IncomeAdapter incomeAdapter;

    ArrayList<StartRide> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);

        initialize();

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IncomeDetailActivity.super.onBackPressed();
            }
        });
    }

    private final void initialize(){

        arrayList = new ArrayList<>();

        incomeAdapter = new IncomeAdapter(IncomeDetailActivity.this,arrayList);

        listView = (ListView) findViewById(R.id.list);

        backbutton = (ImageView) findViewById(R.id.achievebackbutton);

        listView.setAdapter(incomeAdapter);

        syncDataFromServer();


    }

    private void syncDataFromServer(){

        String currtime = (DateFormat.format("dd-MM-yyyy", new java.util.Date()).toString());

        FirebaseDatabase.
                getInstance().
                getReference().
                child("Riding").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                orderByChild("time").equalTo(currtime).
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        StartRide startRide = dataSnapshot.getValue(StartRide.class);

                        arrayList.add(startRide);

                        incomeAdapter.notifyDataSetChanged();

                        Log.d("Data", "onChildAdded: "+startRide.getName()+startRide.getStartlatitude());

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

}
