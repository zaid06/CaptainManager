package bilal.com.captain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bilal.com.captain.complainActivity.ComplainActivity;
import bilal.com.captain.expenceActivity.ExpenseActivity;
import bilal.com.captain.resideMenu.ResideMenu;
import bilal.com.captain.resideMenu.ResideMenuItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ResideMenu resideMenu;
    private ResideMenuItem itemTodayJc;
    private ResideMenuItem itemVisitSchedule;
    private ResideMenuItem itemDayEnd;
    private ResideMenuItem itemHierarchy;
    private ResideMenuItem itemPassword;
    private ResideMenuItem itemLogout;

    private ResideMenuItem itemText;

    ImageView radial_right, radial_left;

    LinearLayout complaint, expense;
    private Timer timer;
    private TimerTask timerTask;
    TextView time;

    boolean timerCheck = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        radial_right = (ImageView) findViewById(R.id.ic_arrow_right);

        complaint = (LinearLayout) findViewById(R.id.complaint);

        expense = (LinearLayout) findViewById(R.id.expense);

        time = (TextView) findViewById(R.id.time);

        radial_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Movement logic here

                        radial_right.setX(radial_right.getX() * 2);

                        Log.d("touch", "move: ");
                        return true;
                    case MotionEvent.ACTION_UP:
                        //Release logic here

                        radial_right.setX(radial_right.getX());
                        Log.d("touch", "up: ");
                        return true;
                }

                return false;
            }
        });

        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ComplainActivity.class));
            }
        });

        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExpenseActivity.class));
            }
        });
//        setUpMenu();


//        findViewById(R.id.complain).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, ComplainActivity.class));
//            }
//        });

        timerShow();
        setUpMenu();
    }

    private void timerShow() {


        try {
            timer = new Timer();

            timerTask = new TimerTask() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

//                                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");

                            if (timerCheck) {

                                String myDt = new SimpleDateFormat("dd-MMM-yyyy  hh:mm aa").format(Calendar.getInstance().getTime());

                                time.setText(myDt);

                                timerCheck = false;
                            } else {

                                String myDt = new SimpleDateFormat("dd-MMM-yyyy  hh mm aa").format(Calendar.getInstance().getTime());

                                time.setText(myDt);

                                timerCheck = true;

                            }
                        }
                    });
                }
            };
            //1800000
//                timer.schedule(timerTask, 3000 , 3000);

            timer.schedule(timerTask, 1000, 1000);
        } catch (Exception e) {

            Log.d("exc", e.toString());

        }


    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
//            Toast.makeText(MainActivity.this, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
//            Toast.makeText(MainActivity.this, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };
//
    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);



        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(0.5f);
        getCurrentUserName(resideMenu);
        itemTodayJc = new ResideMenuItem(this, R.drawable.ic_sync, "Today's JC", ResideMenu.DIRECTION_LEFT);
        itemVisitSchedule = new ResideMenuItem(this, R.drawable.ic_sync, "Visit Schedule", ResideMenu.DIRECTION_LEFT);
        itemDayEnd = new ResideMenuItem(this, R.drawable.ic_sync, "Reports", ResideMenu.DIRECTION_LEFT);
        itemHierarchy = new ResideMenuItem(this, R.drawable.ic_sync, "Download product Hierarchy", ResideMenu.DIRECTION_LEFT);

        itemPassword = new ResideMenuItem(this, R.drawable.ic_sync, "Change Password", ResideMenu.DIRECTION_LEFT);
        itemLogout = new ResideMenuItem(this, R.drawable.ic_sync, "Logout", ResideMenu.DIRECTION_LEFT);

        itemText = new ResideMenuItem(getApplicationContext(), "", ResideMenu.DIRECTION_LEFT);

        itemTodayJc.setOnClickListener(this);
        itemVisitSchedule.setOnClickListener(this);
        itemDayEnd.setOnClickListener(this);
        itemHierarchy.setOnClickListener(this);
        itemPassword.setOnClickListener(this);
        itemLogout.setOnClickListener(this);

        resideMenu.addMenuItem(itemTodayJc, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemVisitSchedule, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemDayEnd, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemHierarchy, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemPassword, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemLogout, ResideMenu.DIRECTION_LEFT);

        if (itemText != null) {
            resideMenu.addMenuItem(itemText, ResideMenu.DIRECTION_LEFT);

        }

        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        itemLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
                intentLogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentLogout);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {


        if (view == itemTodayJc) {

            Toast.makeText(this, "alert", Toast.LENGTH_SHORT).show();

        } else if (view == itemVisitSchedule) {

        } else if (view == itemDayEnd) {

        } else if (view == itemHierarchy) {

        } else if (view == itemPassword) {

        } else if (view == itemLogout) {

        }
        resideMenu.closeMenu();
    }

    private void getCurrentUserName(final ResideMenu r){

        FirebaseDatabase.getInstance().getReference().child("USER").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Firebase firebase = dataSnapshot.getValue(Firebase.class);

                Log.d("data", "onChildAdded: "+firebase.getUsername());

                r.setImageAndName(/*"Bilal(Offline user)"*/firebase.getUsername(), "V 2.2.6", R.drawable.ic_user);
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

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {



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
