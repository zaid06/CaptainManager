package bilal.com.captain.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import bilal.com.captain.R;
import bilal.com.captain.classes.BoldCustomTextView;
import bilal.com.captain.classes.RegularCustomTextView;
import bilal.com.captain.models.SingleChatModel;

/**
 * Created by ikodePC-1 on 1/17/2018.
 */

public class SingleChattingAdapter extends ArrayAdapter<SingleChatModel> {

    ArrayList<SingleChatModel> arrayList;

    Context context;

    LayoutInflater inflater;

    public SingleChattingAdapter(@NonNull Context context, ArrayList<SingleChatModel> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;

        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SingleChatModel singleChatModel = getItem(position);

        if(singleChatModel.isFlag()){

            convertView = inflater.inflate(R.layout.send_message_layout,parent,false);

            final ViewHolder viewHolder = new ViewHolder(convertView);

            viewHolder.name_bBoldCustomTextView.setText(singleChatModel.getUser());

            viewHolder.message_reRegularCustomTextView.setText(singleChatModel.getMsgs());


        }else {

            convertView = inflater.inflate(R.layout.recieve_msg_layout,parent,false);

            final ViewHolder viewHolder = new ViewHolder(convertView);

            viewHolder.name_bBoldCustomTextView.setText(singleChatModel.getUser());

            viewHolder.message_reRegularCustomTextView.setText(singleChatModel.getMsgs());

        }



        return convertView;
    }

    static class ViewHolder{

        BoldCustomTextView name_bBoldCustomTextView;

        RegularCustomTextView message_reRegularCustomTextView;

        public ViewHolder(View v){

            this.name_bBoldCustomTextView = (BoldCustomTextView) v.findViewById(R.id.name);

            this.message_reRegularCustomTextView = (RegularCustomTextView) v.findViewById(R.id.message);
        }

    }
}
