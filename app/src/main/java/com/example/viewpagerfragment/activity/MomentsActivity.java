package com.example.viewpagerfragment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.viewpagerfragment.DataHolder;
import com.example.viewpagerfragment.ImageHolder;
import com.example.viewpagerfragment.MomentItem;
import com.example.viewpagerfragment.MyRecyclerAdapter;
import com.example.viewpagerfragment.NameHolder;
import com.example.viewpagerfragment.R;
import com.example.viewpagerfragment.fragment.ReleaseFragment;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//单例或全局变量，用于传输数据，非常重要！！！
public class MomentsActivity extends AppCompatActivity {

    private ImageView back;
    private ImageView camera;

    private RecyclerView recyclerView;

    private static MyRecyclerAdapter myRecyclerAdapter;
    private static List<MomentItem> momentItems;

    public ImageView moments_user_icon;

    private ImageView background;
    private TextView moments_user_name;
   private String rename=null;
    String receivedValue;

    private String receivedMessage=null;

    private MessageAdapter messageAdapter;

    private Uri receivedUri=null;
    private JSONArray dataArray;

 //SharedViewModel sharedViewModel;
    MomentItem momentItem;

 //  public void onResume(){
 //      super.onResume();

 //      if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
 //          ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
 //      }
 //  }

    public void receiveData(String msg, Uri selectedImageUri) {
        receivedMessage = msg;
        receivedUri=selectedImageUri;

        Log.e("msg",receivedMessage);


        FrameLayout frameLayout = findViewById(R.id.container);

        if (frameLayout != null) {
            frameLayout.getLayoutParams().height = 0;
            frameLayout.requestLayout();
        }

        // 将消息添加到数据持有器中
     //   MessageDataHolder.getInstance().addMessage(receivedMessage,receivedUri);

        MessageDataHolder.MessageItem messageItem = new MessageDataHolder.MessageItem(receivedMessage, receivedUri);
        MessageDataHolder.getInstance().addMessage(messageItem);


        // 通知适配器数据已经改变
        messageAdapter.notifyItemInserted(MessageDataHolder.getInstance().getMessages().size() - 1);

        // 滚动到新消息位置
        recyclerView.scrollToPosition(MessageDataHolder.getInstance().getMessages().size() - 1);
    }
    public void processDataAndCloseFragment(String msg, Uri selectedImageUri) {
        // 处理数据，例如将消息添加到数据持有器中
        receiveData(msg,selectedImageUri);
//        NameHolder.getInstance().setRelease_content(msg);
//        new LoadMomentToSQL().execute();
        // 关闭或隐藏Fragment
        getSupportFragmentManager().beginTransaction()
                .remove(getSupportFragmentManager().findFragmentById(R.id.container))
                .commit();
    }



    public void hideFragment(int number) {

        FrameLayout frameLayout = findViewById(R.id.container);

        if (frameLayout != null) {
            frameLayout.getLayoutParams().height = 0;
            frameLayout.requestLayout();
        }

        // 关闭或隐藏Fragment
        getSupportFragmentManager().beginTransaction()
                .remove(getSupportFragmentManager().findFragmentById(R.id.container))
                .commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // 获取选择的图片的URI
            Uri selectedImage = data.getData();

            saveImageUriToSharedPreferences(selectedImage);

            // 将选中的图片设置给 background
           // background.setBackground(selectedImage);

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                // 创建BitmapDrawable
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);

                // 设置背景
                background.setBackground(bitmapDrawable);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

    // 保存选中的图片URI到SharedPreferences
    private void saveImageUriToSharedPreferences(Uri imageUri) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("background_image_uri", imageUri.toString());
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);

        // 请求存储权限
     //   if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
     //       ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
     //   }
