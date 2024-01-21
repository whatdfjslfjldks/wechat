package com.example.viewpagerfragment.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewpagerfragment.ImageHolder;
import com.example.viewpagerfragment.NameHolder;
import com.example.viewpagerfragment.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<MessageDataHolder.MessageItem> messages;

    public MessageAdapter(List<MessageDataHolder.MessageItem> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moments_item_layout, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageDataHolder.MessageItem messageItem = messages.get(position);

        // 设置文本
        holder.messageText.setText(messageItem.getText());

        String name= NameHolder.getInstance().getSharedValue();
        holder.item_user_name.setText(name);
        ImageHolder imageHolder = ImageHolder.getInstance();
        Bitmap sharedImage = imageHolder.getSharedImage();



          if (sharedImage != null) {
            holder.item_user_icon.setImageBitmap(sharedImage);
        } else {
            // 如果共享图像为空，你可以设置一个默认图像或采取其他适当的措施
            holder.item_user_icon.setImageResource(R.drawable.test);
        }


        // 设置图片
        Uri imageUri = messageItem.getImageUri();
        if (imageUri != null) {
            // 这里需要根据您的布局更改 ImageView 的 ID
            holder.messageImage.setImageURI(imageUri);
            Log.e("图片","已加载");
        }
        else{
            Log.e("吐泡泡","图片未加载");
            ViewGroup.LayoutParams layoutParams = holder.messageImage.getLayoutParams();
            layoutParams.width = 0;
            layoutParams.height = 20;
            holder.messageImage.setLayoutParams(layoutParams);

        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        ImageView messageImage;

        TextView item_user_name;
        ImageView item_user_icon;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.item_release);
            messageImage = itemView.findViewById(R.id.item_iv); // 更改为您的 ImageView ID
            item_user_name=itemView.findViewById(R.id.item_user_name);
            item_user_icon=itemView.findViewById(R.id.item_user_icon);

        }
    }
}
