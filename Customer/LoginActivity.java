package com.example.motasim.photo.Customer;


import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.Custome_Class.GlobalV;
import com.example.motasim.photo.Custome_Class.MyCheckBox;
import com.example.motasim.photo.DBLogin;
import com.example.motasim.photo.Photoger.PhotoLoginActivity;
import com.example.motasim.photo.R;
import com.example.motasim.photo.Send_Classes.Send_Data_Login;

import org.json.JSONObject;

import br.com.bloder.magic.view.MagicButton;

public class LoginActivity extends AppCompatActivity {

    Button SignUp , Look , PhotoSign;
    MagicButton Login;
    MyCheckBox Remember_Me;
    GlobalV mGlobalV;
    DBLogin mDBLogin;
    String Check , save_Data;
    TextInputLayout mTextInputLayoutEmail;
    TextInputLayout mTextInputLayoutPassword;
    LinearLayout mLinearLayout;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignUp = findViewById(R.id.btnCreate);
        Look = findViewById(R.id.btnAbout);
        Login = findViewById(R.id.btnLogin);
        PhotoSign = findViewById(R.id.btnPhotoSign);
        Remember_Me = findViewById(R.id.CheckLogin);

        mTextInputLayoutEmail = findViewById(R.id.InputEmail);
        mTextInputLayoutPassword = findViewById(R.id.InputPassword);
        mProgressBar = findViewById(R.id.progressBar);

        mLinearLayout = findViewById(R.id.LoginLinearLayout);

        mGlobalV = (GlobalV)getApplicationContext();
        mDBLogin = new DBLogin(this);

        if(mDBLogin.get_Check() != null){
            Check = mDBLogin.get_Check();
            if(Check.equals("1")){
                mTextInputLayoutEmail.getEditText().setText(mDBLogin.get_Email());
                mTextInputLayoutPassword.getEditText().setText(mDBLogin.get_Password());
                Remember_Me.setChecked(true);
                save_Data = "1";

                startActivity(new Intent(LoginActivity.this , CustomerMainActivity.class));
                finish();
            } else{
                Remember_Me.setChecked(false);
                save_Data = "0";
            }
        }

        Look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LookPage = new Intent(LoginActivity.this , AboutActivity.class);
                startActivity(LookPage);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegPage = new Intent(LoginActivity.this , RegActivity.class);
                startActivity(RegPage);
            }
        });

        PhotoSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent PhotoSignPage = new Intent(LoginActivity.this , PhotoLoginActivity.class);
                startActivity(PhotoSignPage);
            }
        });


        Login.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ValidateEmail() |!ValidatePassword()){
                    return;
                } else {


                    mProgressBar.setVisibility(View.VISIBLE);
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

                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(LoginActivity.this , CustomerMainActivity.class));
                                    mDBLogin.update_Data_Login(Login_Email , Login_Password , save_Data);
                                }else
                                {
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(LoginActivity.this,R.string.Login_Error_Message,Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception e) {
                                mProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    Send_Data_Login send_data =  new Send_Data_Login (Login_Email,Login_Password,responsListener);
                    RequestQueue req= Volley.newRequestQueue(LoginActivity.this);
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
