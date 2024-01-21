package com.example.viewpagerfragment;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private String username;

    public String getUsername() {
        Log.d("get", "get username : " + username);
        return username;
    }

    public void setUsername(String username) {
        Log.d("set", "Set username to: " + username);
        this.username = username;
    }
}
