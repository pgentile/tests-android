package com.example.testsandroid.app;

import android.graphics.Bitmap;

final class PhotoCapturedEvent {

    private final Bitmap photoBitmap;

    public PhotoCapturedEvent(Bitmap photoBitmap) {
        this.photoBitmap = photoBitmap;
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

}
