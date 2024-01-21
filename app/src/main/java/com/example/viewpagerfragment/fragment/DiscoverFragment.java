package com.example.viewpagerfragment.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viewpagerfragment.activity.MomentsActivity;
import com.example.viewpagerfragment.R;
import com.example.viewpagerfragment.SharedViewModel;

public class DiscoverFragment extends Fragment {

    SharedViewModel sharedViewModel;


    public DiscoverFragment() {
        // Required empty public constructors
    }


    private View root;





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);


        return true;

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_discover, container, false);
        }
        ConstraintLayout moments=root.findViewById(R.id.moments);

        TextView textView=root.findViewById(R.id.textView);



        moments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moments.setBackgroundResource(R.drawable.background_clickable);
               Intent intent=new Intent(DiscoverFragment.this.getContext(), MomentsActivity.class);

                startActivity(intent);
            }
        });



        return root;
    }




}
