package com.example.motasim.photo.Customer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.DBLogin;
import com.example.motasim.photo.Lists_Items.List_Photo_Item;
import com.example.motasim.photo.R;
import com.example.motasim.photo.Send_Classes.Send_Data_Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Calendar;

public class ContactActivity extends AppCompatActivity {


    Calendar mCalendar;
    int year , month , day , hour , minute;
    RadioButton mRadioButtonCity1 , mRadioButtonCity2 , mRadioButtonCity3;
    Button Contact , OrderType;
    FloatingActionButton SelectDate , SelectTime;
    public String Photo_email , Customer_email , Customer_name;
    String getCustomerEmail , getCity , getType , getDate , getTime ,  mTextDate  , mTextTime , getNote , mTextnote , getEmail , getPrice , getCustomerName;
    DBLogin mDBLogin;
    EditText mEditTextNote;
    RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact2);


        mRadioButtonCity1 = findViewById(R.id.City1);
        mRadioButtonCity2 = findViewById(R.id.City2);
        mRadioButtonCity3 = findViewById(R.id.City3);

        OrderType = findViewById(R.id.btnType);
        mEditTextNote = findViewById(R.id.ordernoteContact);

        Contact = findViewById(R.id.btnContactPhoto);

        SelectDate = findViewById(R.id.btnSelectDate);
        SelectTime = findViewById(R.id.btnSelectTime);

        mDBLogin = new DBLogin(this);

        Photo_email = PhotoProfileActivity.email;
        Customer_email = mDBLogin.get_Email();
        Show_User_Details();

        mTextnote = mEditTextNote.getText().toString();


        OrderType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactActivity.this , SelectOrderTypeActivity.class));
            }
        });



        mCalendar = Calendar.getInstance();
        year = mCalendar.get(Calendar.YEAR);
        month = mCalendar.get(Calendar.MONTH);
        day = mCalendar.get(Calendar.DAY_OF_MONTH);

        hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        minute = mCalendar.get(Calendar.MINUTE);

        month = month + 1;


        SelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePickerDialog = new DatePickerDialog(ContactActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        mTextDate = year + "/" + month + "/" + day;
                    }
                }, year, month, day);
                mDatePickerDialog.show();
            }
        });

        SelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog mTimePickerDialog = new TimePickerDialog(ContactActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        mTextTime = hour + "/" + minute;
                    }
                } , hour , minute , true);
                mTimePickerDialog.show();
            }
        });
        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ContactActivity.this);
                mBuilder.setMessage(R.string.Contact_Send_Message)
                        .setIcon(android.R.drawable.stat_notify_chat)
                        .setTitle(R.string.Contact_Send_Title)
                        .setPositiveButton(R.string.Contact_Send_Pos_Btn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                getCustomerName = Customer_name;
                                getCustomerEmail = Customer_email;

                                if (mRadioButtonCity1.isChecked()){
                                    getCity = mRadioButtonCity1.getText().toString();

                                } else if (mRadioButtonCity2.isChecked()){
                                    getCity = mRadioButtonCity2.getText().toString();

                                } else {
                                    getCity = mRadioButtonCity3.getText().toString();

                                }
                                try {
                                    Intent Company_GetData = getIntent();
                                    getType = Company_GetData.getExtras().getString("name");
                                    getPrice = Company_GetData.getExtras().getString("price");


                                    getDate = mTextDate;
                                    getTime = mTextTime;
                                    getNote = mTextnote;
                                    getEmail = Photo_email;

                                    final Response.Listener <String> Order = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                boolean success = jsonObject.getBoolean("success");
                                                if (success){
                                                    Toast.makeText(ContactActivity.this, R.string.Contact_Send_Success_Message, Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }else {
                                                    Toast.makeText(ContactActivity.this, R.string.Contact_Send_Error_Message, Toast.LENGTH_SHORT).show();
                                                }
                                            }catch (Exception e){
                                                Toast.makeText(ContactActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    };
                                    Send_Data_Order mSend_data_order = new Send_Data_Order(getCustomerName ,getCustomerEmail ,
                                            getCity,getType,getDate,getTime,getPrice,getEmail , getNote, Order);
                                    RequestQueue order = Volley.newRequestQueue(ContactActivity.this);
                                    order.add(mSend_data_order);
                                }catch (Exception ex){
                                    Toast.makeText(ContactActivity.this, R.string.Contact_Send_Catch_Message, Toast.LENGTH_SHORT).show();
                                }


                            }
                        })
                .setNegativeButton(R.string.Contact_Send_Neg_Btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
            }
        });
    }

    public void Show_User_Details(){
        String NEWS_URL = "http://192.168.43.57/photo/users_profile.php?email=" + Customer_email;
        requestQueue = Volley.newRequestQueue(ContactActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NEWS_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("users");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString("name");
                        String phone = object.getString("phone");

                        Customer_name = name;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

        });
        requestQueue.add(jsonObjectRequest);
    }

}
