package com.example.viewpagerfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FriendsListAdapter extends ArrayAdapter<Friend> {
    private Context context;

    public FriendsListAdapter(Context context, List<Friend> friends) {
        super(context, 0, friends);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_friend, parent, false);
        }

        Friend currentFriend = getItem(position);

        ImageView profileImage = listItemView.findViewById(R.id.profile_image);
        TextView friendName = listItemView.findViewById(R.id.friend_name);
        TextView lastMessage = listItemView.findViewById(R.id.last_message);

        profileImage.setImageResource(currentFriend.getProfileImageResource());
        friendName.setText(currentFriend.getName());
        lastMessage.setText(currentFriend.getLastMessage());

        return listItemView;
    }
}
