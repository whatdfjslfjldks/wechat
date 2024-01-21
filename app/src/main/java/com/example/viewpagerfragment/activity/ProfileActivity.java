package com.example.viewpagerfragment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewpagerfragment.DataHolder;
import com.example.viewpagerfragment.ImageHolder;
import com.example.viewpagerfragment.NameHolder;
import com.example.viewpagerfragment.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profile_back;
    private ImageView photo;
    private TextView name2;
    private TextView weixinid;

    private LinearLayout profile_photo;
    private LinearLayout profile_name;
    private LinearLayout profile_tickle;
    private LinearLayout profile_weixinid;
    private LinearLayout profile_qrcode;
    private LinearLayout profile_moreinfor;
    private  Bitmap sharedImage = null;
    String receivedRename=null;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String file_address;
    private String uid;


    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 通过URI获取InputStream
            InputStream imageStream = getContentResolver().openInputStream(uri);

            // 使用BitmapFactory将InputStream解码为Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

            // 记得关闭InputStream
            imageStream.close();

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onResume() {
        super.onResume();

        receivedRename= NameHolder.getInstance().getSharedValue();
        if(receivedRename==null){
            name2.setText(NameHolder.getInstance().getSharedValue());
        }else {
            name2.setText(receivedRename);
        }
//        name2.setText(receivedRename);



        sharedImage = ImageHolder.getInstance().getSharedImage();
        if (sharedImage != null) {
            Log.e("Image", "Shared Image is not null");
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), sharedImage);
            photo.setBackground(bitmapDrawable);
        }

    }

//重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            // 获取选择的图片的URI
//            Uri selectedImage = data.getData();
//
//
//
//            // 获取 Bitmap 对象
//            Bitmap selectedBitmap = getBitmapFromUri(selectedImage);
//
//            // 将选中的图片设置给ImageView
//            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), selectedBitmap);
//            photo.setBackground(bitmapDrawable);
//
//            // 将 Bitmap 保存到 ImageHolder
//            ImageHolder.getInstance().setSharedImage(selectedBitmap);
//        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);

                Bitmap selectedBitmap = getBitmapFromUri(selectedImage);
             //    将选中的图片设置给ImageView
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), selectedBitmap);
            photo.setBackground(bitmapDrawable);

            // 将 Bitmap 保存到 ImageHolder
            ImageHolder.getInstance().setSharedImage(selectedBitmap);

                byte[] imageData = convertBitmapToByteArray(bitmap);

                // 将图片上传到电脑桌面的服务器
                int number=0;
                if(number==0) {
                    new UploadImageTask().execute(imageData);
                    number++;
                }
                if(number==1) {
                    new UploadPhotoToSQL().execute();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private class UploadImageTask extends AsyncTask<byte[], Void, String> {
        @Override
        protected String doInBackground(byte[]... params) {
            try {
                byte[] imageData = params[0];

                // 电脑的局域网 IP 地址，请替换成你的电脑的实际 IP
                String serverUrl = "http://192.168.145.1:8888/upload";

                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // 将字节数组写入输出流
                OutputStream os = connection.getOutputStream();
                os.write(imageData);
                os.flush();
                os.close();

                // 获取服务器响应
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 上传成功，获取服务器响应内容
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    file_address=response.toString();
                    Log.e("test",file_address);
                    NameHolder.getInstance().setFile(file_address);

                    return file_address;
                } else {
                    return "Error uploading image";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Error uploading image";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // 在 UI 线程中处理上传结果
            // 注意：在 Android 中不能在子线程中直接更新 UI，如果需要更新 UI，请使用 Handler 或 AsyncTask
            System.out.println(result);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        uid=NameHolder.getInstance().getUid();


        initID();

//        String a=NameHolder.getInstance().getUid();
        

        setOnclick();


    }


    private void initID() {
        profile_back=findViewById(R.id.profile_back);
        profile_photo=findViewById(R.id.profile_photo);
        profile_name=findViewById(R.id.profile_name);
        profile_tickle=findViewById(R.id.profile_tickle);
        profile_weixinid=findViewById(R.id.profile_weixinid);
        profile_qrcode=findViewById(R.id.profile_myqrcode);
        profile_moreinfor=findViewById(R.id.profile_moreinfor);

        photo=findViewById(R.id.photo);
        name2=findViewById(R.id.name2);
        weixinid=findViewById(R.id.weixinid);

    }


    private void setOnclick() {

        profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                // 创建一个Intent，设置其Action为ACTION_PICK，用于从系统图库中选择图片
//                Intent intent = new Intent(Intent.ACTION_PICK);
//
//                // 设置数据类型为image/*，表示选择的文件类型是图片
//                intent.setType("image/*");
//
//                // 使用startActivityForResult启动图库应用，请求码为1
//                startActivityForResult(intent, 1);

                   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }
        });

        profile_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, NameActivity.class));
            }
        });

        profile_tickle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, TickleActivity.class));
            }
        });

        profile_weixinid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, WeixinidActivity.class));
            }
        });

        profile_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, QRcodeActivity.class));
            }
        });

        profile_moreinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MoreInforActivity.class));
            }
        });


    }

    private class UploadPhotoToSQL extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            OkHttpClient addPhoto_client=new OkHttpClient();
            String url="http://192.168.145.1:8080/addPhoto";
            String test="123";
            test=NameHolder.getInstance().getFile_address();
            Log.e("test",test);

            //构建请求体
            RequestBody addPhoto_requestBody=new FormBody.Builder()
                    .add("uid", uid)
                    .add("usericon", test)
                    .add("background", test)
                    .build();

            //发送请求
            Request addPhoto_request=new Request.Builder()
                    .url(url)
                    .post(addPhoto_requestBody)
                    .build();
            Log.e("test","发送请求成功");
            //发送请求并解析响应
            try {
                Log.e("sdfsfs","开始");
                Response addPhoto_response=addPhoto_client.newCall(addPhoto_request).execute();
                String addPhoto_responseData=addPhoto_response.body().string();
                Log.e("position","is here");

                Log.e("data",addPhoto_responseData);
                if(addPhoto_responseData.equals("Photo added successfully")){
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
                Toast.makeText(ProfileActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this,"设置失败！请检查您的网络！注意学号不能重复！",Toast.LENGTH_SHORT).show();
            }
        }

    }


}