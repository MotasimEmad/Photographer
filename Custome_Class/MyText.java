package com.example.motasim.photo.Custome_Class;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class MyText extends android.support.v7.widget.AppCompatTextView{

    public MyText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets() , "trado.ttf"));
    }
}
