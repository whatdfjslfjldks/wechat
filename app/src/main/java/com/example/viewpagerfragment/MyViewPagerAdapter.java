package com.example.viewpagerfragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.viewpagerfragment.fragment.ChatsFragment;
import com.example.viewpagerfragment.fragment.ContactsFragment;
import com.example.viewpagerfragment.fragment.DiscoverFragment;
import com.example.viewpagerfragment.fragment.MeFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {

    public MyViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ChatsFragment();
            case 1:
                return new ContactsFragment();
            case 2:
                return new DiscoverFragment();
            case 3:
                return new MeFragment();
            default:
                return null; // 返回 null 或其他默认的 Fragment
        }
    }

    @Override
    public int getItemCount() {
        return 4; // 你要显示的 Fragment 数量
    }
}
