package bilal.com.captain.ShowYears;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import bilal.com.captain.Global;
import bilal.com.captain.GlobalVariables;
import bilal.com.captain.R;
import bilal.com.captain.activityForHoldMonthlyRecordFragments.ActivityForHoldMonthlyAndWeeklyRecordFragment;
import bilal.com.captain.adapters.ShowYearAdapter;
import bilal.com.captain.models.IncomeModel;

public class ShowYears extends AppCompatActivity {

    ArrayList<IncomeModel> data;

    ListView listView;

    ShowYearAdapter showYearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_years);

        initialise();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                IncomeModel incomeModel = (IncomeModel) parent.getItemAtPosition(position);

                GlobalVariables.year = incomeModel.getYear();

                startActivity(new Intent(ShowYears.this, ActivityForHoldMonthlyAndWeeklyRecordFragment.class));

            }
        });

    }

    private void initialise(){

        String temp = "";

        listView = findViewById(R.id.list);

        data = new ArrayList<>();

        for (int i = 0; i< Global.curr.size();i++){

            if( !(temp.equals(Global.curr.get(i).getYear()))){

                data.add(Global.curr.get(i));

                temp = Global.curr.get(i).getYear();
            }

        }

        showYearAdapter = new ShowYearAdapter(ShowYears.this,data);

        listView.setAdapter(showYearAdapter);

    }
}
