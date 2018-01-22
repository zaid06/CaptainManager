package bilal.com.captain;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class ExpenseDetails extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ExpensePagerAdapter expensePagerAdapter;

    private int[] tabIcons = {
            R.drawable.fuel,
            R.drawable.puncture,
            R.drawable.repair,
            R.drawable.challan,
            R.drawable.others
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);

//        toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabsExpense);
        viewPager = (ViewPager)findViewById(R.id.pagerExpense);

        expensePagerAdapter =  new ExpensePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(expensePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        viewPager.setOffscreenPageLimit(4);

    }

    private void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }
}
