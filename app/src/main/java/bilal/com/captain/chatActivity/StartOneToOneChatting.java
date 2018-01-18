package bilal.com.captain.chatActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import bilal.com.captain.R;
import bilal.com.captain.adapters.SingleChattingAdapter;
import bilal.com.captain.models.SingleChatModel;

public class StartOneToOneChatting extends AppCompatActivity {

    EditText editText;

    Button send;

    ListView listView;

    String uid;

    Bundle bundle;

    SingleChattingAdapter singleChattingAdapter;

    ArrayList<SingleChatModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_one_to_one_chatting);

        bundle = getIntent().getExtras();

        uid = bundle.getString("uid");

        initialize();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String message = editText.getText().toString().trim();

                String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

                String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());


                SingleChatModel singleChatModel = new SingleChatModel(message,"Sender",true,"hello",date+" "+time, FirebaseDatabase.getInstance().getReference().child("Chatting").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("uid").push().getKey());

                FirebaseDatabase.getInstance().getReference().child("Chatting").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uid).child(singleChatModel.getKey()).setValue(singleChatModel);

                singleChatModel.setFlag(false);

                FirebaseDatabase.getInstance().getReference().child("Chatting").child(uid).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(singleChatModel.getKey()).setValue(singleChatModel);

                editText.setText("");

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

        singleChattingAdapter = new SingleChattingAdapter(StartOneToOneChatting.this,arrayList);

        editText = (EditText) findViewById(R.id.messageEditText);

        send = (Button) findViewById(R.id.sendButton);

        listView = (ListView) findViewById(R.id.messageListView);

        listView.setAdapter(singleChattingAdapter);

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
        FirebaseDatabase.getInstance().getReference().child("Chatting").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(uid).addChildEventListener(new ChildEventListener() {
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
}
