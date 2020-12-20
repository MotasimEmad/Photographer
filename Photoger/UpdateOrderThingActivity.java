package com.example.motasim.photo.Photoger;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.DBLoginPhoto;
import com.example.motasim.photo.Lists_Items.List_Order_Type_Item;
import com.example.motasim.photo.R;
import com.example.motasim.photo.Send_Classes.Send_Data_New_Order_Type;
import com.example.motasim.photo.Send_Classes.Send_Data_Photo_Delete_Order_Type;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpdateOrderThingActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    GridView mGridViewUpdate;
    DBLoginPhoto mDBLoginPhoto;
    String Photo_Email, User_id, getName, getPrice;
    Dialog mDialog;
    ArrayList<List_Order_Type_Item> List_OrderType = new ArrayList<>();
    FloatingActionButton AddOrder, Add, UpdateOrder;
    EditText EditAddOrderName, EditAddOrderPrice, EditAddOrderNameUpdate, EditAddOrderPriceUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order_thing);

        mGridViewUpdate = findViewById(R.id.GridUpdateOrder);
        mDBLoginPhoto = new DBLoginPhoto(this);
        Photo_Email = mDBLoginPhoto.get_Email();

        mDialog = new Dialog(UpdateOrderThingActivity.this);
        mDialog.setContentView(R.layout.customorderdialog);

        mDialog.setTitle(R.string.Add_Order_Dialog_Message);

        EditAddOrderName = mDialog.findViewById(R.id.AddOrderName);
        EditAddOrderPrice = mDialog.findViewById(R.id.AddOrderPrice);
        AddOrder = mDialog.findViewById(R.id.AddOrder);


        Add = findViewById(R.id.BtnAddOrder);

        List_OrderType.clear();
        Show_OrderType();

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AddOrder.setEnabled(true);

                EditAddOrderName.setEnabled(true);
                EditAddOrderPrice.setEnabled(true);

                AddOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String getOrderNewName = EditAddOrderName.getText().toString();
                        String getOrderNewPrice = EditAddOrderPrice.getText().toString();
                        String EmailUser = Photo_Email;
                        final Response.Listener<String> new_order = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    boolean sucsse = jsonObject.getBoolean("success");

                                    if (sucsse) {
                                        List_OrderType.clear();
                                        Show_OrderType();
                                        Alerter.create(UpdateOrderThingActivity.this)
                                                .setTitle(R.string.Alert_Add_Order_Title)
                                                .setText(R.string.Alert_Add_Order_Text)
                                                .setDuration(5000)
                                                .setBackgroundColorRes(R.color.colorPrimaryDark)
                                                .show();
                                    } else {
                                        Toast.makeText(UpdateOrderThingActivity.this, R.string.Order_Things_Error_Message, Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(UpdateOrderThingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        };
                        Send_Data_New_Order_Type send_data = new Send_Data_New_Order_Type(EmailUser, getOrderNewName, getOrderNewPrice, new_order);
                        RequestQueue req = Volley.newRequestQueue(UpdateOrderThingActivity.this);
                        req.add(send_data);
                    }
                });
                mDialog.show();
            }
        });
    }


    public void listData() {
        UpdateOrderThingActivity.ListAdapter ad = new UpdateOrderThingActivity.ListAdapter(List_OrderType);
        mGridViewUpdate.setAdapter(ad);
    }

    class ListAdapter extends BaseAdapter {

        ArrayList<com.example.motasim.photo.Lists_Items.List_Order_Type_Item> List_Order_Type_Item;

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
            LayoutInflater layoutInflater = UpdateOrderThingActivity.this.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.custom_cardview_updateorderthing, null);
            final TextView name = view.findViewById(R.id.OrderName);
            final TextView price = view.findViewById(R.id.OrderPrice);
            FloatingActionButton Update = view.findViewById(R.id.btnUpdateOrder);
            FloatingActionButton Delete = view.findViewById(R.id.btnDeleteOrder);


            name.setText(List_Order_Type_Item.get(position).name);
            price.setText(List_Order_Type_Item.get(position).price);

            // Picasso.with(getApplicationContext()).load("http://192.168.43.57/said2/img/" + listitem.get(position).img).into(img);
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openCompany = new Intent(UpdateOrderThingActivity.this, PhotoUpdateOrderActivity.class);
                    openCompany.putExtra("id", List_Order_Type_Item.get(position).id);
                    openCompany.putExtra("name", List_Order_Type_Item.get(position).name);
                    openCompany.putExtra("price", List_Order_Type_Item.get(position).price);
                    startActivity(openCompany);
                }
            });

            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String IDUser = List_Order_Type_Item.get(position).id;
                    final Response.Listener<String> new_order = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                boolean sucsse = jsonObject.getBoolean("success");

                                if (sucsse) {
                                    List_OrderType.clear();
                                    Show_OrderType();
                                    Alerter.create(UpdateOrderThingActivity.this)
                                            .setTitle(R.string.Alert_Delete_Order_Title)
                                            .setText(R.string.Alert_Delete_Order_Text)
                                            .setDuration(5000)
                                            .setBackgroundColorRes(R.color.colorPrimaryDark)
                                            .show();

                                } else {
                                    Toast.makeText(UpdateOrderThingActivity.this, R.string.Order_Things_Error_Message, Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception e) {
                                Toast.makeText(UpdateOrderThingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    Send_Data_Photo_Delete_Order_Type send_data = new Send_Data_Photo_Delete_Order_Type(IDUser, new_order);
                    RequestQueue req = Volley.newRequestQueue(UpdateOrderThingActivity.this);
                    req.add(send_data);
                }
            });
            return view;
        }
    }

    public void Show_OrderType() {
        String NEWS_URL = "http://192.168.43.57/photo/show_order_type.php?photo_email=" + Photo_Email;
        requestQueue = Volley.newRequestQueue(UpdateOrderThingActivity.this);
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

                        EditAddOrderNameUpdate.setText(name);
                        EditAddOrderPriceUpdate.setText(price);
                        getPrice = price;

                        List_OrderType.add(new List_Order_Type_Item(id, name, price));
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
