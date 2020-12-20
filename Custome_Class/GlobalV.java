package com.example.motasim.photo.Custome_Class;

import android.app.Application;

/**
 * Created by Motasim on 10/28/2018.
 */

public class GlobalV extends Application {

    public String name;
    public String Email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
