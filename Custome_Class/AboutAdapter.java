package com.example.motasim.photo.Custome_Class;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.motasim.photo.Models.Model;
import com.example.motasim.photo.R;

import java.util.List;

public class AboutAdapter extends PagerAdapter {

    private List<Model> models;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public AboutAdapter(List<Model> models , Context mContext){
        this.models = models;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.card_item , container , false);
        ImageView mImageView;
        TextView mTitle , mDesc;

        mImageView = view.findViewById(R.id.Image);
        mTitle = view.findViewById(R.id.Title);
        mDesc = view.findViewById(R.id.Desc);

        mImageView.setImageResource(models.get(position).getImage());
        mTitle.setText(models.get(position).getTitle());
        mDesc.setText(models.get(position).getDesc());

        container.addView(view , 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
