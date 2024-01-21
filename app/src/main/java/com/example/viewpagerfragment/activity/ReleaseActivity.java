package com.example.viewpagerfragment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.provider.MediaStore;
import android.net.Uri;

import com.example.viewpagerfragment.R;

import java.io.InputStream;

public class ReleaseActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;

    private ImageView release_back;
    private Button release_post;
    private ImageView add_photo;

    private EditText input_words;

    private int imageResource;

    private Uri selectedImageUri;

    private int hasLeft = 0;

    private boolean hasSelectedImage = false;



    public void onResume() {
        super.onResume();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);

        release_back = findViewById(R.id.release_back);
        release_post = findViewById(R.id.release_post);
        add_photo = findViewById(R.id.add_photo);
        input_words=findViewById(R.id.input_words);

        release_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        release_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                release_post.setBackgroundResource(R.drawable.green_button_style);//没成功运行

                // 获取输入框中的文字
                String inputText = input_words.getText().toString();

                // 获取 add_photo 图片资源

                // 创建 Intent
                Intent intent = new Intent(ReleaseActivity.this, MomentsActivity.class);

                intent.putExtra("textView", inputText);
                intent.putExtra("imageUri", selectedImageUri.toString()); // 将图片的Uri传递


                // 启动 MomentsActivity 并传递数据
               // intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
             //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);


                input_words.setText("");
               Drawable drawable = getResources().getDrawable(R.drawable.big_add);
               add_photo.setImageDrawable(drawable);

            }
        });

        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建 Intent 打开相册
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
                hasSelectedImage = true; // 用户选择了图片
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            // 用户已选择一张图片
            selectedImageUri = data.getData();

            imageResource = getResourceIdFromUri(selectedImageUri);
            // 设置选中的图片为 add_photo 的背景
            add_photo.setImageURI(selectedImageUri);
        }
    }
    private int getResourceIdFromUri(Uri imageUri) {
        int imageResource = 0;

        // 获取 ContentResolver 和 Resources
        ContentResolver contentResolver = getContentResolver();
        Resources resources = getResources();

        try {
            // 通过 ContentResolver 获取 InputStream
            InputStream inputStream = contentResolver.openInputStream(imageUri);

            // 创建 Bitmap 对象
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // 根据 Bitmap 创建 Drawable
            Drawable drawable = new BitmapDrawable(resources, bitmap);

            // 使用 R.drawable 获取资源 ID


            // 使用 drawable 获取资源 ID
            imageResource = resources.getIdentifier("selected_image", "drawable", getPackageName());


            // 释放资源
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageResource;
    }

}
