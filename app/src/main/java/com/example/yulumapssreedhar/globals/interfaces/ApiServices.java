package com.example.yulumapssreedhar.globals.interfaces;

import com.example.yulumapssreedhar.globals.constants.ConstantStrings;
import com.example.yulumapssreedhar.globals.constants.ConstantUrls;
import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiServices {

    @GET(ConstantUrls.PLACE)
    Call<ResponseBody> getAllLocationData();


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST(ConstantUrls.PLACE)
    Call<ResponseBody> addPlace(@Body RequestBody body);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PUT(ConstantUrls.PLACE + "/{id}")
    Call<ResponseBody> updatePlace(@Path(ConstantStrings.ID_KEY) String id,
                                   @Body RequestBody requestBody);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET(ConstantUrls.PLACE + "/{id}")
    Call<ResponseBody> getPlaceDetails(@Path("id") String id);

    @Multipart
    @Headers({"Content-Type: application/x-www-form-urlencoded;charset=UTF-8"})
    @POST(ConstantUrls.PLACE)
    Call<ResponseBody> addPlaceWithImage(@Part("file\"; filename=\"pp.jpeg\" ") RequestBody fileName ,
                                         @Part MultipartBody.Part file,
                                         @Body RequestBody requestBody);


    @Multipart
    @PUT(ConstantUrls.PLACE + "/{id}")
    Call<ResponseBody> updatePlaceImage(@Path(ConstantStrings.ID_KEY) String id,
                                        @Part("file\"; filename=\"pp.jpeg\" ") RequestBody fileName ,
                                        @Part MultipartBody.Part file);






}
