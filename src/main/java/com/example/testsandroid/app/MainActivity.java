package com.example.testsandroid.app;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int NOTIF_ID = 1;

    private RatingBar ratingBar;

    private ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(LOG_TAG, "Creation");

        setContentView(R.layout.activity_main);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final TextView aboutText = (TextView) findViewById(R.id.aboutRating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                aboutText.setText("Note : " + rating + " / " + ratingBar.getNumStars());
            }
        });

        final Button openCameraButton = (Button) findViewById(R.id.openCameraButton);
        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        photoView = (ImageView) findViewById(R.id.photo);

        final Button notifButton = (Button) findViewById(R.id.notifButton);
        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG_TAG, "Envoi d'une notification");

                Notification notif = new Notification.Builder(view.getContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Notif...")
                        .setContentText("Contenu de la notif")
                        .build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIF_ID, notif);
            }
        });

        final Button showCardButton = (Button) findViewById(R.id.showCardButton);
        showCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CardActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(LOG_TAG, "Pause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(LOG_TAG, "Resume");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(LOG_TAG, "Stop");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(LOG_TAG, "Start");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(LOG_TAG, "Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(LOG_TAG, "Destroy");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "Restauration etat instance");

        ratingBar.setRating(savedInstanceState.getFloat("note"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(LOG_TAG, "Sauvegarde etat instance");

        outState.putFloat("note", ratingBar.getRating());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            final Bundle extras = data.getExtras();
            final Bitmap bitmap = (Bitmap) extras.get("data");
            photoView.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
