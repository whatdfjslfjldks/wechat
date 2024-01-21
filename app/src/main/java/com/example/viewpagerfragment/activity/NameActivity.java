package com.example.viewpagerfragment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewpagerfragment.DataHolder;
import com.example.viewpagerfragment.NameHolder;
import com.example.viewpagerfragment.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NameActivity extends AppCompatActivity {

    private ImageView editname_back;
    private TextView edit_save;
    private EditText edit_name;
    private String rename;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        initID();
        setOnclick();
//        edit_name.setText(NameHolder.getInstance().getSharedValue());


    }

    private void initID() {
        editname_back=findViewById(R.id.editname_back);
        edit_save=findViewById(R.id.edit_save);
        edit_name=findViewById(R.id.edit_name);
    }
    private void setOnclick() {
        editname_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rename=edit_name.getText().toString();
                uid=NameHolder.getInstance().getUid();
                if(!rename.isEmpty() && !uid.isEmpty()) {
                    new AlterNameTask().execute();
                }else{
                    Toast.makeText(NameActivity.this,"信息不能为空",Toast.LENGTH_SHORT).show();
                }
//                NameHolder.getInstance().setSharedValue(rename);
               // finish();
//                startActivity(new Intent(NameActivity.this, ProfileActivity.class));
            }
        });
    }

    private class AlterNameTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            OkHttpClient alterName_client=new OkHttpClient();
            String url="http://192.168.145.1:8080/alterUsername";

            //构建请求体
            RequestBody alterName_requestBody=new FormBody.Builder()
                    .add("newUsername",rename)
                    .add("uid",uid)
                    .build();

            //发送请求
            Request alterName_request=new Request.Builder()
                    .url(url)
                    .post(alterName_requestBody)
                    .build();
            Log.e("test","发送请求成功");
            //发送请求并解析响应
            try {
                Log.e("sdfsfs","开始");
                Response alterName_response=alterName_client.newCall(alterName_request).execute();
                String alterName_responseData=alterName_response.body().string();

                Log.e("data",alterName_responseData);
                if(alterName_responseData.equals("ok")){
                    return true;
                }else return false;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                NameHolder.getInstance().setSharedValue(rename);
                 finish();
                startActivity(new Intent(NameActivity.this, ProfileActivity.class));
            } else {
                Toast.makeText(NameActivity.this,"设置失败！请检查您的网络！注意名字不能重复!",Toast.LENGTH_SHORT).show();
            }
        }

    }

}