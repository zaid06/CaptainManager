package bilal.com.captain.chatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bilal.com.captain.GlobalVariables;
import bilal.com.captain.R;
import bilal.com.captain.Util.Util;
import bilal.com.captain.adapters.SingleChattingAdapter;
import bilal.com.captain.galleryActivity.GalleryActivity;
import bilal.com.captain.models.SingleChatModel;

public class StartGroupChattingActivity extends AppCompatActivity {

    EditText editText;

    Button send;

    ListView listView;

    Bundle bundle;

    SingleChattingAdapter singleChattingAdapter;

    ArrayList<SingleChatModel> arrayList;

    ProgressDialog progressDialog;

    HorizontalScrollView horizontal_scroll_view;

    LinearLayout parentImageShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_group_chatting);

        initialize();

        findViewById(R.id.mPhotoPickerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(StartGroupChattingActivity.this, GalleryActivity.class), Util.REQUEST_CODE_CAPTURE_IMAGE);

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String message = editText.getText().toString().trim();

                String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

                String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());


                if(selectedItems != null && selectedItems.size() > 0){
                    progressDialog.show();
                    uploadPictures(message);

                }else {

                    SingleChatModel singleChatModel = new SingleChatModel(message, "Sender", true, "hello", date + " " + time, FirebaseDatabase.getInstance().getReference().child("GroupChatting").child(GlobalVariables.groupNameUsersModel.getPrimarypushkey()).child(GlobalVariables.groupNameUsersModel.getAdminkey()).push().getKey(), null);

//                FirebaseDatabase.getInstance().getReference().child("GroupChatting").child(GlobalVariables.groupNameUsersModel.getPrimarypushkey()).child(GlobalVariables.groupNameUsersModel.getAdminkey()).child(singleChatModel.getKey()).setValue(singleChatModel);

//                singleChatModel.setFlag(false);

                    for (int i = 0; i < GlobalVariables.groupNameUsersModel.getUsers().size(); i++) {

                        if (GlobalVariables.groupNameUsersModel.getUsers().get(i).getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                            singleChatModel.setFlag(true);

                            FirebaseDatabase.getInstance().getReference().child("GroupChatting").child(GlobalVariables.groupNameUsersModel.getPrimarypushkey()).child(GlobalVariables.groupNameUsersModel.getUsers().get(i).getKey()).child(singleChatModel.getKey()).setValue(singleChatModel);

                        } else {

                            singleChatModel.setFlag(false);

                            FirebaseDatabase.getInstance().getReference().child("GroupChatting").child(GlobalVariables.groupNameUsersModel.getPrimarypushkey()).child(GlobalVariables.groupNameUsersModel.getUsers().get(i).getKey()).child(singleChatModel.getKey()).setValue(singleChatModel);
                        }
                    }


                    editText.setText("");
                }

