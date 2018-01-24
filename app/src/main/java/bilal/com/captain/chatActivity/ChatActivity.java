package bilal.com.captain.chatActivity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import bilal.com.captain.R;
import bilal.com.captain.fragmentsPagerAdapters.ChattingPagerAdapter;

public class ChatActivity extends AppCompatActivity {

    ViewPager viewPager;

    ImageView backbutton;

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        viewPager = (ViewPager) findViewById(R.id.vp);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        backbutton = (ImageView) findViewById(R.id.chattingbackbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatActivity.super.onBackPressed();
            }
        });

        ChattingPagerAdapter chattingPagerAdapter = new ChattingPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(chattingPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }
}
