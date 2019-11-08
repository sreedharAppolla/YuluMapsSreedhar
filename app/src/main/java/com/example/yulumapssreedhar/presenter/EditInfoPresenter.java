package com.example.yulumapssreedhar.presenter;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.yulumapssreedhar.R;
import com.example.yulumapssreedhar.contract.EditInfoContract;
import com.example.yulumapssreedhar.model.EditInfoModel;
import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;
import com.example.yulumapssreedhar.view.activities.BaseActivity;
import com.example.yulumapssreedhar.view.fragments.MapFragment;

import org.json.JSONException;
import org.json.JSONObject;


import androidx.fragment.app.FragmentTransaction;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditInfoPresenter implements EditInfoContract.EditInfoPresenter {
    private EditInfoContract.EditInfoView editInfoView;
    private Context context;
    private EditInfoModel model;
    private MultipartBody.Part multiPartBody;
    private boolean isImageUploaded;
    private RequestBody name;

    public EditInfoPresenter(EditInfoContract.EditInfoView editInfoView, Context context) {
        this.editInfoView = editInfoView;
        this.context = context;
        model=new EditInfoModel();
    }


    @Override
    public void saveInfo(View views, Boolean isAddDetails, DetailListPojo detailListPojo,DetailListPojo detailListPojoRef) {
        if (isAddDetails){
            if (detailListPojo.getTitle()!=null&&!detailListPojo.getTitle().equals("")) {
                JSONObject jsonParams=new JSONObject();
                try {

                        jsonParams.put("title", detailListPojo.getTitle());
                        jsonParams.put("latitude", detailListPojo.getLatitude());
                        jsonParams.put("longitude", detailListPojo.getLongitude());
                        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonParams.toString()));
                        model.saveAddedDetails(context, body, isImageUploaded, name, multiPartBody);




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                ((BaseActivity)context).callToastClass("Please enter title");
            }
        }else {
            JSONObject jsonParams=new JSONObject();
            try {

                jsonParams.put("id",detailListPojo.getId());
                if (!detailListPojo.getTitle().equals(detailListPojoRef.getTitle())){
                    jsonParams.put("title",detailListPojo.getTitle());
                }

                jsonParams.put("longitude",detailListPojo.getLongitude());
                jsonParams.put("latitude",detailListPojo.getLatitude());

                if (isImageUploaded){
                    jsonParams.put("imageUrl",detailListPojo.getImage());
                }


                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonParams.toString()));
                model.saveEditedDetails(context,body,detailListPojo,isImageUploaded,name, multiPartBody);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void backClick() {
        ((BaseActivity)context).onBackClick();
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
    public void uploadImageClick() {
        editInfoView.uploadImage();
    }

    @Override
    public void uploadImage(boolean isImageUploaded,RequestBody name,MultipartBody.Part multiPartBody) {
        this.isImageUploaded=isImageUploaded;
        this.name=name;
        this.multiPartBody=multiPartBody;

    }


}
