package com.example.luan.food;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.luan.food.domain.Rating;

import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm realm = Realm.getDefaultInstance();

        init( realm );

        RealmResults<Rating> ratings = realm.where(Rating.class).findAll();

        realm.close();
    }

    private void init( Realm realm ){
        SharedPreferences sp = getPreferences(MODE_PRIVATE);

        if( sp.getInt("flag", 0) == 0 ){
            Log.i("LOG", "init()");
            sp.edit().putInt("flag", 1).apply();

            try{
                AssetManager assetManager = getAssets();
                InputStream is = null;

                realm.beginTransaction();

                is = assetManager.open("rating.json");
                realm.createOrUpdateAllFromJson(Rating.class, is);

                realm.commitTransaction();

            }
            catch( Exception e ){
                e.printStackTrace();
                realm.cancelTransaction();
            }
        }
    }

    public void callRating( View view){
        Log.i("LOG", "ENTROU CALL RATING");
        Intent it = new Intent(this, AddRatingActivity.class);
        startActivity(it);
    }
}
