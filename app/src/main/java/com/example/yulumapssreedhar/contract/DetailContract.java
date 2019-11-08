package com.example.yulumapssreedhar.contract;

import android.view.View;

import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;

public interface DetailContract {
    interface DetailView{

    }

    interface DetailPresenter{
        void latLngClick(DetailListPojo detailListPojo);
        void backClick();
        void editClick(DetailListPojo detailListPojo);
    }

    interface DetailModel{

    }
}
