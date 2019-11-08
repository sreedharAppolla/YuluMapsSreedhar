package com.example.yulumapssreedhar.model;

import android.content.Context;
import android.util.Log;

import com.example.yulumapssreedhar.contract.EditInfoContract;
import com.example.yulumapssreedhar.globals.constants.RetrofitClint;
import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;
import com.example.yulumapssreedhar.view.activities.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInfoModel implements EditInfoContract.EditInfoModel {


    @Override
    public void saveAddedDetails(Context context, RequestBody body, boolean isImageUploaded, RequestBody name,MultipartBody.Part multiPartBody) {

        Call<ResponseBody> call= new RetrofitClint(context).apiServices.addPlace(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String value=response.body().string();

                        JSONObject jsonObject=new JSONObject(value);
                        String id=jsonObject.optString("id");
                        if (isImageUploaded){
                            addImage(context,id,name,multiPartBody);
                        }else {
                            ((BaseActivity)context).getSupportFragmentManager().popBackStack();
                            ((BaseActivity)context).getSupportFragmentManager().popBackStackImmediate();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else {
                    try {
                        String responseError=response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
            }
        });
    }

    @Override
    public void saveEditedDetails(Context context, RequestBody body, DetailListPojo detailListPojo,boolean isImageUploaded,RequestBody name,MultipartBody.Part multiPartBody) {
        Call<ResponseBody> call= new RetrofitClint(context).apiServices.updatePlace(detailListPojo.getId(),body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){


                        if (isImageUploaded){
                            addImage(context,detailListPojo.getId(),name,multiPartBody);
                        }else {
                            ((BaseActivity)context).getSupportFragmentManager().popBackStack();
                            ((BaseActivity)context).getSupportFragmentManager().popBackStackImmediate();
                        }




                }else {
                    try {
                        String responseError=response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
            }
        });
    }

    @Override
    public void addPlaceWithImage(Context context, RequestBody fileName, MultipartBody.Part file, RequestBody title, RequestBody latitude, RequestBody longitude, RequestBody description) {

        Call<ResponseBody> call= new RetrofitClint(context).apiServices.addPlaceWithImage(fileName,file,title);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String value=response.body().string();
                        Log.e("value success",value);
                        ((BaseActivity)context).getSupportFragmentManager().popBackStack();
                        ((BaseActivity)context).getSupportFragmentManager().popBackStackImmediate();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }else {
                    try {
                        String responseError=response.errorBody().string();
                        Log.e("value ",responseError
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.e("value success","Failed");
            }
        });
    }


    public void addImage(Context context,String id,RequestBody name,MultipartBody.Part multiPartBody){

        Call<ResponseBody> call= new RetrofitClint(context).apiServices.updatePlaceImage(id,name,multiPartBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String value=response.body().string();
                        Log.e("value success n",value);
                        ((BaseActivity)context).getSupportFragmentManager().popBackStack();
                        ((BaseActivity)context).getSupportFragmentManager().popBackStackImmediate();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }else {
                    try {
                        String responseError=response.errorBody().string();
                        Log.e("value n",responseError);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.e("value fail n","Failed");
            }
        });



    }



}
