package bilal.com.captain;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

import bilal.com.captain.ShowYears.ShowYears;
import bilal.com.captain.Util.CustomToast;
import bilal.com.captain.Util.InternetConnection;
import bilal.com.captain.models.IncomeModel;

public class CashInHand extends AppCompatActivity {

    Toolbar toolbar;
    ImageView backbutton;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    private ArrayList<IncomeModel> incomeModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_in_hand);

//        toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        incomeModelArrayList = new ArrayList<>();

        backbutton =(ImageView) findViewById(R.id.cashinhandbackbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CashInHand.super.onBackPressed();
            }
        });


        findViewById(R.id.all_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InternetConnection.internetConnectionAvailable(2000) && incomeModelArrayList.size() > 0){

                    Global.curr = incomeModelArrayList;

                    startActivity(new Intent(CashInHand.this, ShowYears.class));

                }else {

                    CustomToast.showToast(CashInHand.this,"There Is No Data", MDToast.TYPE_INFO);

                }
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);

        getDataFromSever();
    }
    
    private void getDataFromSever(){

        FirebaseDatabase.
                getInstance().
                getReference().
                child("Income").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                addChildEventListener(cashEventListener);
    }


    ChildEventListener cashEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            IncomeModel incomeModel = dataSnapshot.getValue(IncomeModel.class);

            incomeModelArrayList.add(incomeModel);

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
    };
    
}
