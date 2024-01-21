package com.example.viewpagerfragment.fragment;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewpagerfragment.NameHolder;
import com.example.viewpagerfragment.R;
import com.example.viewpagerfragment.activity.MomentsActivity;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReleaseFragment extends Fragment {

    private boolean hasSelectedImage = false;
    private static final int REQUEST_IMAGE_PICK = 1;
    private View root;
    private ImageView release_back;
    private TextView release_post;
    private ImageView add_photo;
    private Uri selectedImageUri;
    private int imageResource;

    private EditText input_words;

    Context mBase;

    public ReleaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBase = context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == -1) {
            // 用户已选择一张图片
            selectedImageUri = data.getData();

            imageResource = getResourceIdFromUri(selectedImageUri);
            // 设置选中的图片为 add_photo 的背景
            add_photo.setImageURI(selectedImageUri);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 启用片段的后退按钮监听
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // 处理后退按钮事件
                MomentsActivity mainActivity2 = (MomentsActivity) getActivity();
                mainActivity2.hideFragment(1);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(root == null){
            root = inflater.inflate(R.layout.fragment_release, container, false);
        }
        release_back = root.findViewById(R.id.release_back);
        release_post = root.findViewById(R.id.release_post);
        add_photo = root.findViewById(R.id.add_photo);
        input_words=root.findViewById(R.id.input_words);


        release_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MomentsActivity mainActivity2 = (MomentsActivity) getActivity();
                mainActivity2.hideFragment(1);
            }
        });

        release_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = input_words.getText().toString();

                if (msg.isEmpty() && !hasSelectedImage) {
                    showAlertDialog("提示", "请添加您要发布的内容");
                    return;
                }
               // if (!hasSelectedImage) {
                //    add_photo.setVisibility(View.GONE);
                //}


// 获取对MainActivity的引用
                MomentsActivity momentsActivity=(MomentsActivity) getActivity();

                if (momentsActivity != null) {
                    // 调用MainActivity的方法，传递数据
                    // mainActivity.receiveData(msg);
                    NameHolder.getInstance().setRelease_content(msg);
                    Log.e("position","标记位置");
                    new LoadMomentToSQL().execute();
                    momentsActivity.processDataAndCloseFragment(msg,selectedImageUri);

                }

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

        return root;
    }
    public class LoadMomentToSQL extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.e("test","start upload");
            OkHttpClient moment_client=new OkHttpClient();
            String moment_url="http://192.168.145.1:8080/addMoment";

            String uid= NameHolder.getInstance().getUid();
            String username=NameHolder.getInstance().getSharedValue();
            String usericon=NameHolder.getInstance().getFile_address();
            String release_content=NameHolder.getInstance().getRelease_content();

            RequestBody moment_requestBody=new FormBody.Builder()
                    .add("uid",uid)
                    .add("username",username)
                    .add("usericon",usericon)
                    .add("release_content",release_content)
//                    .add("release_photo",release_photo)//还没存地址
                    .add("release_photo",usericon)//
                    .build();
            Request moment_request=new Request.Builder()
                    .url(moment_url)
                    .post(moment_requestBody)
                    .build();

            //发送请求并获取响应
            try {
                Response moment_response=moment_client.newCall(moment_request).execute();
                String moment_respongseData=moment_response.body().string();

                if(moment_respongseData.equals("true")){
                    return true;
                }else return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean success){
            if(success){
                Toast.makeText(getContext(),"发布成功！",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getContext(),"发布失败！请检查网络设置",Toast.LENGTH_SHORT).show();
            }
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
            imageResource = resources.getIdentifier("selected_image", "drawable", getPackageName());

            // 释放资源
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageResource;
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public ContentResolver getContentResolver() {
        return mBase.getContentResolver();
    }

    public String getPackageName() {
        return mBase.getPackageName();
    }
}
