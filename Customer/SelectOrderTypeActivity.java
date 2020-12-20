package com.example.motasim.photo.Customer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.DBLogin;
import com.example.motasim.photo.Lists_Items.List_Order_Type_Item;
import com.example.motasim.photo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.ListResourceBundle;

public class SelectOrderTypeActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    GridView mGridViewPhoto;
    String Photo_Email;


    ArrayList<List_Order_Type_Item> List_OrderType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order_type);

        mGridViewPhoto = findViewById(R.id.GridOrderType);
        Photo_Email = PhotoProfileActivity.email;

        Show_OrderType();
    }

    public void listData() {
        SelectOrderTypeActivity.ListAdapter ad = new SelectOrderTypeActivity.ListAdapter(List_OrderType);
        mGridViewPhoto.setAdapter(ad);
    }

    class ListAdapter extends BaseAdapter {

        ArrayList<List_Order_Type_Item> List_Order_Type_Item;

        ListAdapter(ArrayList<List_Order_Type_Item> List_Order_Type_Item) {
            this.List_Order_Type_Item = List_Order_Type_Item;
        }

        @Override
        public int getCount() {
            return List_Order_Type_Item.size();
        }

        @Override
        public Object getItem(int position) {
            return List_Order_Type_Item.get(position).name;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = SelectOrderTypeActivity.this.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.custom_cardview_ordertype, null);
            TextView name =  view.findViewById(R.id.OrderName);
            TextView price =  view.findViewById(R.id.OrderPrice);
            final CardView Card = view.findViewById(R.id.CardViewOrder);




            name.setText(List_Order_Type_Item.get(position).name);
            price.setText(List_Order_Type_Item.get(position).price);
            // Picasso.with(getApplicationContext()).load("http://192.168.43.57/said2/img/" + listitem.get(position).img).into(img);

            Card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openCompany = new Intent(SelectOrderTypeActivity.this, ContactActivity.class);
                    openCompany.putExtra("id", List_Order_Type_Item.get(position).id);
                    openCompany.putExtra("name", List_Order_Type_Item.get(position).name);
                    openCompany.putExtra("price", List_Order_Type_Item.get(position).price);
                    startActivity(openCompany);
                    finish();
                }
            });

            return view;
        }
    }

    public void Show_OrderType(){
        String NEWS_URL = "http://192.168.43.57/photo/show_order_type.php?photo_email=" + Photo_Email;
        requestQueue = Volley.newRequestQueue(SelectOrderTypeActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NEWS_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("ordertype");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String name = object.getString("name");
                        String price = object.getString("price");
                        String photo_email = object.getString("photo_email");

                        List_OrderType.add(new List_Order_Type_Item(id , name , price));
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
