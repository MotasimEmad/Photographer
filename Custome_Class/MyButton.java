package com.example.motasim.photo.Custome_Class;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButton extends Button{
    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets() , "trado.ttf"));
    }
}
