package com.example.motasim.photo.Send_Classes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Send_Data_New_Order_Type extends StringRequest {

    private static final String Send_data_url= "http://192.168.43.57/photo/Add_new_order_type.php";
    private Map<String,String> MapData;

    public Send_Data_New_Order_Type(String Photo_Email , String Name , String Price , Response.Listener<String> listener) {
        super(Method.POST,Send_data_url, listener,null);
        MapData = new HashMap<>();
        MapData.put("Photo_Email" , Photo_Email);
        MapData.put("Order_Name",Name);
        MapData.put("Order_Price",Price);

    }



    @Override
    protected Map<String, String> getParams()  {
        return MapData;
    }
}
