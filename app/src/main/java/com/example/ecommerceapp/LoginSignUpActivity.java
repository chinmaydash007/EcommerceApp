package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;

import com.example.ecommerceapp.Fragments.LoginFragment;
import com.example.ecommerceapp.Fragments.SignUpFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class LoginSignUpActivity extends AppCompatActivity {
    LoginFragment loginFragment;
    SignUpFragment signUpFragment;
    TabLayout tabLayout;
    ViewPagerAdapeter viewPagerAdapeter;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_purple_one));

        loginFragment = new LoginFragment();
        signUpFragment = new SignUpFragment();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapeter = new ViewPagerAdapeter(getSupportFragmentManager(), 0);

        tabLayout.setupWithViewPager(viewPager);
        viewPagerAdapeter.addFragment(loginFragment, "Login");
        viewPagerAdapeter.addFragment(signUpFragment, "SignUp");

        viewPager.setAdapter(viewPagerAdapeter);


    }

    class ViewPagerAdapeter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitle = new ArrayList<>();


        public ViewPagerAdapeter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitle.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);

        }
    }
}
