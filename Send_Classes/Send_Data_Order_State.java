package com.example.motasim.photo.Send_Classes;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class Send_Data_Order_State extends StringRequest {

    private static final String Send_data_url= "http://192.168.43.57/photo/order_state_update.php";
    private Map<String,String> MapData;

    public Send_Data_Order_State(String id , String state, Response.Listener<String> listener) {
        super(Method.POST,Send_data_url, listener,null);

        MapData = new HashMap<>();
        MapData.put("ID",id);
        MapData.put("State",state);
    }

    @Override
    protected Map<String, String> getParams()  {
        return MapData;
    }
}
