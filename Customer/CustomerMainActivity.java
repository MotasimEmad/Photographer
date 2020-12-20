package com.example.motasim.photo.Customer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.motasim.photo.R;

public class CustomerMainActivity extends AppCompatActivity {

    FrameLayout mFrameLayout;
    BottomNavigationView mBottomNavigationView;


    private PhotoCustomerFragment mPhotoCustomerFragment;
    private ProfileCustomerFragment mProfileCustomerFragment;
    private MoreCustomerFragment mMoreCustomerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_main);

        mFrameLayout = findViewById(R.id.FrameLayout);
        mBottomNavigationView = findViewById(R.id.BottomNavigationView);


        mPhotoCustomerFragment = new PhotoCustomerFragment();
        mProfileCustomerFragment = new ProfileCustomerFragment();
        mMoreCustomerFragment = new MoreCustomerFragment();


        setFragment(mPhotoCustomerFragment);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.navi_photo:
                        setFragment(mPhotoCustomerFragment);
                        return true;


                    case R.id.navi_profile:
                        setFragment(mProfileCustomerFragment);
                        return true;

                    case R.id.navi_more:
                        setFragment(mMoreCustomerFragment);
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.menu_notification){
            Intent NotificationActivity = new Intent(CustomerMainActivity.this , CustomerNotificationActivity.class);
            startActivity(NotificationActivity);
        } else if(itemID == R.id.menu_search){
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}

