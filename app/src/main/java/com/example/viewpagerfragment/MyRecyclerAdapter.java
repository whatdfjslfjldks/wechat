package com.example.viewpagerfragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MomentItem> momentItems;

    String receivedUserName;


    private static final int VIEW_TYPE_DYNAMIC = 1;

    private Bitmap sharedImage=null;



    public MyRecyclerAdapter(List<MomentItem> momentItems,String msg) {
        this.momentItems = momentItems;
        receivedUserName=msg;
    }

    @Override
    public int getItemCount() {
        return momentItems.size(); // 加1是因为要包括固定项
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View view = inflater.inflate(R.layout.moments_item_layout, parent, false);

            return new DynamicViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // 绑定其他项的数据
            MomentItem momentItem = momentItems.get(position); // position减1来获取正确的用户发布的图文
            ((DynamicViewHolder) holder).textView.setText(momentItem.getItemRelease());
            ((DynamicViewHolder) holder).imageView.setImageURI(momentItem.getUri());
            ((DynamicViewHolder) holder).item_user_name.setText(receivedUserName);


        Bitmap sharedImage = ImageHolder.getInstance().getSharedImage();
        if (sharedImage != null) {
            Log.e("Image", "Shared Image is not null");

            ((DynamicViewHolder) holder).item_user_icon.setImageBitmap(sharedImage);
        }



    }

    public static class DynamicViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        private TextView item_user_name;

        private ImageView item_user_icon;



        public DynamicViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.item_release);
            imageView = view.findViewById(R.id.item_iv);
            item_user_name=view.findViewById(R.id.item_user_name);
            item_user_icon=view.findViewById(R.id.item_user_icon);


        }
    }


}
