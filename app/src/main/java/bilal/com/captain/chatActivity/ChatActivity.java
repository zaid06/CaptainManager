package bilal.com.captain.chatActivity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bilal.com.captain.R;
import bilal.com.captain.fragmentsPagerAdapters.ChattingPagerAdapter;

public class ChatActivity extends AppCompatActivity {

    ViewPager viewPager;

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        viewPager = (ViewPager) findViewById(R.id.vp);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        ChattingPagerAdapter chattingPagerAdapter = new ChattingPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(chattingPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}
