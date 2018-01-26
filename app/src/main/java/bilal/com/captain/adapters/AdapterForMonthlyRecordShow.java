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
import bilal.com.captain.models.ModelForMonthlyRecordsShow;

/**
 * Created by ikodePC-1 on 1/25/2018.
 */

public class AdapterForMonthlyRecordShow extends ArrayAdapter<ModelForMonthlyRecordsShow> {

    LayoutInflater layoutInflater;

    Context context;

    public AdapterForMonthlyRecordShow(@NonNull Context context, ArrayList<ModelForMonthlyRecordsShow> arrayList) {
        super(context, 0,arrayList);

        this.context = context;

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ModelForMonthlyRecordsShow modelForMonthlyRecordsShow = getItem(position);

        if(convertView == null){

            if(modelForMonthlyRecordsShow.getType().equals("month")){

                convertView = layoutInflater.inflate(R.layout.show_month_title_item,parent,false);

                final BoldCustomTextView title = (BoldCustomTextView) convertView.findViewById(R.id.title);

                setTitle(title, modelForMonthlyRecordsShow.getMonthly());

            }else if(modelForMonthlyRecordsShow.getType().equals("record")){
                convertView = layoutInflater.inflate(R.layout.cash_custom_layout,parent,false);

                final BoldCustomTextView cashcash = (BoldCustomTextView) convertView.findViewById(R.id.cashcash);

                final RegularCustomTextView date = (RegularCustomTextView) convertView.findViewById(R.id.cashdate);

                cashcash.setText("Rs. "+modelForMonthlyRecordsShow.getIncome());

                date.setText(""+modelForMonthlyRecordsShow.getDate());


            }else if(modelForMonthlyRecordsShow.getType().equals("total")){

                convertView = layoutInflater.inflate(R.layout.show_total_item,parent,false);

                final BoldCustomTextView total = (BoldCustomTextView) convertView.findViewById(R.id.total);

                total.setText(""+modelForMonthlyRecordsShow.getTotal());

            }

        }


        return convertView;
    }

    private void setTitle(BoldCustomTextView boldCustomTextView, String month){

        String month_start_two_char = month.substring(0, 2);

        if(month_start_two_char.equals("01")){
            boldCustomTextView.setText("January:");
        }else if(month_start_two_char.equals("02")){
            boldCustomTextView.setText("February:");
        }else if(month_start_two_char.equals("03")){
            boldCustomTextView.setText("March:");
        }else if(month_start_two_char.equals("04")){
            boldCustomTextView.setText("April:");
        }else if(month_start_two_char.equals("05")){
            boldCustomTextView.setText("May:");
        }else if(month_start_two_char.equals("06")){
            boldCustomTextView.setText("June:");
        }else if(month_start_two_char.equals("07")){
            boldCustomTextView.setText("July:");
        }else if(month_start_two_char.equals("08")){
            boldCustomTextView.setText("August:");
        }else if(month_start_two_char.equals("09")){
            boldCustomTextView.setText("September:");
        }else if(month_start_two_char.equals("10")){
            boldCustomTextView.setText("October:");
        }else if(month_start_two_char.equals("11")){
            boldCustomTextView.setText("November:");
        }else if(month_start_two_char.equals("12")){
            boldCustomTextView.setText("December:");
        }
    }
}
