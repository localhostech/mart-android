package com.localhostech.imagereq;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.yandex.metrica.YandexMetrica;

public class MyApplication extends Application {
    String YANDEX_API_KEY = "8a7c5e3e-b2df-496a-8360-4eb5c8becec0";

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Инициализация AppMetrica SDK
        YandexMetrica.activate(getApplicationContext(), YANDEX_API_KEY);
        // Отслеживание активности пользователей
        YandexMetrica.enableActivityAutoTracking(this);
        Log.i("MY_LOG", "AppMetrica is started!");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}