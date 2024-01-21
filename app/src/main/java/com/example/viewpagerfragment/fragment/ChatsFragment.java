package com.example.viewpagerfragment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.viewpagerfragment.Friend;
import com.example.viewpagerfragment.FriendsListAdapter;
import com.example.viewpagerfragment.R;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

    View root;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_chats, container, false);
        }

        // 获取 ListView 控件
        ListView listView = root.findViewById(R.id.list_view);

        // 创建好友列表
        List<Friend> friends = new ArrayList<>();
        // 添加好友到列表
        friends.add(new Friend("熊大", R.drawable.xiongda, "来砍树没有？"));
        friends.add(new Friend("熊二", R.drawable.xionger, "你家有好吃的吗？"));
        friends.add(new Friend("嘟嘟", R.drawable.dudu, "我不识字"));


        // 创建适配器并设置给 ListView
        FriendsListAdapter adapter = new FriendsListAdapter(requireContext(), friends);
        listView.setAdapter(adapter);

        return root;
    }
}
