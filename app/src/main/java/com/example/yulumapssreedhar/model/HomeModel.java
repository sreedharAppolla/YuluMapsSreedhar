package com.example.yulumapssreedhar.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.example.yulumapssreedhar.R;
import com.example.yulumapssreedhar.contract.HomeFragmentContract;
import com.example.yulumapssreedhar.databases.OfflineStorageDatabase;
import com.example.yulumapssreedhar.globals.constants.RetrofitClint;
import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;
import com.example.yulumapssreedhar.view.adapters.DetailListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeModel implements HomeFragmentContract.HomeModel {
    private ArrayList<DetailListPojo> detailListPojos;
    private RecyclerView recyclerView;
    private DetailListAdapter detailListAdapter;
    private OfflineStorageDatabase offlineStorageDatabase;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    public ArrayList<DetailListPojo>  callApi( View view, Context context) {
        offlineStorageDatabase=new OfflineStorageDatabase(context);
        sqLiteDatabase=offlineStorageDatabase.getWritableDatabase();

        detailListPojos=new ArrayList<>();
        recyclerView=view.findViewById(R.id.details_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        detailListAdapter=new DetailListAdapter(context,detailListPojos);
        recyclerView.setAdapter(detailListAdapter);


        Call<ResponseBody> call = new RetrofitClint(context).apiServices.getAllLocationData();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    String data = null;

                    try {
                        data = response.body().string();
                        if (offlineStorageDatabase!=null){
                            offlineStorageDatabase.clearRecords(sqLiteDatabase);
                            offlineStorageDatabase.insertDetails(data,sqLiteDatabase);
                        }
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(data);


                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                double latitude = jsonObject.getDouble("latitude");
                                double longitude = jsonObject.getDouble("longitude");
                                String baseImage = jsonObject.optString("imageUrl", "/");
                                String image = baseImage.substring(1);
                                detailListPojos.add(new DetailListPojo(id, title, latitude, longitude, image));
                                detailListAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (offlineStorageDatabase!=null){
                    Cursor cursor=offlineStorageDatabase.fetechDetails(sqLiteDatabase);
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            do {

                                String data=cursor.getString(0);
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(data);


                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String id = jsonObject.getString("id");
                                        String title = jsonObject.getString("title");
                                        double latitude = jsonObject.getDouble("latitude");
                                        double longitude = jsonObject.getDouble("longitude");
                                        String baseImage = jsonObject.optString("imageUrl", "/");
                                        String image = baseImage.substring(1);
                                        detailListPojos.add(new DetailListPojo(id, title, latitude, longitude, image));
                                        detailListAdapter.notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            } while (cursor.moveToNext());
                        }
                    }


                }
            }
        });




return detailListPojos;
    }
}