//        rename= NameHolder.getInstance().getSharedValue();

       // if(rename==null){
      //      receivedValue = NameHolder.getInstance().getSharedValue();
      //  }
       // else{
       //     receivedValue=rename;
       // }
        receivedValue=rename;

        recyclerView = findViewById(R.id.recyclerview);
        messageAdapter = new MessageAdapter(MessageDataHolder.getInstance().getMessages());

        LinearLayoutManager layoutManager = new LinearLayoutManager(MomentsActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true); // 可选，确保新的项出现在底部
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(messageAdapter);

        new DownLoadMomentsTask().execute();

        moments_user_icon=findViewById(R.id.moments_user_icon);
        moments_user_name=findViewById(R.id.moments_user_name);


          rename=NameHolder.getInstance().getSharedValue();
      if(rename==null){
          moments_user_name.setText(receivedValue);
      }
      else{
          moments_user_name.setText(rename);
      }

       Bitmap sharedImage = ImageHolder.getInstance().getSharedImage();
       if (sharedImage != null) {
           Log.e("Image", "Shared Image is not null");
       moments_user_icon.setImageBitmap(sharedImage);
       }




background=findViewById(R.id.moments_background);
background.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // 创建一个Intent，设置其Action为ACTION_PICK，用于从系统图库中选择图片
        Intent intent = new Intent(Intent.ACTION_PICK);

        // 设置数据类型为image/*，表示选择的文件类型是图片
        intent.setType("image/*");

        // 使用startActivityForResult启动图库应用，请求码为1
        startActivityForResult(intent, 1);
    }
});




        back = findViewById(R.id.moments_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        camera = findViewById(R.id.moments_camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout frameLayout = findViewById(R.id.container);

                if (frameLayout != null) {
                    // 更改FrameLayout的高度为match_parent
                    frameLayout.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                    frameLayout.requestLayout();
                }


                ReleaseFragment fragment = new ReleaseFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment); // R.id.container是用于承载Fragment的容器
                //  transaction.addToBackStack(null); // 添加到返回栈中
                transaction.commit();
            }
        });
    }
    private class DownLoadMomentsTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.e("sdfsfs","开始");

            OkHttpClient load_client=new OkHttpClient();
            String url="http://192.168.145.1:8080/listInfor";
            //构建请求体
            RequestBody download_requestBody=new FormBody.Builder()
                    .build();

            Request load_request=new Request.Builder()
                    .url(url)
                    .post(download_requestBody)
                    .build();

            //发送请求数据并获得响应
            try {
                Log.e("sdfsfs","发送数据");
                Response load_response=load_client.newCall(load_request).execute();
                String load_responseData=load_response.body().string();

                com.alibaba.fastjson.JSONObject jsonResponse = com.alibaba.fastjson.JSONObject.parseObject(load_responseData);
                int code = jsonResponse.getIntValue("code");
                Log.e("code", String.valueOf(code));
                String message = jsonResponse.getString("message");
                Log.e("back", message);

                // 获取 "data" 对应的 JSON 数组
                dataArray = jsonResponse.getJSONArray("data");
                Log.e("test", dataArray.toString());
                Log.e("长度", String.valueOf(dataArray.size()));

                // 处理响应数据
                return  code==200;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success){
            if(success){
                Log.e("test","win");
                int arraySize = dataArray.size();
// 遍历数组并添加到 MessageDataHolder 中
                for (int i = 0; i < arraySize; i++) {
                    JSONObject item = dataArray.getJSONObject(i);

                    // 获取 uid、usericon、username
                    String uid = item.getString("uid");
                    String userIcon = item.getString("usericon");
                    String username = item.getString("username");

                    // 创建 MessageItem 对象并添加到 MessageDataHolder
                    MessageDataHolder.MessageItem messageItem = new MessageDataHolder.MessageItem(username, receivedUri);//用户图标没改呢，这里要变化
                    MessageDataHolder.getInstance().addMessage(messageItem);
                }

// 通知适配器数据已经改变
                messageAdapter.notifyItemInserted(MessageDataHolder.getInstance().getMessages().size() - 1);

// 滚动到新消息位置
                recyclerView.scrollToPosition(MessageDataHolder.getInstance().getMessages().size() - 1);


            }
            else{
                Toast.makeText(MomentsActivity.this,"设置失败！请检查您的网络！",Toast.LENGTH_SHORT).show();
            }
        }

    }






}
