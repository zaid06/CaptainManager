package bilal.com.captain.activityIncomeDetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import bilal.com.captain.R;
import bilal.com.captain.StartRide;
import bilal.com.captain.adapters.IncomeAdapter;
import bilal.com.captain.models.IncomeModel;

public class IncomeDetailActivity extends AppCompatActivity {

    ListView listView;

    IncomeAdapter incomeAdapter;

    ArrayList<StartRide> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);

        initialize();
    }

    private final void initialize(){

        arrayList = new ArrayList<>();

        incomeAdapter = new IncomeAdapter(IncomeDetailActivity.this,arrayList);

        listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(incomeAdapter);

        syncDataFromServer();

    }

    private void syncDataFromServer(){

        FirebaseDatabase.
                getInstance().
                getReference().
                child("Riding").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
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
