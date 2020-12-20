package com.example.motasim.photo.Photoger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.motasim.photo.DBLoginPhoto;
import com.example.motasim.photo.Lists_Items.List_Order_Item;
import com.example.motasim.photo.Custome_Class.MyButton;
import com.example.motasim.photo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AgreeOrderPhotoFragment extends Fragment {

    DBLoginPhoto mDBLoginPhoto;
    String User_Email;
    RequestQueue requestQueue;
    ListView mListViewAllOrder;
    TextView mTextView;

    SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<List_Order_Item> List_AllOrder = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_old_order_fragment, container , false);
        mDBLoginPhoto = new DBLoginPhoto(getContext());
        User_Email = mDBLoginPhoto.get_Email();
        mListViewAllOrder = view.findViewById(R.id.ListAllOrder2);
        mSwipeRefreshLayout = view.findViewById(R.id.RefreshLayout);
        mTextView = view.findViewById(R.id.PhotoText);


        if (List_AllOrder.isEmpty()){
            mTextView.setVisibility(View.VISIBLE);
            mListViewAllOrder.setVisibility(View.INVISIBLE);
        } else {
            mTextView.setVisibility(View.INVISIBLE);
            List_AllOrder.clear();
            Show_Photo();
        }


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List_AllOrder.clear();
                Show_Photo();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    public void listData() {
        AgreeOrderPhotoFragment.ListAdapter ad = new AgreeOrderPhotoFragment.ListAdapter(List_AllOrder);
        mListViewAllOrder.setAdapter(ad);
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
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.cardview_allorder, null);
            TextView name =  view.findViewById(R.id.CardUserName);
            TextView type = view.findViewById(R.id.CardUserType);
            MyButton button = view.findViewById(R.id.btn_Open);



            name.setText(List_Order_Item.get(position).customer_name);
            type.setText(List_Order_Item.get(position).order_type);
            // Picasso.with(getApplicationContext()).load("http://192.168.43.57/said2/img/" + listitem.get(position).img).into(img);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openCompany = new Intent(getActivity(), ShowAgreeOrder.class);
                    openCompany.putExtra("id", List_Order_Item.get(position).id);
                    openCompany.putExtra("name",List_Order_Item.get(position).customer_name);
                    openCompany.putExtra("email", List_Order_Item.get(position).customer_email);
                    openCompany.putExtra("city" , List_Order_Item.get(position).order_city);
                    openCompany.putExtra("type", List_Order_Item.get(position).order_type);
                    openCompany.putExtra("date" , List_Order_Item.get(position).order_date);
                    openCompany.putExtra("time" , List_Order_Item.get(position).order_time);
                    openCompany.putExtra("price" , List_Order_Item.get(position).order_price);
                    openCompany.putExtra("note" , List_Order_Item.get(position).order_note);
                    startActivity(openCompany);
                }
            });

            return view;
        }
    }

    public void Show_Photo(){
        String NEWS_URL = "http://192.168.43.57/photo/showPhotoOrder.php?photo_email=" + User_Email;
        requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NEWS_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("PhotoOrder");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String name = object.getString("customer_name");
                        String email = object.getString("customer_email");
                        String type = object.getString("order_type");
                        String city = object.getString("order_city");
                        String date = object.getString("order_date");
                        String time = object.getString("order_time");
                        String price = object.getString("order_price");
                        String note = object.getString("order_note");
                        String photo_email = object.getString("photo_email");

                        List_AllOrder.add(new List_Order_Item(id ,name , email , city , type , date , time ,price , note, photo_email));
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