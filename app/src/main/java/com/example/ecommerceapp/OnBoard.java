package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ecommerceapp.Adapter.OnBroadScreenPagerAdapter;
import com.example.ecommerceapp.Model.OnBoardScreenComponents;
import com.example.ecommerceapp.Utils.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class OnBoard extends AppCompatActivity {
    OnBroadScreenPagerAdapter onBroadScreenPagerAdapter;
    List<OnBoardScreenComponents> list;
    ViewPager viewPager;
    TextView skip;
    TextView next;
    CircleIndicator circleIndicator3;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_on_board);
        viewPager = findViewById(R.id.viewpager);
        skip = findViewById(R.id.textView2);
        next = findViewById(R.id.textView);
        circleIndicator3 = findViewById(R.id.circleindicator);


        list = new ArrayList<>();
        list.add(new OnBoardScreenComponents("Online Shopping", "Find every type of furniture in this e-commerce platform made for you.", R.drawable.one));
        list.add(new OnBoardScreenComponents("Online Order", "Quickly find things that you want and place order with the press of a button", R.drawable.two));
        list.add(new OnBoardScreenComponents("Online Payment", "You can pay via your UPI for easy and fast payment", R.drawable.three));

        onBroadScreenPagerAdapter = new OnBroadScreenPagerAdapter(list, this);
        viewPager.setAdapter(onBroadScreenPagerAdapter);
        circleIndicator3.setViewPager(viewPager);
        onBroadScreenPagerAdapter.registerDataSetObserver(circleIndicator3.getDataSetObserver());
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = viewPager.getCurrentItem();
                if (next.getText().equals("Finish") && position == list.size() - 1) {
                    goToMainActivity();
                }
                if (position < list.size() - 1) {
                    position++;
                    viewPager.setCurrentItem(position);
                }
                if (position == list.size() - 1) {
                    next.setText("Finish");
                }

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position < list.size() - 1) {
                    next.setText("Next");

                }
                if (position == list.size() - 1) {
                    next.setText("Finish");
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(OnBoard.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
