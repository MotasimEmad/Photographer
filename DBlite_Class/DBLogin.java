package com.example.motasim.photo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBLogin  extends SQLiteOpenHelper {

    public static final String DBName = "mdatabase.db";
    public DBLogin(Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table my_table (id INTEGER PRIMARY KEY AUTOINCREMENT , email TEXT , password TEXT , save_data TEXT)");
        ContentValues mContentValues = new ContentValues();
        mContentValues.put("email" , "null");
        mContentValues.put("password" , "null");
        mContentValues.put("save_data" , "0");

        db.insert("my_table", null , mContentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS my_table");
        onCreate(db);
    }


    public String get_Email(){
        String Email;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("select email FROM my_table" , null);
        mCursor.moveToFirst();
        Email = mCursor.getString(mCursor.getColumnIndex("email"));
        return Email;
    }

    public String get_Password(){
        String Password;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("select password FROM my_table" , null);
        mCursor.moveToFirst();
        Password = mCursor.getString(mCursor.getColumnIndex("password"));
        return Password;
    }

    public String get_Check(){
        String Check;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("select save_data FROM my_table" , null);
        mCursor.moveToFirst();
        Check = mCursor.getString(mCursor.getColumnIndex("save_data"));
        return Check;
    }


    public boolean update_Data_Login(String email , String password , String check_save_data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues mContentValues = new ContentValues();
        mContentValues.put("email" , email);
        mContentValues.put("password" , password);
        mContentValues.put("save_data" , check_save_data);

        db.update("my_table" , mContentValues , "id= ?" , new String[]{"1"});
        return true;
    }
    public boolean update_Data_Logout(String email , String password , String check_save_data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues mContentValues = new ContentValues();
        mContentValues.put("email" , "null");
        mContentValues.put("password" , "null");
        mContentValues.put("save_data" , "0");

        db.update("my_table" , mContentValues , "id= ?" , new String[]{"1"});
        return true;
    }


    public boolean update_Data_Req(String email , String password , String check_save_data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues mContentValues = new ContentValues();
        mContentValues.put("email" , email);
        mContentValues.put("password" , password);
        mContentValues.put("save_data" , check_save_data);

        db.update("my_table" , mContentValues , "id= ?" , new String[]{"1"});
        return true;
    }
}
