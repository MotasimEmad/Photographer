package com.example.motasim.photo.Customer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.motasim.photo.Custome_Class.MyText;
import com.example.motasim.photo.Lists_Items.List_Order_Type_Item;
import com.example.motasim.photo.R;


public class PhotoProfileActivity extends AppCompatActivity {

    MyText mMyTextName , mMyTextEmail , mMyTextCamera , mMyTextPhone , mMyTextSite;
    Button Contact;

    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_profile);

        mMyTextName = findViewById(R.id.PhotoProfileName);
        mMyTextEmail = findViewById(R.id.PhotoProfileEmail);
        mMyTextCamera = findViewById(R.id.PhotoProfileCamera);
        mMyTextPhone = findViewById(R.id.PhotoProfilePhone);
        mMyTextSite = findViewById(R.id.PhotoProfileSite);

        Intent Company_GetData = getIntent();
        mMyTextName.setText(Company_GetData.getExtras().getString("name"));
        mMyTextEmail.setText(Company_GetData.getExtras().getString("email"));
        mMyTextCamera.setText(Company_GetData.getExtras().getString("camera"));
        mMyTextPhone.setText(Company_GetData.getExtras().getString("phone"));
        mMyTextSite.setText(Company_GetData.getExtras().getString("site"));

        email = mMyTextEmail.getText().toString();

        Contact = findViewById(R.id.btnContent);
        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoProfileActivity.this , ContactActivity.class);
                startActivity(intent);
            }
        });

        mMyTextEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent SendEmail = new Intent(Intent.ACTION_SEND);
                    SendEmail.setData(Uri.parse("mailto"));
                    SendEmail.setType("message/rfc822");
                    SendEmail.putExtra(Intent.EXTRA_EMAIL,""+ mMyTextEmail.getText().toString());
                    SendEmail.putExtra(Intent.EXTRA_SUBJECT , R.string.Photo_Profile_Email_Message_Title);
                    SendEmail.putExtra(Intent.EXTRA_TEXT , R.string.Photo_Profile_Email_Message_Text);
                    startActivity(SendEmail);

                }catch (Exception e){
                    Toast.makeText(PhotoProfileActivity.this, "SomeThing Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mMyTextSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent WebSite = new Intent(Intent.ACTION_VIEW);
                WebSite.setData(Uri.parse("http://" +mMyTextSite.getText().toString()));

                if (WebSite.resolveActivity(getPackageManager()) != null){
                    startActivity(WebSite);
                }
            }
        });
    }
}
