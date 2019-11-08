package com.example.yulumapssreedhar.contract;

import android.content.Context;
import android.view.View;

import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface EditInfoContract {
    interface EditInfoView{
        void uploadImage();

    }
    interface EditInfoPresenter{
        void saveInfo(View views, Boolean isAddDetails, DetailListPojo detailListPojo,DetailListPojo detailListPojoRef);
        void backClick();
        void latLngClick(DetailListPojo detailListPojo);
        void uploadImage(boolean isImageUploaded, RequestBody name,MultipartBody.Part multiPartBody);
        void uploadImageClick();
    }
    interface EditInfoModel{
        void saveAddedDetails(Context context, RequestBody body,boolean isImageUploaded,RequestBody name,MultipartBody.Part multiPartBody);
        void saveEditedDetails(Context context,RequestBody body,DetailListPojo detailListPojo,boolean isImageUploaded,RequestBody name,MultipartBody.Part multiPartBody);
        void addPlaceWithImage(Context context,RequestBody fileName,MultipartBody.Part file,RequestBody title,RequestBody latitude,RequestBody longitude,RequestBody description);
    }
}
