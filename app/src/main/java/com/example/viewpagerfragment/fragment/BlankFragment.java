package com.example.viewpagerfragment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viewpagerfragment.R;


public class BlankFragment extends Fragment {

    private static final String ARG_TXT = "param1";

    private View root;


    // TODO: Rename and change types of parameters
    private String mTxtString;
    private TextView fragTxt;





    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(String param1) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TXT, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTxtString = getArguments().getString(ARG_TXT);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
  if(root==null){
      root=inflater.inflate(R.layout.fragment_blank, container, false);
  }
    initView();
        return root;
    }

    private void initView() {
  fragTxt=root.findViewById(R.id.fragtxt);
  fragTxt.setText(mTxtString);

    }
}