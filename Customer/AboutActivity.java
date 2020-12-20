package com.example.motasim.photo.Customer;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.motasim.photo.Custome_Class.AboutAdapter;
import com.example.motasim.photo.Models.Model;
import com.example.motasim.photo.R;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity {

    ViewPager mViewPager;
    AboutAdapter mAboutAdapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

    Button mCircularProgressButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mCircularProgressButton = (Button)findViewById(R.id.btnLoading);

        models = new ArrayList<>();
        models.add(new Model(R.drawable.background , "مصورين إحترافين" , "مجموعة من المصورين الاحترافين بين يديك !"));
        models.add(new Model(R.drawable.login_background , "لكافة المناسبات" , "احجز موصورك المناسب وبأقل تكلفة ولجميع المناسبات المختلفة"));
        models.add(new Model(R.drawable.start3 , "" , "مجموعة المصورين من كافة السودان"));
        models.add(new Model(R.drawable.start4 , "اطلب الان" , "ما الذي تنتظره ! اطلب الان"));

        mAboutAdapter = new AboutAdapter(models , this);
        mViewPager = findViewById(R.id.ViewPager);
        mViewPager.setAdapter(mAboutAdapter);
        mViewPager.setPadding(130 , 0 , 130 , 0);

        Integer[] Colors_Temp = {
                getResources().getColor(R.color.colorAdapter1),
                getResources().getColor(R.color.colorAdapter2),
                getResources().getColor(R.color.colorAdapter3),
                getResources().getColor(R.color.colorAdapter4)
        };

        colors = Colors_Temp;

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position < (mAboutAdapter.getCount() - 1) && position < (colors.length - 1)){
                    mViewPager.setBackgroundColor(
                            (Integer)mArgbEvaluator.evaluate(
                                    positionOffset ,
                                    colors[position] ,
                                    colors[position + 1]
                            )
                    );
                }else {
                    mViewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mCircularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent StartPage = new Intent(AboutActivity.this , LoginActivity.class);
                startActivity(StartPage);
            }
        });
    }

}
