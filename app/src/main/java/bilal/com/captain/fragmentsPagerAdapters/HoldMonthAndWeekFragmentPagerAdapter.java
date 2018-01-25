package bilal.com.captain.fragmentsPagerAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import bilal.com.captain.fragments.Monthly;
import bilal.com.captain.fragments.Weekly;

/**
 * Created by ikodePC-1 on 1/25/2018.
 */

public class HoldMonthAndWeekFragmentPagerAdapter extends FragmentPagerAdapter {

    public HoldMonthAndWeekFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:

                return new Monthly();

            case 1:

                return new Weekly();

            default:

                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Monthly";
            case 1:
                return "Weekly";
            default:
                return null;
        }
    }
}
