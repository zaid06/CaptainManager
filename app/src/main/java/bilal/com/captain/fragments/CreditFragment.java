package bilal.com.captain.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import bilal.com.captain.Global;
import bilal.com.captain.R;
import bilal.com.captain.adapters.CreditCustomAdapter;
import bilal.com.captain.adapters.WalletCustomAdapter;
import bilal.com.captain.models.IncomeModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreditFragment extends Fragment {

    ArrayList<IncomeModel>credit = new ArrayList<>();

    ListView listView;

    CreditCustomAdapter customAdapter;

    View view;


    public CreditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_credit, container, false);

        for(int i = 0; i< Global.curr.size(); i++) {
            if ("credit".equals(Global.curr.get(i).getIncometype())) {
                credit.add(Global.curr.get(i));
            }
        }
        listView = (ListView) view.findViewById(R.id.creditlistview);

        customAdapter = new CreditCustomAdapter(getContext(), credit);

        listView.setAdapter(customAdapter);

        return view;

    }

}
