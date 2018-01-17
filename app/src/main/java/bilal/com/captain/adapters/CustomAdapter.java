package bilal.com.captain.adapters;

import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bilal.com.captain.CashDetails;
import bilal.com.captain.Global;
import bilal.com.captain.R;
import bilal.com.captain.models.IncomeModel;

/**
 * Created by shame on 2018-01-17.
 */

public class CustomAdapter extends BaseAdapter {

    TextView date,cash;

    Context context;


    ArrayList<IncomeModel> data;

    LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<IncomeModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.from(context).inflate(R.layout.cash_custom_layout,viewGroup,false);

        date = (TextView)view.findViewById(R.id.cashdate);
        cash = (TextView)view.findViewById(R.id.cashcash);

        date.setText(data.get(i).getDate());
        cash.setText(data.get(i).getIncome());




        return view;
    }
}
