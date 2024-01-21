package com.example.viewpagerfragment;

import android.net.Uri;

public class MomentItem {
    private String item_release;
    private Uri uri;

    public MomentItem(String item_release, Uri uri) {
        this.item_release = item_release;
        this.uri = uri;
    }

    public String getItemRelease() {
        return item_release;
    }

    public Uri getUri() {
        return uri;
    }
}