//                new SentReadMsgs( msg, "sender", firebaseAuth.getCurrentUser().getDisplayName(),dttm,databaseReference.push().getKey(),null,true);
//
//                databaseReference.child(uid).child(firebaseAuth.getCurrentUser().getUid()).child(sentReadMsgs.getKey()).setValue(sentReadMsgs);
//
//                sentReadMsgs.setFlag(false);
//
//                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child(uid).child(sentReadMsgs.getKey()).setValue(sentReadMsgs);


            }
        });

        getMessages();

    }

    private void initialize(){

        arrayList = new ArrayList<>();

        singleChattingAdapter = new SingleChattingAdapter(StartGroupChattingActivity.this,arrayList);

        editText = (EditText) findViewById(R.id.messageEditText);

        send = (Button) findViewById(R.id.sendButton);

        listView = (ListView) findViewById(R.id.messageListView);

        listView.setAdapter(singleChattingAdapter);

        progressDialog = new ProgressDialog(getApplicationContext());

        progressDialog = new ProgressDialog(this);

//        check = (TextView) findViewById(R.id.check);

        progressDialog.setMessage("Sending Message...");

//        check.setVisibility(View.GONE);

        progressDialog.setCanceledOnTouchOutside(false);

        horizontal_scroll_view = (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);

        parentImageShow = (LinearLayout) findViewById(R.id.parentImageShow);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.toString().trim().length()>0){
                    send.setEnabled(true);
                }else {
                    send.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void getMessages(){
        FirebaseDatabase.getInstance().getReference().child("GroupChatting").child(GlobalVariables.groupNameUsersModel.getPrimarypushkey()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                SingleChatModel singleChatModel = dataSnapshot.getValue(SingleChatModel.class);

                arrayList.add(singleChatModel);

                singleChattingAdapter.notifyDataSetChanged();

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

    ArrayList<String> selectedItems;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case Util.REQUEST_CODE_CAPTURE_IMAGE:

                if(GalleryActivity.selectedItems != null && GalleryActivity.selectedItems.size() >0){

                    horizontal_scroll_view.setVisibility(View.VISIBLE);

//                        progressDialog.show();

                    for (int i = 0; i < GalleryActivity.selectedItems.size(); i++){



                        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
                        float dp = 100f;
                        float fpixels = metrics.density * dp;
                        int pixels = (int) (fpixels + 0.5f);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(pixels, pixels);

                        ImageView imageView = new ImageView(getApplicationContext());

                        imageView.setLayoutParams(layoutParams);

                        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                        Glide.with(getApplicationContext())
                                .load(GalleryActivity.selectedItems.get(i))
                                .into(imageView);


                        parentImageShow.addView(imageView);

                    }

                    selectedItems = GalleryActivity.selectedItems;

                }
//                    else if(){
//
//
//                    }

                break;
        }
    }

    int index = 0;

    ArrayList<String> selectDownloadUrls = new ArrayList<>();

    private void uploadPictures(final String message){

        if( (selectedItems.size()) == index ){

            index = 0;

            String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

            String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());


            SingleChatModel singleChatModel = new SingleChatModel(message, "Sender", true, "hello", date + " " + time, FirebaseDatabase.getInstance().getReference().child("GroupChatting").child(GlobalVariables.groupNameUsersModel.getPrimarypushkey()).child(GlobalVariables.groupNameUsersModel.getAdminkey()).push().getKey(), selectDownloadUrls);

//                FirebaseDatabase.getInstance().getReference().child("GroupChatting").child(GlobalVariables.groupNameUsersModel.getPrimarypushkey()).child(GlobalVariables.groupNameUsersModel.getAdminkey()).child(singleChatModel.getKey()).setValue(singleChatModel);

//                singleChatModel.setFlag(false);

            for (int i = 0; i < GlobalVariables.groupNameUsersModel.getUsers().size(); i++) {

                if (GlobalVariables.groupNameUsersModel.getUsers().get(i).getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                    singleChatModel.setFlag(true);

                    FirebaseDatabase.getInstance().getReference().child("GroupChatting").child(GlobalVariables.groupNameUsersModel.getPrimarypushkey()).child(GlobalVariables.groupNameUsersModel.getUsers().get(i).getKey()).child(singleChatModel.getKey()).setValue(singleChatModel);

                } else {

                    singleChatModel.setFlag(false);

                    FirebaseDatabase.getInstance().getReference().child("GroupChatting").child(GlobalVariables.groupNameUsersModel.getPrimarypushkey()).child(GlobalVariables.groupNameUsersModel.getUsers().get(i).getKey()).child(singleChatModel.getKey()).setValue(singleChatModel);
                }
            }


            editText.setText("");
            selectDownloadUrls.clear();

            selectedItems.clear();

            GalleryActivity.selectedItems = null;

            selectedItems = null;

            horizontal_scroll_view.setVisibility(View.GONE);

            parentImageShow.removeAllViews();

            progressDialog.dismiss();
// ;

            editText.setText("");


        }else {

            File file = new File(selectedItems.get(index));

            String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());

            FirebaseStorage.getInstance().getReference().child("Chatting").child(timeStamp).putFile(Uri.fromFile(file)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    index++;

                    selectDownloadUrls.add(String.valueOf(taskSnapshot.getDownloadUrl()));

                    uploadPictures(message);
                }
            });


        }

    }
}
