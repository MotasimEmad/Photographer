package com.example.motasim.photo.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.motasim.photo.DBLogin;
import com.example.motasim.photo.R;

public class MoreCustomerFragment extends Fragment {

    ListView List_More;
    String items [] = {String.valueOf(R.string.More_Fragment_AppVersion), String.valueOf(R.string.More_Fragment_AboutDeveloper) , String.valueOf(R.string.More_Fragment_LogOut)};
    DBLogin mDBLogin;
    String user_Email , user_Password , check;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_more_fragment, container , false);

        List_More = view.findViewById(R.id.ListMore);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(getActivity() , android.R.layout.simple_list_item_1 , items);
        List_More.setAdapter(Adapter);
        mDBLogin = new DBLogin(getContext());
        user_Email = mDBLogin.get_Email();
        user_Password = mDBLogin.get_Password();
        check = mDBLogin.get_Check();

        List_More.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent MoreIntent = new Intent(view.getContext() , VersionAppActivity.class);
                    startActivityForResult(MoreIntent , 0);
                }

                if(position == 1){
                    Intent MoreIntent = new Intent(view.getContext() , AboutDevActivity.class);
                    startActivityForResult(MoreIntent , 1);
                }

                if(position == 2){
                    Intent MoreIntent = new Intent(view.getContext() , LoginActivity.class);
                    startActivityForResult(MoreIntent , 2);
                    mDBLogin.update_Data_Logout(user_Email , user_Password , check);
                    getActivity().finish();
                }

            }
        });

        return view;
    }
}
