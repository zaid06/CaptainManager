package bilal.com.captain.expenceActivity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

import bilal.com.captain.CaptainInterfaces.Initialization;
import bilal.com.captain.R;
import bilal.com.captain.Util.CustomToast;
import bilal.com.captain.Util.Util;
import bilal.com.captain.models.ExpenseModel;

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

        if (v.getId() == R.id.fuel) {

            openAlert("Fuel", R.drawable.fuel_1);

        } else if (v.getId() == R.id.puncture) {

            openAlert("Puncture", R.drawable.puncture_2);

        } else if (v.getId() == R.id.repair) {

            openAlert("Repair", R.drawable.repair_1);

        } else if (v.getId() == R.id.challan) {

            openAlert("Challan", R.drawable.challan_1);

        } else if (v.getId() == R.id.others) {

            openAlert("Others", R.drawable.others);

        }

    }

    private void openAlert(final String type, final int imageResource) {

        AlertDialog.Builder alert = new AlertDialog.Builder(ExpenseActivity.this);

        View view = getLayoutInflater().inflate(R.layout.alert_layout, null);

        final EditText et_expence = (EditText) view.findViewById(R.id.writeExpence);

        final ImageView icon = (ImageView) view.findViewById(R.id.image);

        icon.setImageResource(imageResource);

        LinearLayout submit = (LinearLayout) view.findViewById(R.id.submit);

        alert.setView(view);

        final AlertDialog dialog = alert.create();

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.etValidate(et_expence)){

                    try {
                        String currtime = (DateFormat.format("dd-MM-yyyy", new java.util.Date()).toString());

                        long expence = Long.valueOf(et_expence.getText().toString().trim());

                        String key =  FirebaseDatabase.
                                            getInstance().
                                            getReference().
                                            child("Expense").
                                            child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                            push().
                                            getKey();

                        ExpenseModel expenseModel = new ExpenseModel(key,expence,type,currtime);

                        FirebaseDatabase.
                                getInstance().
                                getReference().
                                child("Expense").
                                child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                child(expenseModel.getKey()).
                                setValue(expenseModel);

                        dialog.dismiss();

                        CustomToast.showToast(ExpenseActivity.this,"Submitted",MDToast.TYPE_SUCCESS);

                    }catch (Throwable e){
                        Log.d("Error", "onClick: "+e);
                    }

                }
            }
        });


        dialog.show();

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
