package com.example.motasim.photo.Customer;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.DBLogin;
import com.example.motasim.photo.Lists_Items.List_Order_Item;
import com.example.motasim.photo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerNotificationActivity extends AppCompatActivity {

    DBLogin mDBLogin;
    String User_Email;
    RequestQueue requestQueue;
    ListView mListViewAllNotification;

    SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<List_Order_Item> List_AllNotification = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_notification);

        mSwipeRefreshLayout = findViewById(R.id.RefreshLayout);
        mListViewAllNotification = findViewById(R.id.ListALLNotification);
        Show_Notification();
        mDBLogin = new DBLogin(this);
        User_Email = mDBLogin.get_Email();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(CustomerNotificationActivity.this, "Refresh Layout", Toast.LENGTH_SHORT).show();
                List_AllNotification.clear();
                Show_Notification();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void listData() {
        CustomerNotificationActivity.ListAdapter ad = new CustomerNotificationActivity.ListAdapter(List_AllNotification);
        mListViewAllNotification.setAdapter(ad);
    }

    class ListAdapter extends BaseAdapter {

        ArrayList<List_Order_Item> List_Order_Item;

        ListAdapter(ArrayList<List_Order_Item> List_Order_Item) {
            this.List_Order_Item = List_Order_Item;
        }

        @Override
        public int getCount() {
            return List_Order_Item.size();
        }

        @Override
        public Object getItem(int position) {
            return List_Order_Item.get(position).customer_email;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = CustomerNotificationActivity.this.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.cardview_notification, null);
            TextView name = view.findViewById(R.id.CardUserName);



            name.setText(List_Order_Item.get(position).photo_email);
            // Picasso.with(getApplicationContext()).load("http://192.168.43.57/said2/img/" + listitem.get(position).img).into(img);

            return view;
        }
    }

    public void Show_Notification(){
        String NEWS_URL = "http://192.168.43.57/photo/showAllNotification.php?customer_email="+User_Email + "&state=yes";
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NEWS_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("AllNotification");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String customer_name = object.getString("customer_name");
                        String customer_email = object.getString("customer_email");
                        String order_city = object.getString("order_city");
                        String order_type = object.getString("order_type");
                        String order_date = object.getString("order_date");
                        String order_time = object.getString("order_time");
                        String order_price = object.getString("order_price");
                        String order_note = object.getString("order_note");
                        String photo_email = object.getString("photo_email");

                        List_AllNotification.add(new List_Order_Item(id ,customer_name ,customer_email ,order_city , order_type , order_date , order_time ,order_price , order_note, photo_email));
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
