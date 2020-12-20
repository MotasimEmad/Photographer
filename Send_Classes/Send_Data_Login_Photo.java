package com.example.motasim.photo.Send_Classes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Send_Data_Login_Photo extends StringRequest {

    private static final String Send_data_url= "http://192.168.43.57/photo/photo_login.php";
    private Map<String,String> MapData;



    public Send_Data_Login_Photo(String Name , String Pass , Response.Listener<String> listener) {
        super(Method.POST,Send_data_url, listener,null);
        MapData = new HashMap<>();
        MapData.put("Login_Email",Name);
        MapData.put("Login_Password",Pass);

    }



    @Override
    protected Map<String, String> getParams()  {
        return MapData;
    }
}
