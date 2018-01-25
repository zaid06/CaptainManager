package bilal.com.captain.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import bilal.com.captain.R;
import bilal.com.captain.classes.RegularCustomTextView;
import bilal.com.captain.models.ExpenseModel;

/**
 * Created by shame on 2018-01-22.
 */

public class ChallanCustomAdapter extends BaseAdapter {
    Context context;

    ArrayList<ExpenseModel> challan = new ArrayList<>();
    LayoutInflater inflater;

    public ChallanCustomAdapter(Context context, ArrayList<ExpenseModel> challan) {
        this.context = context;
        this.challan = challan;
    }


    @Override
    public int getCount() {
        return challan.size();
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
        final RegularCustomTextView sno,cash,date;
        final CardView cardView;

        view = inflater.from(context).inflate(R.layout.expense_details_custom_layout,viewGroup,false);


        sno = (RegularCustomTextView)view.findViewById(R.id.sno);
        cash = (RegularCustomTextView)view.findViewById(R.id.expensecustomcash);
        date = (RegularCustomTextView)view.findViewById(R.id.expensecustomdate);
        cardView = (CardView)view.findViewById(R.id.expensecustomcard);

        cash.setText("Rs. "+challan.get(i).getExpence());
        date.setText(challan.get(i).getDate());
        sno.setText((i+1)+"");

        return view;
    }
}
