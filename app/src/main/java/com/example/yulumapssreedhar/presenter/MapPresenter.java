package com.example.yulumapssreedhar.presenter;

import android.content.Context;

import com.example.yulumapssreedhar.contract.MapFragmentContract;
import com.example.yulumapssreedhar.view.activities.BaseActivity;

import java.net.ContentHandler;

public class MapPresenter implements MapFragmentContract.MapFragmentPresenter {
    private MapFragmentContract.MapFragmentView view;
    private Context context;

    public MapPresenter(MapFragmentContract.MapFragmentView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void backClick() {
        ((BaseActivity)context).getSupportFragmentManager().popBackStackImmediate();
    }
}
