package com.example.motasim.photo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.Customer.ContactActivity;
import com.example.motasim.photo.Customer.PhotoProfileActivity;
import com.example.motasim.photo.Customer.SelectOrderTypeActivity;
import com.example.motasim.photo.Lists_Items.List_Img_Item;
import com.example.motasim.photo.Lists_Items.List_Order_Type_Item;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhotoPicActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    GridView mGridViewPhotoPic;
    String Photo_Email;

    ArrayList<List_Img_Item> List_Photo_Img = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_pic);

        mGridViewPhotoPic = findViewById(R.id.GridOrderType);
        Photo_Email = PhotoProfileActivity.email;


    }

    public void listData() {
        PhotoPicActivity.ListAdapter ad = new PhotoPicActivity.ListAdapter(List_Photo_Img);
        mGridViewPhotoPic.setAdapter(ad);
    }

    class ListAdapter extends BaseAdapter {

        ArrayList<List_Img_Item> List_Img_Item;

        ListAdapter(ArrayList<List_Img_Item> List_Img_Item) {
            this.List_Img_Item = List_Img_Item;
        }

        @Override
        public int getCount() {
            return List_Img_Item.size();
        }

        @Override
        public Object getItem(int position) {
            return List_Img_Item.get(position).img;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = PhotoPicActivity.this.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.custom_imgview_pic, null);
            ImageView img =  view.findViewById(R.id.ImgPhotoPic);

           // Picasso.with(PhotoPicActivity.this).load("http://192.168.43.57/photo/"+ List_Img_Item.get(position).img).into(img);

            return view;
        }
    }

    public void Show_OrderType(){
        String NEWS_URL = "http://192.168.43.57/photo/show_photo_img.php?photo_email=" + Photo_Email;
        requestQueue = Volley.newRequestQueue(PhotoPicActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NEWS_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("photoimg");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String name = object.getString("img");

                        List_Photo_Img.add(new List_Img_Item(id , name));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

        });
        requestQueue.add(jsonObjectRequest);
    }
}
