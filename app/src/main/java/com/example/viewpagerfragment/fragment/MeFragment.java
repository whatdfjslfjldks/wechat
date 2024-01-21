package com.example.viewpagerfragment.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.viewpagerfragment.DataHolder;
import com.example.viewpagerfragment.ImageHolder;
import com.example.viewpagerfragment.NameHolder;
import com.example.viewpagerfragment.activity.ProfileActivity;
import com.example.viewpagerfragment.R;
import com.example.viewpagerfragment.activity.StartActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {
    private View root;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView username;
    private ImageView me_portrait;
    private  Bitmap sharedImage = null;
    private String rename=null;

    public MeFragment() {
        // Required empty public constructor
    }

    //非常关键，可以让fragment的运行摆脱activity的“干扰”
@Override
    public void onResume() {
    super.onResume();

    rename= NameHolder.getInstance().getSharedValue();
    if(rename==null){
        username.setText(NameHolder.getInstance().getSharedValue());
    }else{
        username.setText(rename);
    }

    sharedImage = ImageHolder.getInstance().getSharedImage();
    if (sharedImage != null) {
        Log.e("Image", "Shared Image is not null");
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), sharedImage);
        me_portrait.setBackground(bitmapDrawable);
    }



}


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_me, container, false);
        }
        TextView logout = root.findViewById(R.id.logout);
        LinearLayout me_profile = root.findViewById(R.id.me_profile);




        username = root.findViewById(R.id.me_username);
        me_portrait = root.findViewById(R.id.me_portrait);

        me_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                me_profile.setBackgroundResource(R.drawable.background_clickable);

                startActivity(new Intent(MeFragment.this.getContext(), ProfileActivity.class));
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout.setBackgroundResource(R.drawable.background_clickable);
                Intent intent = new Intent(MeFragment.this.getContext(), StartActivity.class);
                //我愿称之为传值判断法，我真是天才！！！！！！！！
                intent.putExtra("your_key", 1); // 传递数值1

                startActivity(intent);
            }
        });

        return root;

    }
}