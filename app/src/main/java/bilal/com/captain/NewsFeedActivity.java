package bilal.com.captain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import bilal.com.captain.adapters.NewsFeedAdapter;
import bilal.com.captain.models.ComplainModel;

public class NewsFeedActivity extends AppCompatActivity {

    ArrayList<ComplainModel> arrayList;

    ListView listView;

    ProgressBar progressBar;

    NewsFeedAdapter newsFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        initialize();

        getNewsFeedFromServer();
    }

    private void initialize(){

        arrayList = new ArrayList<>();

        newsFeedAdapter = new NewsFeedAdapter(NewsFeedActivity.this,arrayList);

        listView = (ListView) findViewById(R.id.list);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        listView.setAdapter(newsFeedAdapter);

    }

    private void getNewsFeedFromServer(){

        FirebaseDatabase.
                getInstance().
                getReference().
                child("Complain").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ComplainModel complainModel = dataSnapshot.getValue(ComplainModel.class);

                arrayList.add(complainModel);

                newsFeedAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);

                listView.setVisibility(View.VISIBLE);

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
