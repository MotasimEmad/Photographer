package com.example.motasim.photo.Send_Classes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Motasim on 10/27/2018.
 */

public class Send_Data_Photo_Delete_Order_Type extends StringRequest {

    private static final String Send_data_url= "http://192.168.43.57/photo/delete_order_type.php";
    private Map<String,String> MapData;



    public Send_Data_Photo_Delete_Order_Type(String id, Response.Listener<String> listener) {
        super(Method.POST,Send_data_url, listener,null);
        MapData = new HashMap<>();
        MapData.put("ID" , id);
    }



    @Override
    protected Map<String, String> getParams()  {
        return MapData;
    }
}
