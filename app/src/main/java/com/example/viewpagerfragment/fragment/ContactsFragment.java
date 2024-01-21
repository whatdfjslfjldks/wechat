package com.example.viewpagerfragment.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.viewpagerfragment.ContactFriend;
import com.example.viewpagerfragment.ContactsListAdapter;
import com.example.viewpagerfragment.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {

    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contacts, container, false);

        // 获取 ListView 控件
        ListView listView = root.findViewById(R.id.friends_list_view);

        List<ContactFriend> contactFriends = new ArrayList<>();

// 添加联系人到列表（你可以在这里添加联系人信息）
        contactFriends.add(new ContactFriend("熊大", R.drawable.xiongda));
        contactFriends.add(new ContactFriend("熊二", R.drawable.xionger));
        contactFriends.add(new ContactFriend("嘟嘟", R.drawable.dudu));
// 继续添加更多联系人

// 创建适配器并设置给 ListView
        ContactsListAdapter adapter = new ContactsListAdapter(requireContext(), contactFriends);
        listView.setAdapter(adapter);

        return root;
    }
}
