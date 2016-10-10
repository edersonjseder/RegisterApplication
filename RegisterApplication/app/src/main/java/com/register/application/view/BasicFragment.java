package com.register.application.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.register.application.R;

public class BasicFragment extends Fragment {

    private static final String TITLE = "Main";

    public BasicFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(TITLE);
    }
}
