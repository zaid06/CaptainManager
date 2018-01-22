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
import bilal.com.captain.adapters.FuelCustomAdapter;
import bilal.com.captain.models.ExpenseModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuelFragment extends Fragment {

    ArrayList<ExpenseModel> fuel = new ArrayList<>();
    ListView listView;
    View view;
    FuelCustomAdapter customAdapter;


    public FuelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fuel, container, false);

        for (int i = 0; i < ExpenseGlobal.e_array.size(); i++) {
            if ("Fuel".equals(ExpenseGlobal.e_array.get(i).getType())) {
                fuel.add(ExpenseGlobal.e_array.get(i));
            }
        }

        listView = (ListView) view.findViewById(R.id.fuelListview);
        customAdapter = new FuelCustomAdapter(getContext(), fuel);
        listView.setAdapter(customAdapter);

        return view;

    }
}
