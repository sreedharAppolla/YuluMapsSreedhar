package com.example.yulumapssreedhar.contract;

import android.content.Context;
import android.view.View;

import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;

import java.util.ArrayList;


public interface HomeFragmentContract {

    interface HomeView{

    }

    interface HomePresenter{
        void callApi(View view);
        void fabClick();
        void addLocationClick();
    }

    interface HomeModel{
        ArrayList<DetailListPojo>  callApi( View view, Context context);
    }

}
