package com.example.viewpagerfragment.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.viewpagerfragment.NameHolder;
import com.example.viewpagerfragment.R;
import com.example.viewpagerfragment.SharedViewModel;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private ImageView xmark;
    private EditText username;
    private EditText password;
    private TextView login;
    private CheckBox autoLogin;

    SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        xmark = findViewById(R.id.login_xmark);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_btn);
        autoLogin = findViewById(R.id.autoLogin);

        xmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MatchInforTask().execute();
//
//
//                boolean isAutoLogin = autoLogin.isChecked();
//
//                if (isAutoLogin) {
//                    // 如果选中自动登录，保存用户名和密码
//                    SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString("username", enteredUsername);
//                    editor.putString("password", enteredPassword);
//                    editor.putBoolean("auto_login", true); // 保存自动登录状态
//                    editor.apply();
//                }
//
//                // 启动主界面
//                Intent goMain = new Intent(LoginActivity.this, MainActivity.class);
//                goMain.putExtra("username", enteredUsername); // 传递用户名
//                startActivity(goMain);
//                finish();
            }
        });
    }

    private class MatchInforTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            OkHttpClient match_client=new OkHttpClient();
            String url="http://192.168.145.1:8080/matchInfor";

            String enteredUsername = username.getText().toString();
            String enteredPassword = password.getText().toString();

            //构建请求体
            RequestBody match_requestbody=new FormBody.Builder()
                    .add("username",enteredUsername)
                    .add("password",enteredPassword)
                    .build();
            //创建post请求
            Request match_request=new Request.Builder()
                    .url(url)
                    .post(match_requestbody)
                    .build();
            //发送请求并获取响应
            try {
                Response match_response=match_client.newCall(match_request).execute();
                String match_data=match_response.body().string();
                Log.e("test",match_data);
                if(match_data.equals("nook")){
                    Log.e("test","is false");
                    return false;
                }else{
                    Log.e("test","is true");
                    NameHolder.getInstance().setUid(match_data);
                    NameHolder.getInstance().setSharedValue(enteredUsername);
                    return true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean success){
            if(success){
                                // 启动主界面
                Intent goMain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(goMain);
                finish();
            }else{
                Toast.makeText(LoginActivity.this,"登录失败！请检查您的网络！检查信息是否填写正确！",Toast.LENGTH_SHORT).show();
            }
        }

    }

}
