package bilal.com.captain.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bilal.com.captain.Global;
import bilal.com.captain.GlobalVariables;
import bilal.com.captain.R;
import bilal.com.captain.adapters.AdapterForMonthlyRecordShow;
import bilal.com.captain.models.ModelForMonthlyRecordsShow;
import bilal.com.captain.models.MonthSelectionFromDropDown;

/**
 * A simple {@link Fragment} subclass.
 */
public class Weekly extends Fragment {

    View view;

    TextView tv_month;

    ArrayList<MonthSelectionFromDropDown> arrayList;
    ArrayList<ModelForMonthlyRecordsShow> arrayList_show_list;
    private Dialog dialogForMonth;
    LinearLayout linear;
    private AdapterForMonthlyRecordShow adapterForMonthlyRecordShow;

    ListView listView;

    public Weekly() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weekly, container, false);

        initialise();

        String temp = "";

        for (int i = 0; i< Global.curr.size(); i++){

            if(GlobalVariables.year.equals(Global.curr.get(i).getYear())&&( !temp.equals(Global.curr.get(i).getMonthly()))){

                arrayList.add(new MonthSelectionFromDropDown(Global.curr.get(i).getMonthly()));

                temp = Global.curr.get(i).getMonthly();
            }
        }

        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForMonthShow();
            }
        });

        return view;
    }

    private void initialise(){

        arrayList = new ArrayList<>();

        tv_month = (TextView) view.findViewById(R.id.tv_month);

        listView = (ListView) view.findViewById(R.id.list);

        linear = (LinearLayout) view.findViewById(R.id.linear);

    }

    private void dialogForMonthShow(){

        dialogForMonth = new Dialog(getContext());
        dialogForMonth.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogForMonth.setContentView(R.layout.custom_dialog_list);

        ListView listView = (ListView) dialogForMonth.findViewById(R.id.listView);

        ArrayAdapter<MonthSelectionFromDropDown> arrayAdapter = new ArrayAdapter<MonthSelectionFromDropDown>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MonthSelectionFromDropDown monthSelectionFromDropDown = (MonthSelectionFromDropDown) adapterView.getItemAtPosition(i);

                tv_month.setText(""+monthSelectionFromDropDown);

                setListView(monthSelectionFromDropDown.getMonthly());

                dialogForMonth.dismiss();

            }
        });

        dialogForMonth.show();
    }

    private void setListView(String monthly){

        arrayList_show_list = new ArrayList<>();

        for (int i=0; i< Global.curr.size(); i++){
            if(Global.curr.get(i).getMonthly().equals(monthly)){

                arrayList_show_list.add(new ModelForMonthlyRecordsShow(
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

//                arrayList_show_list.add(new ModelForMonthlyRecordsShow(
//                        Global.curr.get(i).getIncome(),
//                        Global.curr.get(i).getKey(),
//                        Global.curr.get(i).getIncometype(),
//                        Global.curr.get(i).getDate(),
//                        Global.curr.get(i).getLatitude(),
//                        Global.curr.get(i).getLongitude(),
//                        Global.curr.get(i).getMonthly(),
//                        Global.curr.get(i).getYear(),
//                        "record")
//                );
//
            }
//            else if(Global.curr.get(i).getMonthly().equals(monthly)){
//
//                arrayList_show_list.add(new ModelForMonthlyRecordsShow(
//                        Global.curr.get(i)s.getIncome(),
//                        Global.curr.get(i).getKey(),
//                        Global.curr.get(i).getIncometype(),
//                        Global.curr.get(i).getDate(),
//                        Global.curr.get(i).getLatitude(),
//                        Global.curr.get(i).getLongitude(),
//                        Global.curr.get(i).getMonthly(),
//                        Global.curr.get(i).getYear(),
//                        "record")
//                );
//
//            }

        }

        adapterForMonthlyRecordShow = new AdapterForMonthlyRecordShow(getContext(),arrayList_show_list);

        listView.setAdapter(adapterForMonthlyRecordShow);

    }

}
