package com.example.motasim.photo.Customer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.Lists_Items.List_Photo_Item;
import com.example.motasim.photo.Custome_Class.MyButton;
import com.example.motasim.photo.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PhotoCustomerFragment extends Fragment {

    RequestQueue requestQueue;
    ListView mListViewPhoto;

    SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<List_Photo_Item> List_Photo = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_photo_fragment, container , false);

        mListViewPhoto = view.findViewById(R.id.ListPhoto);
        mSwipeRefreshLayout = view.findViewById(R.id.RefreshLayout);
        List_Photo.clear();
        Show_Photo();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List_Photo.clear();
                Show_Photo();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public void listData() {
        PhotoCustomerFragment.ListAdapter ad = new PhotoCustomerFragment.ListAdapter(List_Photo);
        mListViewPhoto.setAdapter(ad);
    }

    class ListAdapter extends BaseAdapter {

        ArrayList<List_Photo_Item> List_Photo_Item;

        ListAdapter(ArrayList<List_Photo_Item> List_Photo_Item) {
            this.List_Photo_Item = List_Photo_Item;
        }

        @Override
        public int getCount() {
            return List_Photo_Item.size();
        }

        @Override
        public Object getItem(int position) {
            return List_Photo_Item.get(position).name;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.cardview_custom, null);
            TextView name =  view.findViewById(R.id.CardUserName);
            CircleImageView circleImageView = view.findViewById(R.id.CardUserImg);
            MyButton button = view.findViewById(R.id.btnOpenProfile);



            name.setText(List_Photo_Item.get(position).name);
         //   Picasso.get(getApplicationContext()).load("http://192.168.43.57/said2/img/" + List_Photo_Item.get(position).img).into(img);
            Picasso.get().load("http://192.168.43.57/photo/img/" + List_Photo_Item.get(position).img).into(circleImageView);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openCompany = new Intent(getActivity(), PhotoProfileActivity.class);
                    openCompany.putExtra("id", List_Photo_Item.get(position).id);
                    openCompany.putExtra("name", List_Photo_Item.get(position).name);
                    openCompany.putExtra("email", List_Photo_Item.get(position).email);
                    openCompany.putExtra("camera" , List_Photo_Item.get(position).camera);
                    openCompany.putExtra("site" , List_Photo_Item.get(position).site);
                    openCompany.putExtra("phone" , List_Photo_Item.get(position).phone);
                    startActivity(openCompany);
                }
            });

            return view;
        }
    }

    public void Show_Photo(){
        String NEWS_URL = "http://192.168.43.57/photo/show_photo.php";
        requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NEWS_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("allphoto");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String id = object.getString("id");
                        String img = object.getString("img");
                        String name = object.getString("name");
                        String email = object.getString("email");
                        String camera = object.getString("camera");
                        String site = object.getString("site");
                        String phone = object.getString("phone");

                        List_Photo.add(new List_Photo_Item(id , img , name , email , camera , site , phone));
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
