package com.example.motasim.photo.Photoger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.motasim.photo.Custome_Class.GlobalV;
import com.example.motasim.photo.Custome_Class.MyButton;
import com.example.motasim.photo.Custome_Class.MyText;
import com.example.motasim.photo.DBLoginPhoto;
import com.example.motasim.photo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfilePhotoFragment extends Fragment {

    MyButton Update , Pics;
    GlobalV mGlobalV;
    MyText mProfileName , mProfileEmail , mProfilePhone , mProfileCamera , mProfileSite;
    String user_Email;
    RequestQueue requestQueue;
    DBLoginPhoto mDBLoginPhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_profile_fragment, container , false);

        Update = view.findViewById(R.id.btnOpenUpdate);
        Pics = view.findViewById(R.id.btnOpenPhotoPic);
        mGlobalV = (GlobalV) getActivity().getApplicationContext();
        mProfileName = view.findViewById(R.id.ProfileRowName);
        mProfileEmail = view.findViewById(R.id.ProfileRowEmail);
        mProfilePhone = view.findViewById(R.id.ProfileRowPhone);
        mProfileCamera = view.findViewById(R.id.ProfileRowCamera);
        mProfileSite = view.findViewById(R.id.ProfileRowSite);
        mGlobalV = (GlobalV) getActivity().getApplicationContext();
        mDBLoginPhoto = new DBLoginPhoto(getContext());
        user_Email = mDBLoginPhoto.get_Email();

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Update_Form = new Intent(getActivity() , PhotoProfileUpdateActivity.class);
                startActivity(Update_Form);
            }
        });
        Show_User_Details();
        return view;
    }

    public void Show_User_Details(){
        String NEWS_URL = "http://192.168.43.57/photo/photo_profile.php?email=" + user_Email;
        requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NEWS_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("photos");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString("name");
                        String email = object.getString("email");
                        String phone = object.getString("phone");
                        String camera = object.getString("camera");
                        String site = object.getString("site");
                        mProfileName.setText(name);
                        mProfileEmail.setText(email);
                        mProfilePhone.setText(phone);
                        mProfileCamera.setText(camera);
                        mProfileSite.setText(site);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

        });
        requestQueue.add(jsonObjectRequest);
    }
}
