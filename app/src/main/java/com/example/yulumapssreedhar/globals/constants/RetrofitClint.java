package com.example.yulumapssreedhar.globals.constants;

import android.content.Context;

import com.example.yulumapssreedhar.globals.interfaces.ApiServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClint {
    private Context context;
    public ApiServices apiServices;

    public RetrofitClint(Context context) {
        this.context = context;
        this.apiServices = createRetrofit();
    }

    private ApiServices createRetrofit(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ConstantUrls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices apiServices=retrofit.create(ApiServices.class);
        return apiServices;
    }
}
