package com.example.viewpagerfragment;

import android.graphics.Bitmap;
import android.util.Log;

public class ImageHolder {
    private static ImageHolder instance = new ImageHolder();
    private Bitmap sharedImage=null; // 存储图像的 Bitmap

    private ImageHolder() {}

    public static ImageHolder getInstance() {
        return instance;
    }

    public Bitmap getSharedImage() {
        return sharedImage;
    }

    public void setSharedImage(Bitmap image) {
        sharedImage = image;
    }
}
