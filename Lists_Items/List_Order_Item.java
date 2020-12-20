package com.example.motasim.photo.Lists_Items;

/**
 * Created by Motasim on 10/30/2018.
 */

public class List_Order_Item {

    public String id;
    public String customer_name;
    public String customer_email;
    public String order_city;
    public String order_type;
    public String order_date;
    public String order_time;
    public String order_price;
    public String order_note;
    public String photo_email;

    public List_Order_Item(String id, String customer_name, String customer_email, String order_city, String order_type, String order_date, String order_time, String order_price, String order_note, String photo_email) {
        this.id = id;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.order_city = order_city;
        this.order_type = order_type;
        this.order_date = order_date;
        this.order_time = order_time;
        this.order_price = order_price;
        this.order_note = order_note;
        this.photo_email = photo_email;
    }
}

