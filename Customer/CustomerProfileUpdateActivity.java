package com.example.motasim.photo.Customer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.Custome_Class.GlobalV;
import com.example.motasim.photo.Custome_Class.MyButton;
import com.example.motasim.photo.DBLogin;
import com.example.motasim.photo.R;
import com.example.motasim.photo.Send_Classes.Send_Data_Customer_Update_Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class CustomerProfileUpdateActivity extends AppCompatActivity {

    MyButton Update , ChangePic;
    EditText UpdateName, UpdateEmail, UpdatePhone , UpdatePassword;
    GlobalV mGlobalV;
    String User_Email , User_id , Encoding;
    RequestQueue requestQueue;
    DBLogin mDBLogin;
    ProgressBar mProgressBar;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile_update);

        Update =  findViewById(R.id.btn_update);
        ChangePic = findViewById(R.id.btn_updatePic);
        mImageView = findViewById(R.id.UpdateImageView);
        UpdateName =  findViewById(R.id.EditTextUpdateName);
        UpdateEmail =  findViewById(R.id.EditTextUpdateEmail);
        UpdatePhone =  findViewById(R.id.EditTextUpdatePhone);
        UpdatePassword = findViewById(R.id.EditTextUpdatePassword);
        mGlobalV = (GlobalV) getApplicationContext();
        mDBLogin = new DBLogin(this);
        User_Email = mDBLogin.get_Email();
        mProgressBar = findViewById(R.id.progressBarCustomerUpdate);
        mProgressBar.setVisibility(View.INVISIBLE);


        Show_User_Details();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap mBitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.JPEG , 90 , mByteArrayOutputStream);
                Encoding = Base64.encodeToString(mByteArrayOutputStream.toByteArray() , Base64.DEFAULT);

                String getName = UpdateName.getText().toString();
                String getEmail = UpdateEmail.getText().toString();
                String getPhone = UpdatePhone.getText().toString();
                String getPassword = UpdatePassword.getText().toString();
                String idUser = User_id;

                mProgressBar.setVisibility(View.VISIBLE);
                Update.setVisibility(View.INVISIBLE);


                final Response.Listener<String> Reg = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            boolean sucsse = jsonObject.getBoolean("success");

                            if (sucsse) {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                finish();
                            } else {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                Update.setVisibility(View.VISIBLE);
                                Toast.makeText(CustomerProfileUpdateActivity.this, R.string.Customer_Profile_Update_Error_Message, Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(CustomerProfileUpdateActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                };
                Send_Data_Customer_Update_Profile send_data = new Send_Data_Customer_Update_Profile(idUser ,Encoding, getName, getEmail, getPhone, getPassword , Reg);
                RequestQueue req = Volley.newRequestQueue(CustomerProfileUpdateActivity.this);
                req.add(send_data);

            }
        });
    }

    public void Show_User_Details(){
        String NEWS_URL = "http://192.168.43.57/photo/users_profile.php?email=" + User_Email;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NEWS_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("users");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String name = object.getString("name");
                        String email = object.getString("email");
                        String phone = object.getString("phone");
                        String password = object.getString("password");
                        User_id = id;
                        UpdateName.setText(name);
                        UpdateEmail.setText(email);
                        UpdatePhone.setText(phone);
                        UpdatePassword.setText(password);

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

    public void Change_Pic(View view){
        Intent Change_Pic = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Change_Pic , 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            mImageView.setImageURI(uri);
        }
    }
}


