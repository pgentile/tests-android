package com.example.testsandroid.app;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class MeteoService extends IntentService {

    private static final String LOG_TAG = MeteoService.class.getSimpleName();

    public MeteoService() {
        super(MeteoService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, "Création du service");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(LOG_TAG, "Arrêt du service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
