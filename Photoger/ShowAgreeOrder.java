package com.example.motasim.photo.Photoger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.motasim.photo.Custome_Class.MyButton;
import com.example.motasim.photo.Custome_Class.MyText;
import com.example.motasim.photo.R;

public class ShowAgreeOrder extends AppCompatActivity {


    MyText mMyTextName , mMyTextType , mMyTextPhone , mMyTextCity , mMyTextDate , mMyTextTime , mMyTextAgree , mMyTextPrice , mMyTextNote;
    MyButton Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_agree_order);

        mMyTextName = findViewById(R.id.AllOrderCusName);
        mMyTextType = findViewById(R.id.AllOrderCusType);
        mMyTextPhone = findViewById(R.id.AllOrderCusPhone);
        mMyTextCity = findViewById(R.id.AllOrderCusCity);
        mMyTextDate = findViewById(R.id.AllOrderCusDate);
        mMyTextTime = findViewById(R.id.AllOrderCusTime);
        mMyTextAgree = findViewById(R.id.AllOrderCusAgree);
        mMyTextPrice = findViewById(R.id.AllOrderCusPrice);
        mMyTextNote = findViewById(R.id.AllOrderCusNote);

        Intent Company_GetData = getIntent();
        mMyTextName.setText(Company_GetData.getExtras().getString("name"));
        mMyTextType.setText(Company_GetData.getExtras().getString("type"));
        mMyTextPhone.setText(Company_GetData.getExtras().getString("number"));
        mMyTextCity.setText(Company_GetData.getExtras().getString("city"));
        mMyTextDate.setText(Company_GetData.getExtras().getString("date"));
        mMyTextTime.setText(Company_GetData.getExtras().getString("time"));
        mMyTextPrice.setText(Company_GetData.getExtras().getString("price"));
        mMyTextNote.setText(Company_GetData.getExtras().getString("note"));

        Back = findViewById(R.id.btnAllOrderBack);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
