package com.example.testsandroid.app;

import android.graphics.Bitmap;

final class PhotoSavedEvent {

    private final Bitmap photoBitmap;

    public PhotoSavedEvent(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

}
