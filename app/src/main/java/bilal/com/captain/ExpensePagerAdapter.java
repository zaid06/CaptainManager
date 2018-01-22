package bilal.com.captain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import bilal.com.captain.fragments.CashFragment;
import bilal.com.captain.fragments.ChallanFragment;
import bilal.com.captain.fragments.CreditFragment;
import bilal.com.captain.fragments.FuelFragment;
import bilal.com.captain.fragments.MiscellaneousFragment;
import bilal.com.captain.fragments.PunctureFragment;
import bilal.com.captain.fragments.RepairFragment;
import bilal.com.captain.fragments.WalletFragment;

/**
 * Created by shame on 2018-01-18.
 */

public class ExpensePagerAdapter extends FragmentPagerAdapter {
    public ExpensePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:

                return new FuelFragment();

            case 1:

                return new PunctureFragment();

            case 2:

                return new RepairFragment();

            case 3:

                return new ChallanFragment();

            case 4:

                return new MiscellaneousFragment();

            default:

                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position){
//            case 0:
//                return "Fuel";
//            case 1:
//                return "Puncture";
//            case 2:
//                return "Repair";
//            case 3:
//                return "Challan";
//            case 4:
//                return "Miscellaneous";
//            default:
//                return null;
//        }
   //}
}
