package com.example.motasim.photo.Photoger;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.motasim.photo.R;

public class PhotoMainActivity extends AppCompatActivity {

    FrameLayout mFrameLayout;
    BottomNavigationView mBottomNavigationView;


    private HomePhotoFragment mHomePhotoFragment;
    private AgreeOrderPhotoFragment mAgreeOrderPhotoFragment;
    private ProfilePhotoFragment mProfilePhotoFragment;
    private MorePhotoFragment mMorePhotoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_main2);

        mFrameLayout = findViewById(R.id.FrameLayout);
        mBottomNavigationView =findViewById(R.id.BottomNavigationView);

        mHomePhotoFragment = new HomePhotoFragment();
        mAgreeOrderPhotoFragment = new AgreeOrderPhotoFragment();
        mProfilePhotoFragment = new ProfilePhotoFragment();
        mMorePhotoFragment = new MorePhotoFragment();

        setFragment(mHomePhotoFragment);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navi_home:
                        setFragment(mHomePhotoFragment);
                        return true;

                    case R.id.navi_photo:
                        setFragment(mAgreeOrderPhotoFragment);
                        return true;


                    case R.id.navi_profile:
                        setFragment(mProfilePhotoFragment);
                        return true;

                    case R.id.navi_more:
                        setFragment(mMorePhotoFragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.FrameLayout , fragment);
        mFragmentTransaction.commit();
    }
}
