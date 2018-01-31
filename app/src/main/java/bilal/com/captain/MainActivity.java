package bilal.com.captain;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.valdesekamdem.library.mdtoast.MDToast;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import bilal.com.captain.Util.CustomToast;
import bilal.com.captain.Util.InternetConnection;
import bilal.com.captain.Util.OpenLocation;
import bilal.com.captain.Util.SaveInSharedPreference;
import bilal.com.captain.Util.ScreenshotUtils;
import bilal.com.captain.Util.Tracker;

import bilal.com.captain.Util.Util;
import bilal.com.captain.activityIncomeDetail.IncomeDetailActivity;
import bilal.com.captain.adapters.ShowGroupsListAdapter;
import bilal.com.captain.adapters.UsersListAdapter;
import bilal.com.captain.cameraActivity.CameraActivity;
import bilal.com.captain.chatActivity.ChatActivity;
import bilal.com.captain.chatActivity.StartOneToOneChatting;
import bilal.com.captain.classes.BoldCustomTextView;
import bilal.com.captain.complainActivity.ComplainActivity;
import bilal.com.captain.expenceActivity.ExpenseActivity;
import bilal.com.captain.mapActivity.MapsActivity;
import bilal.com.captain.models.ExpenseModel;
import bilal.com.captain.models.GroupNameUsersModel;
import bilal.com.captain.models.IncomeModel;
import bilal.com.captain.models.NotificationModel;
import bilal.com.captain.models.SingleChatModel;
import bilal.com.captain.notifications.PushService;
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
    ImageView logout;

    boolean timerCheck = true;

    TextView tv_cash, tv_expence, todaysGoal, tv_achieved, tv_remaining;

    ArrayList<IncomeModel> incomeModelArrayList;

    ArrayList<Firebase> arrayList_users;

    ArrayList<GroupNameUsersModel> groupNameUsersModelArrayList;

    ArrayList<ExpenseModel> expenseModelArrayList;

    int totalGoal = 0;

    Tracker tracker;

    UiThread uiThread = new UiThread();

    RelativeLayout rootContent;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.exit_alert, null);

        final LinearLayout yes = (LinearLayout) view.findViewById(R.id.exityes);
        final LinearLayout no = (LinearLayout) view.findViewById(R.id.exitno);

        alert.setView(view);
        final AlertDialog dialog = alert.create();

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.super.onBackPressed();
                dialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public enum ScreenshotType {
        FULL, CUSTOM;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tracker = new Tracker(MainActivity.this);

        rootContent = (RelativeLayout) findViewById(R.id.rootContent);

//        if(!tracker.checkGPSStatus()){
//            OpenLocation.openLocation(MainActivity.this);
////            return;
//        }
            uiThread.thread(MainActivity.this);

//            return;




        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot(ScreenshotType.FULL);
            }
        });

        findViewById(R.id.goal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser() != null){

            stopService(new Intent(MainActivity.this, PushService.class));

            startService(new Intent(MainActivity.this, PushService.class));

        }

        swipeButton = (com.ebanx.swipebtn.SwipeButton) findViewById(R.id.swipebutton);

        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                if (active == true) {

                    if(InternetConnection.internetConnectionAvailable(2000)){
                        openAlert();
                    }

                    else{
                        CustomToast.showToast(MainActivity
                                .this,"Please Check Internet Connection",MDToast.TYPE_ERROR);
                    }

                }

            }
        });


        logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
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

        cashinhand = (LinearLayout) findViewById(R.id.cash);

        cashinhand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Global.curr = incomeModelArrayList;
                startActivity(new Intent(MainActivity.this, CashInHand.class));
            }
        });

        expenseDetails = (LinearLayout) findViewById(R.id.remaining);

        expenseDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpenseGlobal.e_array = expenseModelArrayList;
                startActivity(new Intent(MainActivity.this, ExpenseDetails.class));
            }
        });

        findViewById(R.id.news_feed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, NewsFeedActivity.class));

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

        arrayList_users = new ArrayList<>();

        groupNameUsersModelArrayList = new ArrayList<>();

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

    private void takeScreenshot(ScreenshotType screenshotType) {
        Bitmap b = null;
        switch (screenshotType) {
            case FULL:
                //If Screenshot type is FULL take full page screenshot i.e our root content.
                b = ScreenshotUtils.getScreenShot(rootContent);
                break;

        }

        //If bitmap is not null
        if (b != null) {
//            showScreenShotImage(b);//show bitmap over imageview

            File saveFile = ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot

            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
            String mImageName="SS_"+ timeStamp +".jpg";

            File file = ScreenshotUtils.store(b, mImageName, saveFile);//save the screenshot to selected path

            if(file.exists()){

                openAlertForSendMessage(file);
//                Toast.makeText(this, "Do Here Of Sending", Toast.LENGTH_SHORT).show();

            }

//            shareScreenshot(file);//finally share screenshot
        } else
            //If bitmap is null show toast message
            Toast.makeText(this, "Failed To take Screen Shot", Toast.LENGTH_SHORT).show();

    }

    boolean isUser = true;

    private void openAlertForSendMessage(final File f){

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        final View view = getLayoutInflater().inflate(R.layout.send_screen_shot_user_layout, null);
        final LinearLayout cash = (LinearLayout) view.findViewById(R.id.users);
        final LinearLayout wallet = (LinearLayout) view.findViewById(R.id.group);
        final LinearLayout cancel = (LinearLayout) view.findViewById(R.id.cancel);
        final TextView tv_cash = (TextView) view.findViewById(R.id.tv_cash);
        final TextView tv_wallet = (TextView) view.findViewById(R.id.tv_wallet);
        final ListView listView = (ListView) view.findViewById(R.id.list);
        UsersListAdapter usersListAdapter = new UsersListAdapter(MainActivity.this,arrayList_users);
        listView.setAdapter(usersListAdapter);
        alert.setView(view);
        final AlertDialog dialog = alert.create();
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.MapDialogTheme;
        dialog.show();
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash.setBackgroundColor(getResources().getColor(R.color.lightGray));
                tv_cash.setTextColor(getResources().getColor(R.color.colorBlack));
                wallet.setBackgroundColor(getResources().getColor(R.color.themeColor));
                tv_wallet.setTextColor(getResources().getColor(R.color.colorWhite));
                isUser = true;
                UsersListAdapter usersListAdapter = new UsersListAdapter(MainActivity.this,arrayList_users);
                listView.setAdapter(usersListAdapter);
            }
        });
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash.setBackgroundColor(getResources().getColor(R.color.themeColor));
                tv_cash.setTextColor(getResources().getColor(R.color.colorWhite));
                wallet.setBackgroundColor(getResources().getColor(R.color.lightGray));
                tv_wallet.setTextColor(getResources().getColor(R.color.colorBlack));
                isUser = false;
                ShowGroupsListAdapter showGroupsListAdapter = new ShowGroupsListAdapter(MainActivity.this,groupNameUsersModelArrayList);
                listView.setAdapter(showGroupsListAdapter);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isUser){
                    Firebase firebase = (Firebase) parent.getItemAtPosition(position);
                    createpopUpForScreenShotSending(f,firebase,null);
                    dialog.dismiss();
                }else {
                    GroupNameUsersModel groupNameUsersModel = (GroupNameUsersModel) parent.getItemAtPosition(position);
                    createpopUpForScreenShotSending(f,null,groupNameUsersModel);
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser = true;
                dialog.dismiss();
                f.delete();
            }
        });
    }

    private void showMenu(View v) {
        PopupMenu show = new PopupMenu(this, v);

        show.inflate(R.menu.captainmenu);

        show.show();

        final Firebase firebase = new Firebase();

        show.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.signout:
                        FirebaseDatabase.getInstance().getReference().child("Public_User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("isonline").setValue(false);

                        FirebaseDatabase.getInstance().getReference().child("USER").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("user").child("isonline").setValue(false);

                        FirebaseAuth.getInstance().signOut();

                        //FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                          //      .child(String.valueOf(firebase.getIsonline())).setValue("false");

                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void openAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.swipealertlayout, null);
        final EditText name = (EditText) view.findViewById(R.id.customerName);
        LinearLayout submit = (LinearLayout) view.findViewById(R.id.swipeSubmit);

        alert.setView(view);
        final AlertDialog dialog = alert.create();

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Util.etValidate(name))) {

                    try {

                        Tracker tracker = new Tracker(MainActivity.this);
                        String et_name = String.valueOf(name.getText().toString());
                        Double et_long = tracker.getLongitude();
                        Double et_lat = tracker.getLatitude();

                        String currtime = (DateFormat.format("dd-MM-yyyy", new java.util.Date()).toString());


                        String key = FirebaseDatabase.
                                getInstance().
                                getReference().
                                child("Riding").
                                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                push().
                                getKey();

                        Log.d("key", "onClick: " + key);

                        StartRide startRide = new StartRide(key, et_name, et_lat, et_long, Double.valueOf(0), Double.valueOf(0), currtime);

                        FirebaseDatabase.getInstance().getReference().child("Riding").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child(startRide.getKey()).setValue(startRide);

                        dialog.dismiss();

                        CustomToast.showToast(MainActivity.this, "Submitted", MDToast.TYPE_SUCCESS);

                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        finish();

                        intent.putExtra("key", startRide.getKey());

                        startActivity(intent);
                    } catch (Throwable e) {
                        Log.d("Error", "onClick: " + e);
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

            Toast.makeText(this, tracker.getLatitude() + "   " + tracker.getLongitude(), Toast.LENGTH_SHORT).show();

        } else if (view == itemDayEnd) {

        } else if (view == itemHierarchy) {

        } else if (view == itemPassword) {

        } else if (view == itemLogout) {

        }
        resideMenu.closeMenu();
    }

    private void getCurrentUserName(final ResideMenu r) {

        FirebaseDatabase.getInstance().getReference().child("USER").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Firebase firebase = dataSnapshot.getValue(Firebase.class);

                Log.d("data", "onChildAdded: " + firebase.getUsername());

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

    ChildEventListener singleUserEventListners = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Firebase firebase = dataSnapshot.getValue(Firebase.class);
            if(!( FirebaseAuth.getInstance().getCurrentUser().getUid().equals(firebase.getId())) ) {
                arrayList_users.add(firebase);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Firebase firebase = dataSnapshot.getValue(Firebase.class);
            for (int i=0; i<arrayList_users.size(); i++){
                if(arrayList_users.get(i).getId().equals(firebase.getId())){
                    if(arrayList_users.get(i).getIsonline()){
                        arrayList_users.remove(i);
                        arrayList_users.add(i,firebase);
                    }else{
                        arrayList_users.remove(i);
                        arrayList_users.add(i,firebase);
                    }
                }
            }
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

    ChildEventListener groupUserEventListners = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            GroupNameUsersModel groupNameUsersModel = dataSnapshot.getValue(GroupNameUsersModel.class);

            if (groupNameUsersModel.getAdminkey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                groupNameUsersModelArrayList.add(groupNameUsersModel);
            } else {
                boolean flag = false;
                for (int i = 0; i < groupNameUsersModel.getUsers().size(); i++) {
                    if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(groupNameUsersModel.getUsers().get(i).getKey())) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    groupNameUsersModelArrayList.add(groupNameUsersModel);
                }
            }
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

    public void achieved() {

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
                            if (InternetConnection.internetConnectionAvailable(2000)) {
                                if (incomeModelArrayList != null && expenseModelArrayList != null) {

                                    int totalIncome = 0;

                                    int expense = 0;

                                    for (IncomeModel incomeModel : incomeModelArrayList) {

                                        totalIncome += incomeModel.getIncome();

                                    }

                                    for (ExpenseModel expenseModel : expenseModelArrayList) {

                                        expense += expenseModel.getExpence();

                                    }

                                    tv_cash.setText("Rs. " + totalIncome);

                                    tv_expence.setText("" + expense);

                                    tv_achieved.setText((incomeModelArrayList.size()) + "");

                                    tv_remaining.setText((totalGoal - incomeModelArrayList.size()) + "");

                                    progressDialog.dismiss();

                                }
                            } else {

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

        FirebaseDatabase.
                getInstance().
                getReference().
                child("Public_User").
                addChildEventListener(singleUserEventListners);

        FirebaseDatabase.
                getInstance().
                getReference().
                child("Groups").addChildEventListener(groupUserEventListners);

    }

    private void createpopUpForScreenShotSending(final File file,final Firebase firebase,final GroupNameUsersModel groupNameUsersModel){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        final View view = getLayoutInflater().inflate(R.layout.send_sms_screen_shot_alert, null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setImageURI(Uri.fromFile(file));
        final EditText editText = (EditText) view.findViewById(R.id.messageEditText);
        final BoldCustomTextView title = (BoldCustomTextView) view.findViewById(R.id.title);
        if(firebase == null){
            title.setText(groupNameUsersModel.getGroupname());
        }else if(groupNameUsersModel == null){
            title.setText(firebase.getUsername());
        }
        final Button sendButton = (Button) view.findViewById(R.id.sendButton);
        alert.setView(view);
        final AlertDialog dialog = alert.create();
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.MapDialogTheme;
        dialog.show();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = editText.getText().toString().trim();

                if(TextUtils.isEmpty(message)){
                    message = "Find An Attachment";
                }

                if(isUser){

                    sendScrenShotToUsers(firebase,null,message,file,dialog);

                }else {

                    sendScrenShotToUsers(null,groupNameUsersModel,message,file,dialog);

                }

            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                file.delete();
            }
        });

    }

    private void sendScrenShotToUsers(final Firebase firebase, final GroupNameUsersModel groupNameUsersModel, final String message, final File file, final AlertDialog dialog){

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        progressDialog.setMessage("Message Sending");

        progressDialog.show();

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

        FirebaseStorage.getInstance().getReference().child("Chatting").child(timeStamp).putFile(Uri.fromFile(file)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                ArrayList<String> selectDownloadUrls = new ArrayList<>();

                selectDownloadUrls.add(String.valueOf(taskSnapshot.getDownloadUrl()));

                String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

                String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

                SingleChatModel singleChatModel = new SingleChatModel(message,"Sender",true,"hello",date+" "+time, FirebaseDatabase.getInstance().getReference().child("Chatting").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("uid").push().getKey(),selectDownloadUrls);

                if(isUser){

                    FirebaseDatabase.getInstance().getReference().child("Chatting").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(firebase.getId()).child(singleChatModel.getKey()).setValue(singleChatModel);

                    singleChatModel.setFlag(false);

                    FirebaseDatabase.getInstance().getReference().child("Chatting").child(firebase.getId()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(singleChatModel.getKey()).setValue(singleChatModel);

                    if(message.length() > 6) {

                        sendNotification(message.substring(0,6)+"...",firebase);

                    }else {

                        sendNotification(message,firebase);

                    }

                } else {
                    for (int i = 0; i < groupNameUsersModel.getUsers().size(); i++) {

                        if (groupNameUsersModel.getUsers().get(i).getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                            singleChatModel.setFlag(true);

                            FirebaseDatabase.getInstance().getReference().child("GroupChatting").child(groupNameUsersModel.getPrimarypushkey()).child(groupNameUsersModel.getUsers().get(i).getKey()).child(singleChatModel.getKey()).setValue(singleChatModel);

                        } else {

                            singleChatModel.setFlag(false);

                            FirebaseDatabase.getInstance().getReference().child("GroupChatting").child(groupNameUsersModel.getPrimarypushkey()).child(groupNameUsersModel.getUsers().get(i).getKey()).child(singleChatModel.getKey()).setValue(singleChatModel);
                        }
                    }

                }

                file.delete();

                progressDialog.dismiss();

                dialog.dismiss();

                CustomToast.showToast(MainActivity.this,"Send Successfully",MDToast.TYPE_SUCCESS);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                file.delete();

                progressDialog.dismiss();

                dialog.dismiss();

                CustomToast.showToast(MainActivity.this,"Not Send",MDToast.TYPE_SUCCESS);
            }
        });


    }

    private void sendDataToMultipleUser(GroupNameUsersModel groupNameUsersModel, String message, File file){

    }

    private void sendNotification(String message, Firebase firebase){

        NotificationModel notificationModel = new NotificationModel(FirebaseDatabase.getInstance().getReference().child("ChattingNotification").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().getKey(), FirebaseAuth.getInstance().getCurrentUser().getUid(), SaveInSharedPreference.getInSharedPreference(MainActivity.this).getName(),message);

        FirebaseDatabase.getInstance().getReference().child("ChattingNotification").child(firebase.getId()).child(notificationModel.getPushkey()).setValue(notificationModel);

    }

}
