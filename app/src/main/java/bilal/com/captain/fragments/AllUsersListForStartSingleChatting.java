package bilal.com.captain.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import bilal.com.captain.Firebase;
import bilal.com.captain.R;
import bilal.com.captain.adapters.UsersListAdapter;
import bilal.com.captain.chatActivity.StartOneToOneChatting;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllUsersListForStartSingleChatting extends Fragment {

    View v;

    ListView listView;

    ArrayList<Firebase> arrayList;

    UsersListAdapter usersListAdapter;

    public AllUsersListForStartSingleChatting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        v = inflater.inflate(R.layout.fragment_all_users_list_for_start_single_chatting, container, false);

        initialize();

        getFataFromServer();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Firebase firebase = (Firebase) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), StartOneToOneChatting.class);

                intent.putExtra("uid",firebase.getId());

                startActivity(intent);
            }
        });

        return v;
    }

    private void initialize(){

        arrayList = new ArrayList<>();

        usersListAdapter = new UsersListAdapter(getContext(),arrayList);

        listView = (ListView) v.findViewById(R.id.list);

        listView.setAdapter(usersListAdapter);
    }
    private void getFataFromServer(){
        FirebaseDatabase.getInstance().getReference().child("Public_User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Firebase firebase = dataSnapshot.getValue(Firebase.class);

                if(!( FirebaseAuth.getInstance().getCurrentUser().getUid().equals(firebase.getId())) ) {

                    arrayList.add(firebase);
                }

                usersListAdapter.notifyDataSetChanged();

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
