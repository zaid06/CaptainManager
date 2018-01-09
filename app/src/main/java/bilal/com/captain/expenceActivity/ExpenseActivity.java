package bilal.com.captain.expenceActivity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import bilal.com.captain.CaptainInterfaces.Initialization;
import bilal.com.captain.R;

public class ExpenseActivity extends AppCompatActivity implements Initialization, View.OnClickListener {

    LinearLayout fuel, puncture, repair, challan, others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        initialise();
        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseActivity.super.onBackPressed();
            }
        });
    }

    @Override
    public void initialise() {

        fuel = (LinearLayout) findViewById(R.id.fuel);

        puncture = (LinearLayout) findViewById(R.id.puncture);

        repair = (LinearLayout) findViewById(R.id.repair);

        challan = (LinearLayout) findViewById(R.id.challan);

        others = (LinearLayout) findViewById(R.id.others);

        fuel.setOnClickListener(ExpenseActivity.this);

        puncture.setOnClickListener(ExpenseActivity.this);

        repair.setOnClickListener(ExpenseActivity.this);

        challan.setOnClickListener(ExpenseActivity.this);

        others.setOnClickListener(ExpenseActivity.this);

    }

    String tem = "";

    @Override
    public void onClick(View v) {



        AlertDialog.Builder alert = new AlertDialog.Builder(ExpenseActivity.this);

        View view = getLayoutInflater().inflate(R.layout.alert_layout, null);

        EditText et_fuel = (EditText) view.findViewById(R.id.writeExpence);

        LinearLayout submit = (LinearLayout) view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ExpenseActivity.this, tem, Toast.LENGTH_SHORT).show();

            }
        });

        alert.setView(view);

        AlertDialog dialog = alert.create();



        if (v.getId() == R.id.fuel) {

            tem = "fuel";

            dialog.show();

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        } else if (v.getId() == R.id.puncture) {

            tem = "puncture";

            dialog.show();

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        } else if (v.getId() == R.id.repair) {

            tem = "repair";

            dialog.show();

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        } else if (v.getId() == R.id.challan) {

            tem = "challan";

            dialog.show();

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        } else if (v.getId() == R.id.others) {

            tem = "others";

            dialog.show();

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }
}
