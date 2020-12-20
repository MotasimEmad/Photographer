package com.example.motasim.photo.Custome_Class;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class MyRadioButton extends RadioButton {

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets() , "trado.ttf"));
    }
}
