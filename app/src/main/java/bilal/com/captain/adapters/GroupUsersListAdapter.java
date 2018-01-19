package bilal.com.captain.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.ArrayList;

import bilal.com.captain.Firebase;
import bilal.com.captain.R;
import bilal.com.captain.classes.BoldCustomTextView;
import bilal.com.captain.classes.GroupUsersModel;

/**
 * Created by ikodePC-1 on 1/19/2018.
 */

public class GroupUsersListAdapter extends ArrayAdapter<Firebase> {

    ArrayList<Firebase> arrayList;

    Context context;

    LayoutInflater layoutInflater;

    GroupUsersModel[] groupUsersModels;

    public GroupUsersListAdapter(@NonNull Context context, ArrayList<Firebase> arrayList) {
        super(context, 0, arrayList);

        this.context = context;

        layoutInflater = LayoutInflater.from(context);

        this.groupUsersModels = new GroupUsersModel[arrayList.size()];

        for (int i=0; i< this.groupUsersModels.length; i++){

            this.groupUsersModels[i] = new GroupUsersModel(arrayList.get(i).getUsername(),arrayList.get(i).getId(),false);

        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.creat_group_users_list_item,parent,false);

        }

        Firebase firebase = getItem(position);

        final int pos = position;

        final GroupUsersListAdapter.ViewHolder viewHolder = new GroupUsersListAdapter.ViewHolder(convertView);

        viewHolder.boldCustomTextView_name.setText(firebase.getUsername());

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(buttonView.isChecked()){

                    viewHolder.checkBox.setChecked(true);

                    groupUsersModels[pos].setFlag(true);

                }else {

                    groupUsersModels[pos].setFlag(false);

                    viewHolder.checkBox.setChecked(false);

                }

            }
        });

        if(this.groupUsersModels[pos].isFlag()){

            viewHolder.checkBox.setChecked(true);

        }else {

            viewHolder.checkBox.setChecked(false);

        }


        return convertView;
    }

    public GroupUsersModel[] getGroupUsersModels() {
        return groupUsersModels;
    }

    static class ViewHolder{
        BoldCustomTextView boldCustomTextView_name;

        CheckBox checkBox;

        public ViewHolder(View v){

            this.boldCustomTextView_name = (BoldCustomTextView) v.findViewById(R.id.name);

            this.checkBox = (CheckBox) v.findViewById(R.id.checkbox);

        }

    }
}

