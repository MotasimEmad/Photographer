package com.example.motasim.photo.Photoger;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.Custome_Class.MyCheckBox;
import com.example.motasim.photo.Customer.AboutActivity;
import com.example.motasim.photo.Customer.LoginActivity;
import com.example.motasim.photo.Custome_Class.MyButton;
import com.example.motasim.photo.DBLoginPhoto;
import com.example.motasim.photo.R;
import com.example.motasim.photo.Send_Classes.Send_Data_Login_Photo;

import org.json.JSONObject;

import br.com.bloder.magic.view.MagicButton;

public class PhotoLoginActivity extends AppCompatActivity {

    MyButton UserLogin , Look , BePhoto;
    MagicButton Login;
    MyCheckBox Remember_Me;
    DBLoginPhoto mDBLoginPhoto;
    String Check , save_Data;

    TextInputLayout mTextInputLayoutEmail , mTextInputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_login);

        UserLogin = findViewById(R.id.btnUserLogin);
        Look = findViewById(R.id.btnAbout);
        Login = findViewById(R.id.btnLogin);
        BePhoto = findViewById(R.id.btnBePhoto);
        mTextInputLayoutEmail = findViewById(R.id.InputEmail);
        mTextInputLayoutPassword = findViewById(R.id.InputPassword);
        Remember_Me = findViewById(R.id.CheckLoginPhoto);
        mDBLoginPhoto = new DBLoginPhoto(this);

        if(mDBLoginPhoto.get_Check() != null){
            Check = mDBLoginPhoto.get_Check();
            if(Check.equals("1")){
                mTextInputLayoutEmail.getEditText().setText(mDBLoginPhoto.get_Email());
                mTextInputLayoutPassword.getEditText().setText(mDBLoginPhoto.get_Password());
                Remember_Me.setChecked(true);
                save_Data = "1";

                startActivity(new Intent(PhotoLoginActivity.this , PhotoMainActivity.class));
                finish();
            } else{
                Remember_Me.setChecked(false);
                save_Data = "0";
            }
        }


        UserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UserSignPage = new Intent(PhotoLoginActivity.this , LoginActivity.class);
                startActivity(UserSignPage);
            }
        });

        Look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LookPage = new Intent(PhotoLoginActivity.this , AboutActivity.class);
                startActivity(LookPage);
            }
        });

        Login.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ValidateEmail() |!ValidatePassword()){
                    return;
                } else {
                    final String Login_Email = mTextInputLayoutEmail.getEditText().getText().toString().trim();
                    final String Login_Password = mTextInputLayoutPassword.getEditText().getText().toString().trim();

                    Response.Listener<String> responsListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {

                            try
                            {
                                JSONObject jsonObject = new JSONObject(s);
                                boolean sucsse = jsonObject.getBoolean("success");

                                if (sucsse)
                                {
                                    Intent mLogingIn = new Intent(PhotoLoginActivity.this , PhotoMainActivity.class);
                                    startActivity(mLogingIn);


                                    mDBLoginPhoto.update_Data_Login(Login_Email , Login_Password , save_Data);


                                    Toast.makeText(PhotoLoginActivity.this,R.string.Login_Success_Message, Toast.LENGTH_LONG).show();

                                }else
                                {
                                    Toast.makeText(PhotoLoginActivity.this,R.string.Login_Error_Message,Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception e) {
                                //e.printStackTrace();
                                Toast.makeText(PhotoLoginActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    Send_Data_Login_Photo send_data =  new Send_Data_Login_Photo (Login_Email,Login_Password,responsListener);
                    RequestQueue req= Volley.newRequestQueue(PhotoLoginActivity.this);
                    req.add(send_data);
                }
            }
        });
    }

    private boolean ValidateEmail(){
        String get_Email = mTextInputLayoutEmail.getEditText().getText().toString().trim();
        if (get_Email.isEmpty()){
            mTextInputLayoutEmail.setError(String.valueOf(R.string.ValidError));
            return false;
        } else {
            mTextInputLayoutEmail.setError(null);
            return true;
        }
    }

    private boolean ValidatePassword(){
        String get_Password = mTextInputLayoutPassword.getEditText().getText().toString().trim();
        if (get_Password.isEmpty()){
            mTextInputLayoutPassword.setError(String.valueOf(R.string.ValidError));
            return false;
        } else {
            mTextInputLayoutPassword.setError(null);
            return true;
        }
    }
}
