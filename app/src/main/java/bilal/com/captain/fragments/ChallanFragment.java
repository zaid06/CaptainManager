package bilal.com.captain.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import bilal.com.captain.ExpenseGlobal;
import bilal.com.captain.R;
import bilal.com.captain.adapters.ChallanCustomAdapter;
import bilal.com.captain.adapters.RepairCustomAdapter;
import bilal.com.captain.models.ExpenseModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChallanFragment extends Fragment {

    ArrayList<ExpenseModel> challan = new ArrayList<>();
    ListView listView;
    View view;
    ChallanCustomAdapter customAdapter;


    public ChallanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_challan, container, false);

        for (int i = 0; i < ExpenseGlobal.e_array.size(); i++) {
            if ("Challan".equals(ExpenseGlobal.e_array.get(i).getType())) {
                challan.add(ExpenseGlobal.e_array.get(i));
            }
        }

        listView = (ListView) view.findViewById(R.id.challanListview);
        customAdapter = new ChallanCustomAdapter(getContext(), challan);
        listView.setAdapter(customAdapter);

        return view;
    }

}
