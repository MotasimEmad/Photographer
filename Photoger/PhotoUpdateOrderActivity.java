package com.example.motasim.photo.Photoger;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.R;
import com.example.motasim.photo.Send_Classes.Send_Data_Photo_Update_Order_Type;

import org.json.JSONObject;

public class PhotoUpdateOrderActivity extends AppCompatActivity {

    FloatingActionButton Update , Back;
    EditText UpdateName, UpdatePrice;
    String User_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_update_order);

        Update = findViewById(R.id.btnUpdateOrder);
        Back = findViewById(R.id.btnUpdateOrderBack);

        UpdateName = findViewById(R.id.EditTextUpdateOrderName);
        UpdatePrice = findViewById(R.id.EditTextUpdateOrderPrice);;

        Intent Company_GetData = getIntent();
        User_id = Company_GetData.getExtras().getString("id");
        UpdateName.setText(Company_GetData.getExtras().getString("name"));
        UpdatePrice.setText(Company_GetData.getExtras().getString("price"));

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String IDUser = User_id;
                String Name = UpdateName.getText().toString();
                String Price = UpdatePrice.getText().toString();
                final Response.Listener<String> Reg = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            boolean sucsse = jsonObject.getBoolean("success");

                            if (sucsse) {
                                Toast.makeText(PhotoUpdateOrderActivity.this, R.string.Update_Success_Message, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(PhotoUpdateOrderActivity.this, R.string.Update_Error_Message, Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(PhotoUpdateOrderActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                };
                Send_Data_Photo_Update_Order_Type send_data = new Send_Data_Photo_Update_Order_Type(IDUser , Name, Price, Reg);
                RequestQueue req = Volley.newRequestQueue(PhotoUpdateOrderActivity.this);
                req.add(send_data);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
