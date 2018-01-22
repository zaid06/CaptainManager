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
import bilal.com.captain.adapters.PunctureCustomAdapter;
import bilal.com.captain.models.ExpenseModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class PunctureFragment extends Fragment {

    ArrayList<ExpenseModel> puncture = new ArrayList<>();
    ListView listView;
    View view;
    PunctureCustomAdapter customAdapter;



    public PunctureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_puncture, container, false);

        for (int i = 0; i < ExpenseGlobal.e_array.size(); i++) {
            if ("Puncture".equals(ExpenseGlobal.e_array.get(i).getType())) {
                puncture.add(ExpenseGlobal.e_array.get(i));
            }
        }

        listView = (ListView) view.findViewById(R.id.punctureListview);
        customAdapter = new PunctureCustomAdapter(getContext(), puncture);
        listView.setAdapter(customAdapter);

        return view;
    }

}
