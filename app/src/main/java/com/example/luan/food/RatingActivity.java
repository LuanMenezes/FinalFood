package com.example.luan.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.luan.food.adapter.RatingAdapter;
import com.example.luan.food.domain.Rating;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class RatingActivity extends AppCompatActivity {

    private Realm realm;
    private RealmResults<Rating> ratings;
    private RealmChangeListener realmChangeListener;
    private ListView lvRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        realm = Realm.getDefaultInstance();

        lvRating = (ListView) findViewById(R.id.lv_rating);

        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                ((RatingAdapter) lvRating.getAdapter()).notifyDataSetChanged();
            }
        };

        realm.addChangeListener(realmChangeListener);
        ratings = realm.where( Rating.class ).findAll();

        lvRating.setAdapter( new RatingAdapter( this, realm, ratings, false ));

//        lvRating = (ListView) findViewById(R.id.lv_rating);
//        lvRating.setAdapter( new RatingAdapter( this, realm, ratings, false ));
    }

    @Override
    protected void onDestroy() {
        realm.removeAllChangeListeners();
        realm.close();
        super.onDestroy();

    }

    public void callAddRating( View view){
        Intent it = new Intent( this, AddRatingActivity.class );
        startActivity(it);
    }
}