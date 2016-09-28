package com.example.testsandroid.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int NOTIF_ID = 1;

    private final EventBus eventBus = EventBus.getDefault();

    private ImageView photoView;

    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventBus.register(this);

        Log.i(LOG_TAG, "Creation");

        setContentView(R.layout.activity_main);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Accueil");
        //toolbar.setSubtitle("Accueil de l'appli");
        toolbar.setNavigationIcon(R.drawable.ic_launcher);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.test_button:
                        Log.i(LOG_TAG, "Menu - Test");
                        Toast.makeText(getApplicationContext(), "Menu sélectionné : Test", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_settings:
                        Log.i(LOG_TAG, "Menu - Settings");
                        Toast.makeText(getApplicationContext(), "Menu sélectionné : Settings", Toast.LENGTH_LONG).show();

                        final Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(intent);

                        break;
                    case R.id.show_list_view:

                        final Intent intent2 = new Intent(getApplicationContext(), ListActivity.class);
                        startActivity(intent2);

                        break;
                    default:
                        Log.w(LOG_TAG, "Menu - Action non reconnue");
                }
                return true;
            }
        });

        toolbar.inflateMenu(R.menu.menu_main);

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
        photoView.setDrawingCacheEnabled(true);

        final Button notifButton = (Button) findViewById(R.id.notifButton);
        notifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG_TAG, "Envoi d'une notification");

                final Intent notifIntent = new Intent(MainActivity.this, MainActivity.class);

                final TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(MainActivity.this)
                        .addParentStack(MainActivity.this)
                        .addNextIntent(notifIntent);

                final PendingIntent contentIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                final Notification notif = new NotificationCompat.Builder(view.getContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Notif...")
                        .setContentText("Contenu de la notif")
                        .setContentIntent(contentIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .build();
                final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIF_ID, notif);

            }
        });

        final Button showCardButton = (Button) findViewById(R.id.showCardButton);
        showCardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(view.getContext(), CardActivity.class);
                startActivity(intent);
            }

        });

        final Button vibrateButton = (Button) findViewById(R.id.vibrateButton);
        vibrateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator.hasVibrator()) {
                    vibrator.vibrate(TimeUnit.SECONDS.toMillis(1));
                }
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

        eventBus.unregister(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i(LOG_TAG, "On new Intent");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i(LOG_TAG, "Restauration etat instance");

        ratingBar.setRating(savedInstanceState.getFloat("note"));

        try {
            try (FileInputStream photoOutputStream = openFileInput("photo.png")) {
                final Bitmap photoBitmap = BitmapFactory.decodeStream(photoOutputStream);
                if (photoBitmap == null) {
                    Log.e(LOG_TAG, "Impossible de décoder la photo sauvegardée");
                } else {
                    photoView.setImageBitmap(photoBitmap);
                }
            }

        } catch (final IOException e) {
            Log.i(LOG_TAG, "Restauration impossible : " + e.getMessage(), e);
        }
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
            final Bitmap intentBitmap = (Bitmap) extras.get("data");

            eventBus.post(new PhotoCapturedEvent(intentBitmap));
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onPhotoCaptured(PhotoCapturedEvent event) {
        try {
            final Bitmap photoBitmap = event.getPhotoBitmap();
            try (FileOutputStream photoOutputStream = openFileOutput("photo.png", Context.MODE_PRIVATE)) {
                if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, photoOutputStream)) {
                    eventBus.post(new PhotoSavedEvent(photoBitmap));
                } else {
                    Log.e(LOG_TAG, "Sauvegarde au format PNG impossible");
                }
            }
        } catch (final IOException e) {
            Log.e(LOG_TAG, "Sauvegarde impossible : " + e.getMessage(), e);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPhotoSaved(PhotoSavedEvent event) {
        photoView.setImageBitmap(event.getPhotoBitmap());
    }

}
