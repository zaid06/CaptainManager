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

import bilal.com.captain.Firebase;
import bilal.com.captain.R;
import bilal.com.captain.classes.BoldCustomTextView;

/**
 * Created by ikodePC-1 on 1/17/2018.
 */

public class UsersListAdapter extends ArrayAdapter<Firebase> {

    ArrayList<Firebase> arrayList;

    Context context;

    LayoutInflater layoutInflater;

    public UsersListAdapter(@NonNull Context context, ArrayList<Firebase> arrayList) {
        super(context, 0, arrayList);

        this.context = context;

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.users_list_item_for_single_chatting,parent,false);

        }

        Firebase firebase = getItem(position);

        final ViewHolder viewHolder = new ViewHolder(convertView);

        viewHolder.boldCustomTextView_name.setText(firebase.getUsername());

        if(firebase.getIsonline()){

            viewHolder.isOnline.setVisibility(View.VISIBLE);

        }else {

            viewHolder.isOnline.setVisibility(View.INVISIBLE);

        }

        return convertView;
    }

    static class ViewHolder{
        BoldCustomTextView boldCustomTextView_name;

        ImageView isOnline;

        public ViewHolder(View v){

            this.boldCustomTextView_name = (BoldCustomTextView) v.findViewById(R.id.name);

            this.isOnline = (ImageView) v.findViewById(R.id.isonline);

        }

    }
}
