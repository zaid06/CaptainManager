package bilal.com.captain.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import bilal.com.captain.Global;
import bilal.com.captain.GlobalVariables;
import bilal.com.captain.R;
import bilal.com.captain.adapters.AdapterForMonthlyRecordShow;
import bilal.com.captain.models.ModelForMonthlyRecordsShow;

/**
 * A simple {@link Fragment} subclass.
 */
public class Monthly extends Fragment {

    ArrayList<ModelForMonthlyRecordsShow> arrayList;

    ListView listView;

    View view;

    AdapterForMonthlyRecordShow adapterForMonthlyRecordShow;

    public Monthly() {
        // Required empty public constructor
    }
/*
January theme color
date
----------
500

total  total bold
500    bold

February color theme color

date
------------
200

total

 */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_monthly, container, false);

        initialise();

        return view;
    }

    private void initialise(){

        arrayList = new ArrayList<>();

        listView = (ListView) view.findViewById(R.id.list);

        int count = 0;

        int total = 0;

        String temp = "";

        for (int i=0; i< Global.curr.size(); i++){
            if(GlobalVariables.year.equals(Global.curr.get(i).getYear() )&&( !temp.equals(Global.curr.get(i).getMonthly() ))){
                arrayList.add(new ModelForMonthlyRecordsShow(
                        Global.curr.get(i).getIncome(),
                        Global.curr.get(i).getKey(),
                        Global.curr.get(i).getIncometype(),
                        Global.curr.get(i).getDate(),
                        Global.curr.get(i).getLatitude(),
                        Global.curr.get(i).getLongitude(),
                        Global.curr.get(i).getMonthly(),
                        Global.curr.get(i).getYear(),
                        "month")
                );
                arrayList.add(new ModelForMonthlyRecordsShow(
                        Global.curr.get(i).getIncome(),
                        Global.curr.get(i).getKey(),
                        Global.curr.get(i).getIncometype(),
                        Global.curr.get(i).getDate(),
                        Global.curr.get(i).getLatitude(),
                        Global.curr.get(i).getLongitude(),
                        Global.curr.get(i).getMonthly(),
                        Global.curr.get(i).getYear(),
                        "record")
                );
                total += Global.curr.get(i).getIncome();
                temp = Global.curr.get(i).getMonthly();
                count++;
            }else if(GlobalVariables.year.equals(Global.curr.get(i).getYear() )){
                arrayList.add(new ModelForMonthlyRecordsShow(
                        Global.curr.get(i).getIncome(),
                        Global.curr.get(i).getKey(),
                        Global.curr.get(i).getIncometype(),
                        Global.curr.get(i).getDate(),
                        Global.curr.get(i).getLatitude(),
                        Global.curr.get(i).getLongitude(),
                        Global.curr.get(i).getMonthly(),
                        Global.curr.get(i).getYear(),
                        "record")
                );
                total += Global.curr.get(i).getIncome();
            }

        }

        adapterForMonthlyRecordShow = new AdapterForMonthlyRecordShow(getContext(),arrayList);

        listView.setAdapter(adapterForMonthlyRecordShow);

    }

}
