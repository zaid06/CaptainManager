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
import bilal.com.captain.models.IncomeModel;

/**
 * Created by ikodePC-1 on 1/25/2018.
 */

public class ShowYearAdapter extends ArrayAdapter<IncomeModel> {

    ArrayList<IncomeModel> data;

    LayoutInflater layoutInflater;

    public ShowYearAdapter(@NonNull Context context, ArrayList<IncomeModel> data) {
        super(context, 0,data);

        layoutInflater  = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.show_year_item,parent,false);

        }

        IncomeModel incomeModel = getItem(position);

        final ViewHolder viewHolder = new ViewHolder(convertView);

        viewHolder.boldCustomTextView.setText(incomeModel.getYear());

        return convertView;
    }

    static class ViewHolder{
        BoldCustomTextView boldCustomTextView;

        public ViewHolder(View v){

            this.boldCustomTextView = v.findViewById(R.id.yearTitle);

        }

    }
}
