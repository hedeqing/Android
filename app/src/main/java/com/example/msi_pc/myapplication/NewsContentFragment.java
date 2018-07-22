package com.example.msi_pc.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MSI-PC on 2018/7/21.
 */

public class NewsContentFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.new_content_frag,container,false);
        return view;
    }
    public  void refresh(String newsTitle,String newsContent){
        View visibility = view.findViewById(R.id.visible_layout);
        visibility.setVisibility(view.VISIBLE);
        TextView newsTitleText  = (TextView) view.findViewById(R.id.news_title);
        TextView newsContnetText = (TextView) view.findViewById(R.id.news_content);
        newsTitleText.setText(newsTitle);
        newsContnetText.setText(newsContent);

    }
}
