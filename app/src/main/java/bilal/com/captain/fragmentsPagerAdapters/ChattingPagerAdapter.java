package bilal.com.captain.fragmentsPagerAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import bilal.com.captain.fragments.AllUsersListForStartSingleChatting;
import bilal.com.captain.fragments.MakingGroupOfUsers;

/**
 * Created by ikodePC-1 on 1/17/2018.
 */

public class ChattingPagerAdapter extends FragmentPagerAdapter {

    public ChattingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return new AllUsersListForStartSingleChatting();
            case 1:
                return new MakingGroupOfUsers();
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
                return "Chatting";
            case 1:
                return "Undefined";
            default:
                return null;

        }
    }
}
