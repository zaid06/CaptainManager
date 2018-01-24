package bilal.com.captain;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.valdesekamdem.library.mdtoast.MDToast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import bilal.com.captain.Util.CustomToast;
import bilal.com.captain.Util.InternetConnection;
import bilal.com.captain.Util.Tracker;

import bilal.com.captain.Util.Util;
import bilal.com.captain.activityIncomeDetail.IncomeDetailActivity;
import bilal.com.captain.chatActivity.ChatActivity;
import bilal.com.captain.complainActivity.ComplainActivity;
import bilal.com.captain.expenceActivity.ExpenseActivity;
import bilal.com.captain.mapActivity.MapsActivity;
import bilal.com.captain.models.ExpenseModel;
import bilal.com.captain.models.IncomeModel;
import bilal.com.captain.resideMenu.ResideMenu;
import bilal.com.captain.resideMenu.ResideMenuItem;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    com.ebanx.swipebtn.SwipeButton swipeButton;
    private ResideMenu resideMenu;
    private ResideMenuItem itemTodayJc;
    private ResideMenuItem itemVisitSchedule;
    private ResideMenuItem itemDayEnd;
    private ResideMenuItem itemHierarchy;
    private ResideMenuItem itemPassword;
    private ResideMenuItem itemLogout;

    private ResideMenuItem itemText;



    LinearLayout complaint, expense;
    private Timer timer;
    private TimerTask timerTask;
    TextView time;
    LinearLayout cashinhand;
    LinearLayout expenseDetails;

    boolean timerCheck = true;

    TextView tv_cash,tv_expence,todaysGoal,tv_achieved,tv_remaining;

    ArrayList<IncomeModel> incomeModelArrayList;


    ArrayList<ExpenseModel> expenseModelArrayList;

    int totalGoal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.goal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });

        swipeButton = (com.ebanx.swipebtn.SwipeButton) findViewById(R.id.swipebutton);

        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                if(active == true){
                    openAlert();
                }

            }
        });



        findViewById(R.id.goal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,LiveVideoTestingUsingFirebase.class));
            }
        });

        findViewById(R.id.achieve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IncomeDetailActivity.class));
            }
        });

        cashinhand = (LinearLayout)findViewById(R.id.cash);

        cashinhand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.curr = incomeModelArrayList;
                startActivity(new Intent(MainActivity.this,CashInHand.class));
            }
        });

        expenseDetails = (LinearLayout)findViewById(R.id.remaining);

        expenseDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseGlobal.e_array = expenseModelArrayList;
                startActivity(new Intent(MainActivity.this,ExpenseDetails.class));
            }
        });

        findViewById(R.id.news_feed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,NewsFeedActivity.class));

            }
        });

        findViewById(R.id.chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
        });


        incomeModelArrayList = new ArrayList<>();

        expenseModelArrayList = new ArrayList<>();

        complaint = (LinearLayout) findViewById(R.id.complaint);

        expense = (LinearLayout) findViewById(R.id.expense);

        time = (TextView) findViewById(R.id.time);

        tv_cash = (TextView) findViewById(R.id.tv_cash);

        tv_expence = (TextView) findViewById(R.id.tv_expence);

        todaysGoal = (TextView) findViewById(R.id.todaysGoal);

        tv_remaining = (TextView) findViewById(R.id.tv_remaining);

        totalGoal = Integer.valueOf(todaysGoal.getText().toString().trim());

        tv_achieved = (TextView) findViewById(R.id.tv_achieved);

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
//        setUpMenu();



//        getDataFromServer();
        getDataFromServer();
        achieved();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void openAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.swipealertlayout,null);
        final EditText name = (EditText)view.findViewById(R.id.customerName);
        LinearLayout submit = (LinearLayout)view.findViewById(R.id.swipeSubmit);

        alert.setView(view);
        final AlertDialog dialog = alert.create();

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Util.etValidate(name))){

                    try{

                        Tracker tracker = new Tracker(MainActivity.this);
                        String et_name = String.valueOf(name.getText().toString());
                        Double et_long = tracker.getLongitude();
                        Double et_lat = tracker.getLatitude();

                        String currtime = (DateFormat.format("dd-MM-yyyy", new java.util.Date()).toString());


                         String key =  FirebaseDatabase.
                                getInstance().
                                getReference().
                                child("Riding").
                                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                push().
                                getKey();

                        Log.d("key", "onClick: "+key);

                        StartRide startRide = new StartRide(key,et_name,et_lat,et_long,Double.valueOf(0),Double.valueOf(0),currtime);

                        FirebaseDatabase.getInstance().getReference().child("Riding").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(startRide.getKey()).setValue(startRide);

                        dialog.dismiss();

                        CustomToast.showToast(MainActivity.this,"Submitted", MDToast.TYPE_SUCCESS);

                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);

                        intent.putExtra("key",startRide.getKey());

                        startActivity(intent);
                    }catch(Throwable e){
                        Log.d("Error", "onClick: "+e);
                    }


                }
            }
        });

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

            Tracker tracker = new Tracker(MainActivity.this);

            Toast.makeText(this, tracker.getLatitude()+"   "+tracker.getLongitude(), Toast.LENGTH_SHORT).show();

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

    long totalExpence = 0;

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

    ChildEventListener expenceEventListner = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            ExpenseModel expenseModel = dataSnapshot.getValue(ExpenseModel.class);

            expenseModelArrayList.add(expenseModel);

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

//            ExpenseModel expenseModel = dataSnapshot.getValue(ExpenseModel.class);
//
//            totalExpence -= expenseModel.getExpence();
//
//            tv_expence.setText(totalExpence+"");

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void achieved(){

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        progressDialog.setMessage("Please Wait...");

        progressDialog.show();

        try {
            final Timer timerForCheckAchievment = new Timer();

            TimerTask timer = new TimerTask() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                            if(InternetConnection.internetConnectionAvailable(2000)){
                                if(incomeModelArrayList != null && expenseModelArrayList != null){

                                    int totalIncome = 0;

                                    int expense = 0;

                                    for (IncomeModel incomeModel : incomeModelArrayList){

                                        totalIncome += incomeModel.getIncome();

                                    }

                                    for (ExpenseModel expenseModel : expenseModelArrayList){

                                        expense += expenseModel.getExpence();

                                    }

                                    tv_cash.setText("Rs. "+totalIncome);

                                    tv_expence.setText(""+expense);

                                    tv_achieved.setText( (incomeModelArrayList.size())+"" );

                                    tv_remaining.setText( (totalGoal - incomeModelArrayList.size())+"" );

                                    progressDialog.dismiss();

                                }
                            }else {

                                progressDialog.dismiss();

                            }
                        }
                    });
                }
            };
            //1800000
//                timer.schedule(timerTask, 3000 , 3000);

            timerForCheckAchievment.schedule(timer, 6000, 6000);
        } catch (Exception e) {

            Log.d("exc", e.toString());

        }

    }

    public void getDataFromServer() {

        String currtime = (DateFormat.format("dd-MM-yyyy", new java.util.Date()).toString());

        FirebaseDatabase.
                getInstance().
                getReference().
                child("Expense").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                orderByChild("date").
                equalTo(currtime).
                addChildEventListener(expenceEventListner);

        FirebaseDatabase.
                getInstance().
                getReference().
                child("Income").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                orderByChild("date").equalTo(currtime).
                addChildEventListener(cashEventListener);

    }
}
