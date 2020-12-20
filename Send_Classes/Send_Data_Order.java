package com.example.motasim.photo.Send_Classes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Send_Data_Order extends StringRequest {

    private static final String Send_data_url= "http://192.168.43.57/photo/photo_order.php";
    private Map<String,String> MapData;

    public Send_Data_Order(
                           String customer_name ,
                           String customer_email ,
                           String city ,
                           String address, String date ,
                           String time,
                           String price ,
                           String email ,
                           String order_note , Response.Listener<String> listener) {
        super(Method.POST,Send_data_url, listener,null);

        MapData = new HashMap<>();
        MapData.put("customer_name",customer_name);
        MapData.put("customer_email",customer_email);
        MapData.put("city",city);
        MapData.put("address",address);
        MapData.put("date",date);
        MapData.put("time",time);
        MapData.put("price",price);
        MapData.put("email",email);
        MapData.put("note",order_note);

    }

    @Override
    protected Map<String, String> getParams()  {
        return MapData;
    }
}
