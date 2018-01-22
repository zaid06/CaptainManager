package bilal.com.captain.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import bilal.com.captain.R;
import bilal.com.captain.classes.BoldCustomTextView;
import bilal.com.captain.classes.RegularCustomTextView;
import bilal.com.captain.models.ExpenseModel;

/**
 * Created by shame on 2018-01-22.
 */

public class FuelCustomAdapter extends BaseAdapter {
    RegularCustomTextView sno,cash,date;
    CardView cardView;
    Context context;

    ArrayList<ExpenseModel> fuel = new ArrayList<>();
    LayoutInflater inflater;


    public FuelCustomAdapter(Context context, ArrayList<ExpenseModel> fuel) {
        this.context = context;
        this.fuel = fuel;
    }


    @Override
    public int getCount() {
        return fuel.size();
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

        view = inflater.from(context).inflate(R.layout.expense_details_custom_layout,viewGroup,false);


        sno = (RegularCustomTextView)view.findViewById(R.id.sno);
        cash = (RegularCustomTextView)view.findViewById(R.id.expensecustomcash);
        date = (RegularCustomTextView)view.findViewById(R.id.expensecustomdate);
        cardView = (CardView)view.findViewById(R.id.expensecustomcard);

        cash.setText("Rs. "+fuel.get(i).getExpence());
        date.setText(fuel.get(i).getDate());
        sno.setText((i+1)+"");

        return view;
    }
}
