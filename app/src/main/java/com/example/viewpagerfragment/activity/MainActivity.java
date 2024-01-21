package com.example.viewpagerfragment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.viewpagerfragment.DataHolder;
import com.example.viewpagerfragment.MyViewPagerAdapter;
import com.example.viewpagerfragment.NameHolder;
import com.example.viewpagerfragment.R;
import com.example.viewpagerfragment.SharedViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FragmentManager fragmentManager;

    PopupWindow popupWindow;

    ViewPager2 viewPager2;

    private TextView title_txt;

    String receivedUserName;

    private LinearLayout nav_chats, nav_contacts, nav_discover, nav_me;
    private ImageView iv_chats, iv_contacts, iv_discover, iv_me, iv_current;

    SharedViewModel sharedViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title_txt=findViewById(R.id.title_txt);

        View root = findViewById(R.id.abc);
        root.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

//
//        NameHolder.getInstance().setSharedValue(receivedUserName);

if(NameHolder.getInstance().getUid()!=null) {
    String a = NameHolder.getInstance().getUid();
    Log.e("test", a);
}

        initFragment();

        initNavi();

        // 创建PopupMenu
        popupWindow = new PopupWindow(MainActivity.this);
        View popupView = getLayoutInflater().inflate(R.layout.newchat_menu_layout, null);
        popupWindow.setContentView(popupView);


        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#333333"));
        popupWindow.setBackgroundDrawable(colorDrawable);

        // 获取屏幕的宽度和高度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;


        float widthRatio = 0.4f; // 例如，设置为屏幕宽度的70%
        float heightRatio = 0.35f; // 例如，设置为屏幕高度的50%

        int popupWidth = (int) (screenWidth * widthRatio);
        int popupHeight = (int) (screenHeight * heightRatio);


        popupWindow.setWidth(popupWidth);
        popupWindow.setHeight(popupHeight);


        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);



        // 添加点击事件到 ImageView
        ImageView title_add = root.findViewById(R.id.title_add);
        title_add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                // 显示Popupwindow
                popupWindow.showAsDropDown(title_add); // 传入锚点视图，如一个按钮或其他视图
            }
        });



    }





private void initNavi(){
    nav_chats= findViewById(R.id.nav_chats);
    nav_chats.setOnClickListener(this);

    nav_contacts= findViewById(R.id.nav_contacts);
    nav_contacts.setOnClickListener(this);

    nav_discover= findViewById(R.id.nav_discover);
    nav_discover.setOnClickListener(this);

    nav_me= findViewById(R.id.nav_me);
    nav_me.setOnClickListener(this);


    iv_chats= findViewById(R.id.iv_chats);

    iv_contacts= findViewById(R.id.iv_contacts);

    iv_discover= findViewById(R.id.iv_discover);

    iv_me= findViewById(R.id.iv_me);

    iv_chats.setSelected(true);
    iv_current=iv_chats;
}

  private void initFragment() {
        fragmentManager=getSupportFragmentManager();





        viewPager2=findViewById(R.id.ViewPager);
        MyViewPagerAdapter myViewPagerAdapter=new MyViewPagerAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(myViewPagerAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                changeNavi(position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


    }


    private void changeNavi(int position) {
        iv_current.setSelected(false);

        if (position == R.id.nav_chats || position == 0) {
            viewPager2.setCurrentItem(0);
            iv_chats.setSelected(true);
            iv_current = iv_chats;
            title_txt.setText("Chats");
        } else if (position == R.id.nav_contacts || position == 1) {
            viewPager2.setCurrentItem(1);
            iv_contacts.setSelected(true);
            iv_current = iv_contacts;
            title_txt.setText("Contacts");
        } else if (position == R.id.nav_discover || position == 2) {
            viewPager2.setCurrentItem(2);
            iv_discover.setSelected(true);
            iv_current = iv_discover;
            title_txt.setText("Discover");
        } else if (position == R.id.nav_me || position == 3) {
            viewPager2.setCurrentItem(3);
            iv_me.setSelected(true);
            iv_current = iv_me;
            title_txt.setText("Me");
        }
    }


    @Override
    public void onClick(View v) {
        changeNavi(v.getId());
    }


}


