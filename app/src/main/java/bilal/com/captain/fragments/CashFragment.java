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
import bilal.com.captain.adapters.CustomAdapter;
import bilal.com.captain.models.IncomeModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CashFragment extends Fragment {

    ListView listView;

    ArrayList<IncomeModel>data = new ArrayList<>();

    CustomAdapter customAdapter;

    View view;

    public CashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate t|he layout for this fragment
        view = inflater.inflate(R.layout.fragment_cash, container, false);

        for(int i=0;i< Global.curr.size();i++) {
            if ("cash".equals(Global.curr.get(i).getIncometype())) {
                data.add(Global.curr.get(i));
            }
        }

        listView = (ListView) view.findViewById(R.id.cashlistview);


        customAdapter = new CustomAdapter(getContext(), data);

        listView.setAdapter(customAdapter);

        return view;

        }


    }

