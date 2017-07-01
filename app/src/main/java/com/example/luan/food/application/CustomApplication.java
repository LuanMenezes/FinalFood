package com.example.luan.food.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Luan on 30/06/2017.
 */

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("realm-food.realm")
                .build();

        Realm.setDefaultConfiguration( realmConfiguration );
    }
}
