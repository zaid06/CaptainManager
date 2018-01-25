package bilal.com.captain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bilal.com.captain.R;
import bilal.com.captain.models.IncomeModel;

/**
 * Created by shame on 2018-01-18.
 */

public class WalletCustomAdapter extends BaseAdapter {
    Context context;

    ArrayList<IncomeModel> data = new ArrayList<>();

    LayoutInflater inflater;

    public WalletCustomAdapter(Context context, ArrayList<IncomeModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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

        final TextView date, cash;
        view = inflater.from(context).inflate(R.layout.cash_custom_layout, viewGroup, false);

        date = (TextView) view.findViewById(R.id.cashdate);
        cash = (TextView) view.findViewById(R.id.cashcash);

        date.setText(data.get(i).getDate());
        cash.setText("Rs. " + data.get(i).getIncome());

        return view;
    }
}
