package bilal.com.captain.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

import bilal.com.captain.Firebase;
import bilal.com.captain.GlobalVariables;
import bilal.com.captain.R;
import bilal.com.captain.Util.CustomToast;
import bilal.com.captain.Util.Util;
import bilal.com.captain.adapters.GroupUsersListAdapter;
import bilal.com.captain.adapters.ShowGroupsListAdapter;
import bilal.com.captain.chatActivity.StartGroupChattingActivity;
import bilal.com.captain.classes.GroupUsersModel;
import bilal.com.captain.models.GroupNameUsersModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakingGroupOfUsers extends Fragment {

    View view;

    FloatingActionButton fab;

    ListView listView;
    private Dialog dialogForPop;

    ArrayList<Firebase> arrayList;

    GroupUsersListAdapter groupUsersListAdapter;

    ArrayList<GroupNameUsersModel> groupNameUsersModelArrayList;

    ShowGroupsListAdapter showGroupsListAdapter;

    public MakingGroupOfUsers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_making_group_of_users, container, false);

        initialize();

        getDataFromServer();

        clicListners();

        return view;
    }

    private void initialize() {

        groupNameUsersModelArrayList = new ArrayList<>();

        arrayList = new ArrayList<>();

        showGroupsListAdapter = new ShowGroupsListAdapter(getContext(),groupNameUsersModelArrayList);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        listView = (ListView) view.findViewById(R.id.list);

        listView.setAdapter(showGroupsListAdapter);
    }


    private void clicListners() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arrayList.size() > 0) {

                    groupUsersListAdapter = new GroupUsersListAdapter(getContext(), arrayList);

                    showDialog();

                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GroupNameUsersModel groupNameUsersModel = (GroupNameUsersModel) parent.getItemAtPosition(position);

                GlobalVariables.groupNameUsersModel = groupNameUsersModel;

                GlobalVariables.groupName =groupNameUsersModel.getGroupname();

                startActivity(new Intent(getActivity(), StartGroupChattingActivity.class));

            }
        });

    }

    private void showDialog() {

        dialogForPop = new Dialog(getContext());
        dialogForPop.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogForPop.setContentView(R.layout.dialog_show_user_list);

        final ListView listView = (ListView) dialogForPop.findViewById(R.id.list);

        final EditText editText_group_name = (EditText) dialogForPop.findViewById(R.id.group_name);

        final LinearLayout create = (LinearLayout) dialogForPop.findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Util.etValidate(editText_group_name)) {

                    String group_name = editText_group_name.getText().toString().trim();

                    ArrayList<GroupUsersModel> arrayList = new ArrayList<>();

                    for (int i = 0; i < groupUsersListAdapter.getGroupUsersModels().length; i++) {

                        if (groupUsersListAdapter.getGroupUsersModels()[i].isFlag()) {

                            arrayList.add(groupUsersListAdapter.getGroupUsersModels()[i]);

                        }

                    }

                    if (arrayList.size() == 0) {

                        CustomToast.showToast(getContext(), "Please Select Members", MDToast.TYPE_INFO);

                    } else {
                        arrayList.add(new GroupUsersModel("Current User",FirebaseAuth.getInstance().getCurrentUser().getUid(),true));
                        String key = FirebaseDatabase.
                                getInstance().
                                getReference().
                                child("Groups").push().getKey();

                        GroupNameUsersModel groupNameUsersModel = new GroupNameUsersModel(key, group_name, FirebaseAuth.getInstance().getCurrentUser().getUid(), arrayList);

                        FirebaseDatabase.
                                getInstance().
                                getReference().
                                child("Groups").child(groupNameUsersModel.getPrimarypushkey()).setValue(groupNameUsersModel);

                        CustomToast.showToast(getContext(), "Group Created", MDToast.TYPE_SUCCESS);

                        dialogForPop.dismiss();
                    }
                }


            }
        });

        listView.setAdapter(groupUsersListAdapter);


        dialogForPop.show();

    }

    private void getDataFromServer() {

        FirebaseDatabase.
                getInstance().
                getReference().
                child("Groups").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                GroupNameUsersModel groupNameUsersModel = dataSnapshot.getValue(GroupNameUsersModel.class);

                if (groupNameUsersModel.getAdminkey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    groupNameUsersModelArrayList.add(groupNameUsersModel);
                    showGroupsListAdapter.notifyDataSetChanged();
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
                        showGroupsListAdapter.notifyDataSetChanged();
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
        });


        FirebaseDatabase.getInstance().getReference().child("Public_User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Firebase firebase = dataSnapshot.getValue(Firebase.class);

                if (!(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(firebase.getId()))) {

                    arrayList.add(firebase);
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
        });


    }

}
