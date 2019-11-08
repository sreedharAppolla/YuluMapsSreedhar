package com.example.yulumapssreedhar.view.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yulumapssreedhar.R;
import com.example.yulumapssreedhar.contract.HomeFragmentContract;
import com.example.yulumapssreedhar.databinding.HomeFragmentBinding;
import com.example.yulumapssreedhar.presenter.HomeFragmentPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeFragmentContract.HomeView {
    private HomeFragmentBinding homeFragmentBinding;
    private HomeFragmentPresenter homeFragmentPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeFragmentBinding= DataBindingUtil.inflate(inflater,R.layout.home_fragment,container,false);
        View view=homeFragmentBinding.getRoot();
        homeFragmentPresenter=new HomeFragmentPresenter(this,getActivity());
        homeFragmentBinding.setHomePresent(homeFragmentPresenter);
        return init(view);
    }
    private View init(View v){
        /**
         * 1.To call api to fetch the all details
         * 2.Storing is done at offline once user call api successfully
         * 3.Sends the details to recycler view form here
         */
        homeFragmentPresenter.callApi(v);
        return v;
    }

}
