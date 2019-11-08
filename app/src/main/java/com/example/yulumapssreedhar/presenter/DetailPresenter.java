package com.example.yulumapssreedhar.presenter;

import android.content.Context;
import android.os.Bundle;

import com.example.yulumapssreedhar.R;
import com.example.yulumapssreedhar.contract.DetailContract;
import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;
import com.example.yulumapssreedhar.view.activities.BaseActivity;
import com.example.yulumapssreedhar.view.fragments.EditInfoFragment;
import com.example.yulumapssreedhar.view.fragments.MapFragment;

import androidx.fragment.app.FragmentTransaction;

public class DetailPresenter implements DetailContract.DetailPresenter {
    private DetailContract.DetailView view;
    private Context context;

    public DetailPresenter(DetailContract.DetailView view, Context context) {
        this.view = view;
        this.context = context;

    }

    @Override
    public void latLngClick(DetailListPojo detailListPojo) {
        FragmentTransaction fragmentTransaction=((BaseActivity)context).getSupportFragmentManager().beginTransaction();
        MapFragment mapFragment=new MapFragment();
        Bundle bundle=new Bundle();
        bundle.putBoolean("isList",false);
        bundle.putBoolean("isEdit",true);
        bundle.putParcelable("detailsPojo",detailListPojo);
        mapFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragments_container,mapFragment,"mapFrag");
        fragmentTransaction.addToBackStack("homeFrag");
        fragmentTransaction.commit();
    }

    @Override
    public void backClick() {
        ((BaseActivity)context).onBackClick();
    }

    @Override
    public void editClick(DetailListPojo detailListPojo) {
        FragmentTransaction fragmentTransaction=((BaseActivity)context).getSupportFragmentManager().beginTransaction();
        EditInfoFragment editInfoFragment=new EditInfoFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("detailsPojo",detailListPojo);
        bundle.putBoolean("addPlace",false);
        editInfoFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragments_container,editInfoFragment,"editInfoFrag");
        fragmentTransaction.addToBackStack("mapFrag");
        fragmentTransaction.commit();
    }
}
