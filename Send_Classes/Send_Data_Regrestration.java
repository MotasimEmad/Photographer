package com.example.motasim.photo.Send_Classes;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Motasim on 10/27/2018.
 */

public class Send_Data_Regrestration extends StringRequest {

    private static final String Send_data_url= "http://192.168.43.57/photo/req.php";
    private Map<String,String> MapData;



    public Send_Data_Regrestration(String name , String email, String pass , Response.Listener<String> listener) {
        super(Method.POST,Send_data_url, listener,null);
        MapData = new HashMap<>();
        MapData.put("Name",name);
        MapData.put("Email",email);
        MapData.put("Password",pass);

    }

    @Override
    protected Map<String, String> getParams()  {
        return MapData;
    }
}
