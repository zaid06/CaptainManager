package bilal.com.captain.activityForHoldMonthlyRecordFragments;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import bilal.com.captain.R;
import bilal.com.captain.fragmentsPagerAdapters.HoldMonthAndWeekFragmentPagerAdapter;

public class ActivityForHoldMonthlyAndWeeklyRecordFragment extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    HoldMonthAndWeekFragmentPagerAdapter holdMonthAndWeekFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_hold_monthly_and_weekly_record_fragment);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityForHoldMonthlyAndWeeklyRecordFragment.super.onBackPressed();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        viewPager = (ViewPager)findViewById(R.id.pager);

        holdMonthAndWeekFragmentPagerAdapter = new HoldMonthAndWeekFragmentPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(holdMonthAndWeekFragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
