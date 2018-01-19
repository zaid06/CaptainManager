package bilal.com.captain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import bilal.com.captain.fragments.CashFragment;
import bilal.com.captain.fragments.CreditFragment;
import bilal.com.captain.fragments.WalletFragment;

/**
 * Created by shame on 2018-01-17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:

                return new CashFragment();

            case 1:

                return new WalletFragment();

            case 2:

                return new CreditFragment();

            default:

                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Cash";
            case 1:
                return "Wallet";
            case 2:
                return "Credit";
            default:
                return null;
        }
    }
}
