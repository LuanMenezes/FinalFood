package com.example.luan.food.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.luan.food.AddRatingActivity;
import com.example.luan.food.R;
import com.example.luan.food.domain.Rating;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class RatingAdapter  extends RealmBaseAdapter<Rating> implements ListAdapter {

    private Realm realm;

    public RatingAdapter(Context context, Realm realm, RealmResults<Rating> realmResults, boolean automaticUpdate ){
        super(context, realmResults, automaticUpdate);
        this.realm = realm;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder;

        if( convertView == null ){
            convertView = inflater.inflate(R.layout.item_rating, parent, false);
            holder = new CustomViewHolder();
            convertView.setTag( holder );

            holder.tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
            holder.tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
            holder.ivPicture = (ImageView) convertView.findViewById(R.id.ivPicture);
            holder.rbRating = (RatingBar) convertView.findViewById(R.id.rbRating);
        }
        else{
            holder = (CustomViewHolder) convertView.getTag();
        }

        final Rating rate = realmResults.get(position);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateFormatted = dateFormatter.format(rate.getCreatedAt());

        holder.tvCategory.setText(rate.getCategory());
        holder.tvCreatedAt.setText(dateFormatted);
        Bitmap imageBitMap = BitmapFactory.decodeFile(rate.getPicture());
        Bitmap resizedImageBitMap = Bitmap.createScaledBitmap(imageBitMap, 85, 85, true);
        imageBitMap.recycle();
        holder.ivPicture.setImageBitmap(resizedImageBitMap);
        holder.rbRating.setRating(rate.getRatingValue());

        return convertView;
    }

    private static class CustomViewHolder{
        RatingBar rbRating;
        TextView tvCategory;
        TextView tvCreatedAt;
        ImageView ivPicture;
    }

}