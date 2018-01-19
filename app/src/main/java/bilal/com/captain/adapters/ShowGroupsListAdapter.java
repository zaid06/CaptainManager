package bilal.com.captain.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import bilal.com.captain.R;
import bilal.com.captain.classes.BoldCustomTextView;
import bilal.com.captain.models.GroupNameUsersModel;

/**
 * Created by ikodePC-1 on 1/19/2018.
 */

public class ShowGroupsListAdapter extends ArrayAdapter<GroupNameUsersModel> {

    ArrayList<GroupNameUsersModel> arrayList;

    Context context;

    LayoutInflater inflater;

    public ShowGroupsListAdapter(@NonNull Context context, ArrayList<GroupNameUsersModel> arrayList) {
        super(context, 0,arrayList);
        this.arrayList = arrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){

            convertView = inflater.inflate(R.layout.users_list_item_for_single_chatting,parent,false);

        }

        GroupNameUsersModel groupNameUsersModel = getItem(position);

        final ViewHolder viewHolder = new ViewHolder(convertView);

        viewHolder.imageView.setVisibility(View.INVISIBLE);

        viewHolder.boldCustomTextView_group_name.setText(groupNameUsersModel.getGroupname());

        return convertView;

    }

    static class ViewHolder{

        BoldCustomTextView boldCustomTextView_group_name;

        ImageView imageView;

        ViewHolder(View v){

            boldCustomTextView_group_name = (BoldCustomTextView) v.findViewById(R.id.name);

            imageView = (ImageView) v.findViewById(R.id.isonline);

        }

    }

}
