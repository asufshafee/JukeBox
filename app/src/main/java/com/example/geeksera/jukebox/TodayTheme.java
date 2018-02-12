package com.example.geeksera.jukebox;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.geeksera.jukebox.Session.MyApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayTheme extends Fragment {

    TextView TodayTheme;


    public TodayTheme() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_today_theme, container, false);

        MyApplication myApplication=(MyApplication)getActivity().getApplicationContext();

        TodayTheme=(TextView) view.findViewById(R.id.TodayTheme);
        TodayTheme.setText(myApplication.getThemee());

        return view;
    }

}
