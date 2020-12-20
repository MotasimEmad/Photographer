package com.example.motasim.photo.Customer;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.Custome_Class.MyButton;
import com.example.motasim.photo.Custome_Class.MyCheckBox;
import com.example.motasim.photo.Custome_Class.MyText;
import com.example.motasim.photo.DBLogin;
import com.example.motasim.photo.R;
import com.example.motasim.photo.Send_Classes.Send_Data_Regrestration;

import org.json.JSONObject;

import br.com.bloder.magic.view.MagicButton;

public class RegActivity extends AppCompatActivity {

    MyButton Already, About;

    TextInputLayout mTextInputLayoutName, mTextInputLayoutEmail, mTextInputLayoutPassword, mTextInputLayoutRePassword;

    MagicButton Create;
    CheckBox Polices;

    TextView PolicesText;

    DBLogin mDBLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        Already =  findViewById(R.id.btnAlready);
        About =  findViewById(R.id.btnAbout);

        mDBLogin = new DBLogin(this);

        mTextInputLayoutName =  findViewById(R.id.InputName);
        mTextInputLayoutEmail =  findViewById(R.id.InputEmail);
        mTextInputLayoutPassword =  findViewById(R.id.InputPassword);
        mTextInputLayoutRePassword =  findViewById(R.id.InputRePassword);

        Create =  findViewById(R.id.btnLogin);
        Polices =  findViewById(R.id.CheckPolices);

        PolicesText = findViewById(R.id.PolicesText);

        Already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginPage = new Intent(RegActivity.this, LoginActivity.class);
                startActivity(LoginPage);
            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LookPage = new Intent(RegActivity.this, AboutActivity.class);
                startActivity(LookPage);
            }
        });

        PolicesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent PolicesPage = new Intent(RegActivity.this, PolicesActivity.class);
                startActivity(PolicesPage);
            }
        });

        Create.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!ValidateName() | !ValidateEmail() | !ValidatePassword() | !ValidateRePassword()){
                    return;
                } else {

                    String getName = mTextInputLayoutName.getEditText().getText().toString();
                    final String getEmail = mTextInputLayoutEmail.getEditText().getText().toString();
                    final String getPassword = mTextInputLayoutPassword.getEditText().getText().toString();
                    String getRePassword = mTextInputLayoutRePassword.getEditText().getText().toString();

                    if (!getPassword.equals(getRePassword)) {
                        Toast.makeText(RegActivity.this, R.string.Reg_Password_Message, Toast.LENGTH_SHORT).show();
                    } else if (!Polices.isChecked()) {
                        Toast.makeText(RegActivity.this, R.string.Reg_Polices_Message, Toast.LENGTH_SHORT).show();
                    } else {

                        final Response.Listener<String> Reg = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {

                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    boolean sucsse = jsonObject.getBoolean("success");

                                    if (sucsse) {
                                        Toast.makeText(RegActivity.this, R.string.Reg_Success_Message, Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(RegActivity.this, CustomerMainActivity.class));
                                    } else {
                                        Toast.makeText(RegActivity.this, R.string.Reg_Error_Message, Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(RegActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        Send_Data_Regrestration send_data = new Send_Data_Regrestration(getName, getEmail, getPassword, Reg);
                        RequestQueue req = Volley.newRequestQueue(RegActivity.this);
                        req.add(send_data);
                    }
                }
            }
        });
    }

    private boolean ValidateName(){
        String get_Name = mTextInputLayoutName.getEditText().getText().toString().trim();
        if (get_Name.isEmpty()){
            mTextInputLayoutName.setError(String.valueOf(R.string.ValidError));
            return false;
        } else {
            mTextInputLayoutName.setError(null);
            return true;
        }
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

    private boolean ValidateRePassword(){
        String get_RePassword = mTextInputLayoutRePassword.getEditText().getText().toString().trim();
        if (get_RePassword.isEmpty()){
            mTextInputLayoutRePassword.setError(String.valueOf(R.string.ValidError));
            return false;
        } else {
            mTextInputLayoutRePassword.setError(null);
            return true;
        }
    }
}
