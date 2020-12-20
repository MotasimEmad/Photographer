package com.example.motasim.photo.Photoger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.Custome_Class.MyButton;
import com.example.motasim.photo.Custome_Class.MyText;
import com.example.motasim.photo.Customer.ContactActivity;
import com.example.motasim.photo.DBLoginPhoto;
import com.example.motasim.photo.R;
import com.example.motasim.photo.Send_Classes.Send_Data_Order_State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ShowAllOrderDetActivity extends AppCompatActivity {

    MyText mMyTextName , mMyTextType , mMyTextPhone , mMyTextCity , mMyTextDate , mMyTextTime , mMyTextPrice , mMyTextNote, mMyTextAgree;
    MyButton Ok , No;
    RequestQueue requestQueue;
    String User_id , Order_State , User_Email;
    DBLoginPhoto mDBLoginPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_order_det);



        mMyTextName = findViewById(R.id.AllOrderCusName);
        mMyTextType = findViewById(R.id.AllOrderCusType);
        mMyTextPhone = findViewById(R.id.AllOrderCusPhone);
        mMyTextCity = findViewById(R.id.AllOrderCusCity);
        mMyTextDate = findViewById(R.id.AllOrderCusDate);
        mMyTextTime = findViewById(R.id.AllOrderCusTime);
        mMyTextPrice = findViewById(R.id.AllOrderCusPrice);
        mMyTextNote = findViewById(R.id.AllOrderCusNote);
        mMyTextAgree = findViewById(R.id.AllOrderCusAgree);


        Intent Company_GetData = getIntent();
        User_id = Company_GetData.getExtras().getString("id");
        mMyTextName.setText(Company_GetData.getExtras().getString("name"));
        mMyTextType.setText(Company_GetData.getExtras().getString("type"));
        mMyTextPhone.setText(Company_GetData.getExtras().getString("number"));
        mMyTextCity.setText(Company_GetData.getExtras().getString("city"));
        mMyTextDate.setText(Company_GetData.getExtras().getString("date"));
        mMyTextTime.setText(Company_GetData.getExtras().getString("time"));
        mMyTextPrice.setText(Company_GetData.getExtras().getString("price"));
        mMyTextNote.setText(Company_GetData.getExtras().getString(" "));

        mDBLoginPhoto = new DBLoginPhoto(this);
        User_Email = mDBLoginPhoto.get_Email();

        Ok = findViewById(R.id.btnAllOrderOk);
        No = findViewById(R.id.btnAllOrderDis);
    }


    public void OK(View view){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ShowAllOrderDetActivity.this);
        mBuilder.setMessage(R.string.Agree_Order_Message)
                .setIcon(android.R.drawable.stat_notify_chat)
                .setTitle(R.string.Agree_Order_Title)
                .setPositiveButton(R.string.Contact_Send_Pos_Btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String UserID = User_id;
                        Order_State = "yes";

                        final Response.Listener<String> Reg = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {

                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    boolean sucsse = jsonObject.getBoolean("success");

                                    if (sucsse) {
                                        Toast.makeText(ShowAllOrderDetActivity.this, R.string.Order_Success_Message, Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ShowAllOrderDetActivity.this, R.string.Order_Error_Message, Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(ShowAllOrderDetActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        Send_Data_Order_State send_data = new Send_Data_Order_State(UserID , Order_State, Reg);
                        RequestQueue req = Volley.newRequestQueue(ShowAllOrderDetActivity.this);
                        req.add(send_data);
                    }
                })
        .setNegativeButton(R.string.Contact_Send_Neg_Btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).show();

    }


    public void No(View view){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ShowAllOrderDetActivity.this);
        mBuilder.setMessage(R.string.DisAgree_Order_Message)
                .setIcon(android.R.drawable.stat_notify_chat)
                .setTitle(R.string.DisAgree_Order_Message)
                .setPositiveButton(R.string.Contact_Send_Pos_Btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String UserID = User_id;
                        Order_State = "dis";


                        final Response.Listener<String> Reg = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {

                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    boolean sucsse = jsonObject.getBoolean("success");

                                    if (sucsse) {
                                        Toast.makeText(ShowAllOrderDetActivity.this, R.string.Order_Success_Message, Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ShowAllOrderDetActivity.this, R.string.Order_Error_Message, Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(ShowAllOrderDetActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        Send_Data_Order_State send_data = new Send_Data_Order_State(UserID , Order_State, Reg);
                        RequestQueue req = Volley.newRequestQueue(ShowAllOrderDetActivity.this);
                        req.add(send_data);
                    }
                })
        .setNegativeButton(R.string.Contact_Send_Neg_Btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).show();
    }

}

