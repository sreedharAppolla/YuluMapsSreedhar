package com.example.yulumapssreedhar.presenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.yulumapssreedhar.R;
import com.example.yulumapssreedhar.contract.HomeFragmentContract;
import com.example.yulumapssreedhar.model.HomeModel;
import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;
import com.example.yulumapssreedhar.view.activities.BaseActivity;
import com.example.yulumapssreedhar.view.fragments.MapFragment;

import java.util.ArrayList;

import androidx.fragment.app.FragmentTransaction;

public class HomeFragmentPresenter implements HomeFragmentContract.HomePresenter {
    private HomeFragmentContract.HomeView view;
    private Context context;
    private HomeModel homeModel;
    private ArrayList<DetailListPojo> detailListPojos;

    public HomeFragmentPresenter(HomeFragmentContract.HomeView view, Context context) {
        this.view = view;
        this.context = context;
        homeModel=new HomeModel();
    }

    @Override
    public void callApi( View view) {
        detailListPojos=homeModel.callApi(view,context);

    }

    @Override
    public void fabClick() {
        FragmentTransaction fragmentTransaction=((BaseActivity)context).getSupportFragmentManager().beginTransaction();
        MapFragment mapFragment=new MapFragment();
        Bundle bundle=new Bundle();
        bundle.putBoolean("isList",true);
        bundle.putParcelableArrayList("detailsList",detailListPojos);
        mapFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragments_container,mapFragment,"mapFrag");
        fragmentTransaction.addToBackStack("homeFrag");
        fragmentTransaction.commit();
    }

    @Override
    public void addLocationClick() {
        FragmentTransaction fragmentTransaction=((BaseActivity)context).getSupportFragmentManager().beginTransaction();
        MapFragment mapFragment=new MapFragment();
        Bundle bundle=new Bundle();
        bundle.putBoolean("isList",false);
        bundle.putBoolean("isEdit",false);
        mapFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragments_container,mapFragment,"mapFrag");
        fragmentTransaction.addToBackStack("homeFrag");
        fragmentTransaction.commit();
    }
}
