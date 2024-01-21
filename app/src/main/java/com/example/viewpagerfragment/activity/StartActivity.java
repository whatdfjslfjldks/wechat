package com.example.viewpagerfragment.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viewpagerfragment.R;

public class StartActivity extends AppCompatActivity {

    private TextView start_login;
    private TextView start_register;

    public static StartActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        instance = this;

        start_login = findViewById(R.id.start_login);
        start_register = findViewById(R.id.start_register);

//我愿称之为传值判断法，我真是天才！！！！！！！！
        int yourValue = getIntent().getIntExtra("your_key", 0); // 0 是默认值，如果无法获取数值，将返回默认值


        if (isLoggedIn()&&yourValue==0) {
            // 如果已登录，直接跳转到MainActivity，并传递用户名
            SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
            String savedUsername = preferences.getString("username", "");
            Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
            mainIntent.putExtra("username", savedUsername); // 传递用户名
            startActivity(mainIntent);
          //  finish(); // 结束当前StartActivity
            return;
        }

        start_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });
    }

    private boolean isLoggedIn() {
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String savedUsername = preferences.getString("username", "");
        String savedPassword = preferences.getString("password", "");

        // 根据实际情况判断用户是否已登录，例如检查用户名和密码是否有效
        // 这里简单示范，如果用户名和密码都不为空，就视为已登录
        return !savedUsername.isEmpty() && !savedPassword.isEmpty();
    }
}
