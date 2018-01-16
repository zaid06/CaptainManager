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
import bilal.com.captain.StartRide;
import bilal.com.captain.classes.RegularCustomTextView;
import bilal.com.captain.models.IncomeModel;

/**
 * Created by ikodePC-1 on 1/16/2018.
 */

public class IncomeAdapter extends ArrayAdapter<StartRide> {
    Context context;

    LayoutInflater inflater;

    public IncomeAdapter(@NonNull Context context, ArrayList<StartRide> arrayList) {
        super(context, 0,arrayList);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){

            convertView = inflater.inflate(R.layout.row_item,parent,false);

        }

        StartRide startRide = getItem(position);

        ViewHolder viewHolder = new ViewHolder(convertView);

        viewHolder.ride.setText((position+1)+"");

        viewHolder.name.setText(startRide.getName());

        viewHolder.start_ride.setText(startRide.getStartlatitude()+",\n"+startRide.getStartlongitude());

        viewHolder.end_ride.setText(startRide.getEndlatitude()+",\n"+startRide.getEndlongitude());

        return convertView;
    }

    static class ViewHolder{

        RegularCustomTextView ride;

        RegularCustomTextView name;

        RegularCustomTextView start_ride;

        RegularCustomTextView end_ride;

        public ViewHolder(View v){

            ride = (RegularCustomTextView) v.findViewById(R.id.ride);

            name = (RegularCustomTextView) v.findViewById(R.id.name);

            start_ride = (RegularCustomTextView) v.findViewById(R.id.start_points);

            end_ride = (RegularCustomTextView) v.findViewById(R.id.end_points);

        }

    }
}
