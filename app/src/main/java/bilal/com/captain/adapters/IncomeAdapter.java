package bilal.com.captain.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.Serializable;
import java.util.ArrayList;

import bilal.com.captain.GlobalVariables;
import bilal.com.captain.MapsRouting;
import bilal.com.captain.R;
import bilal.com.captain.StartRide;
import bilal.com.captain.classes.RegularCustomTextView;
import bilal.com.captain.models.IncomeModel;

/**
 * Created by ikodePC-1 on 1/16/2018.
 */

public class IncomeAdapter extends ArrayAdapter<StartRide> implements Serializable {
    Context context;

    LayoutInflater inflater;

    ArrayList<StartRide> arrayList;

    public IncomeAdapter(@NonNull Context context, ArrayList<StartRide> arrayList) {
        super(context, 0,arrayList);
        this.arrayList = arrayList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){

            convertView = inflater.inflate(R.layout.row_item,parent,false);

        }

        final StartRide startRide = getItem(position);

        ViewHolder viewHolder = new ViewHolder(convertView);


        final int pos = position;

        if(pos == (arrayList.size()-1) ){

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
            float dp = 8f;
            float fpixels = metrics.density * dp;
            int pixels = (int) (fpixels + 0.5f);

            layoutParams.setMargins(pixels,0,pixels,8);

            viewHolder.cardView.setLayoutParams(layoutParams);

        }


        viewHolder.ride.setText((position+1)+"");

        viewHolder.name.setText(startRide.getName());

        viewHolder.start_ride.setText(startRide.getStartlatitude()+",\n"+startRide.getStartlongitude());

        viewHolder.end_ride.setText(startRide.getEndlatitude()+",\n"+startRide.getEndlongitude());

        viewHolder.openActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalVariables.startRide = startRide;

                Intent intent = new Intent(context, MapsRouting.class);

                context.startActivity(intent);

            }
        });

        return convertView;
    }

    static class ViewHolder{

        RegularCustomTextView ride;

        RegularCustomTextView name;

        RegularCustomTextView start_ride;

        RegularCustomTextView end_ride;

        ImageView openActivity;

        CardView cardView;

        public ViewHolder(View v){

            cardView = (CardView) v.findViewById(R.id.card);

            ride = (RegularCustomTextView) v.findViewById(R.id.ride);

            name = (RegularCustomTextView) v.findViewById(R.id.name);

            start_ride = (RegularCustomTextView) v.findViewById(R.id.start_points);

            end_ride = (RegularCustomTextView) v.findViewById(R.id.end_points);

            openActivity = (ImageView) v.findViewById(R.id.openActivity);

        }

    }
}
