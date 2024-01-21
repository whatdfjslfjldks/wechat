package com.example.viewpagerfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactsListAdapter extends ArrayAdapter<ContactFriend> {
    private Context context;

    public ContactsListAdapter(Context context, List<ContactFriend> friends) {
        super(context, 0, friends);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_contacts, parent, false);
        }

        ContactFriend currentFriend = getItem(position);

        ImageView profileImage = listItemView.findViewById(R.id.friend_profile_image);
        TextView friendName = listItemView.findViewById(R.id.friend_name);

        profileImage.setImageResource(currentFriend.getProfileImageResource());
        friendName.setText(currentFriend.getName());

        return listItemView;
    }
}
