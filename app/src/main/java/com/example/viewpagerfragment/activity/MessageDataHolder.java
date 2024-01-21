package com.example.viewpagerfragment.activity;

import java.util.ArrayList;
import java.util.List;
import android.net.Uri;

public class MessageDataHolder {
    private static MessageDataHolder instance;
    private List<MessageItem> messages = new ArrayList<>();

    private MessageDataHolder() {}

    public static MessageDataHolder getInstance() {
        if (instance == null) {
            instance = new MessageDataHolder();
        }
        return instance;
    }

    public List<MessageItem> getMessages() {
        return messages;
    }

    public void addMessage(MessageItem message) {
        messages.add(message);
    }

    public static class MessageItem {
        private String text;
        private Uri imageUri; // You can use Uri to represent the image

        public MessageItem(String text, Uri imageUri) {
            this.text = text;
            this.imageUri = imageUri;
        }

        public String getText() {
            return text;
        }

        public Uri getImageUri() {
            return imageUri;
        }
    }
}
