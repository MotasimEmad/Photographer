package com.example.motasim.photo.Photoger;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.Custome_Class.MyButton;

import com.example.motasim.photo.DBLoginPhoto;
import com.example.motasim.photo.R;

import com.example.motasim.photo.Send_Classes.Send_Data_Photo_Update_Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PhotoProfileUpdateActivity extends AppCompatActivity {

    MyButton Update ,  AddOrder;
    EditText UpdateName, UpdateEmail, UpdatePhone , UpdatePassword , UpdateCamera , UpdateSite;
    String User_Email , User_id;
    RequestQueue requestQueue;
    DBLoginPhoto mDBLoginPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_profile_update);

        Update =  findViewById(R.id.btn_update);
        AddOrder = findViewById(R.id.btnOrder);
        UpdateName =  findViewById(R.id.EditTextUpdateName);
        UpdateEmail =  findViewById(R.id.EditTextUpdateEmail);
        UpdatePhone =  findViewById(R.id.EditTextUpdatePhone);
        UpdatePassword = findViewById(R.id.EditTextUpdatePassword);
        UpdateCamera = findViewById(R.id.EditTextUpdateCamera);
        UpdateSite = findViewById(R.id.EditTextUpdateSite);
        mDBLoginPhoto = new DBLoginPhoto(this);
        User_Email = mDBLoginPhoto.get_Email();

        AddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhotoProfileUpdateActivity.this , UpdateOrderThingActivity.class));
            }
        });

        Show_User_Details();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = UpdateName.getText().toString();
                String getEmail = UpdateEmail.getText().toString();
                String getPhone = UpdatePhone.getText().toString();
                String getPassword = UpdatePassword.getText().toString();
                String getCamera = UpdateCamera.getText().toString();
                String getSite = UpdateSite.getText().toString();
                String idUser = User_id;


                final Response.Listener<String> Reg = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean sucsse = jsonObject.getBoolean("success");

                    if (sucsse) {
                        Toast.makeText(PhotoProfileUpdateActivity.this, R.string.Update_Success_Message, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(PhotoProfileUpdateActivity.this, R.string.Update_Error_Message, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(PhotoProfileUpdateActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        };
        Send_Data_Photo_Update_Profile send_data = new Send_Data_Photo_Update_Profile(idUser , getName, getEmail, getPhone, getPassword , getCamera , getSite, Reg);
        RequestQueue req = Volley.newRequestQueue(PhotoProfileUpdateActivity.this);
        req.add(send_data);

    }
});
    }

    public void Show_User_Details(){
        String NEWS_URL = "http://192.168.43.57/photo/photo_profile.php?email=" + User_Email;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NEWS_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("photos");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String name = object.getString("name");
                        String email = object.getString("email");
                        String phone = object.getString("phone");
                        String password = object.getString("password");
                        String camera = object.getString("camera");
                        String site = object.getString("site");
                        User_id = id;
                        UpdateName.setText(name);
                        UpdateEmail.setText(email);
                        UpdatePhone.setText(phone);
                        UpdatePassword.setText(password);
                        UpdateCamera.setText(camera);
                        UpdateSite.setText(site);

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
