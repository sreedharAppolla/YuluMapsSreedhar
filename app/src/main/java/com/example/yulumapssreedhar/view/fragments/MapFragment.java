package com.example.yulumapssreedhar.view.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yulumapssreedhar.R;
import com.example.yulumapssreedhar.contract.MapFragmentContract;
import com.example.yulumapssreedhar.databinding.MapFragmentBinding;
import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;
import com.example.yulumapssreedhar.presenter.MapPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener,MapFragmentContract.MapFragmentView {
    private Bundle bundle;
    private ArrayList<DetailListPojo> detailListPojos;
    private GoogleMap map;
    private RelativeLayout saveDetails;
    private TextView saveText;
    private DetailListPojo detailPojo;
    private boolean isList=false;
    private boolean isEdit=false;
    private double latitude,longitude;
    private static final int TAG_CODE_PERMISSION_LOCATION=100;
    private MapFragmentBinding binding;
    private MapPresenter presenter;
    private SupportMapFragment supportMapFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.map_fragment,container,false);
        View view=binding.getRoot();
        presenter=new MapPresenter(this,getActivity());
        binding.setPresenter(presenter);

        saveDetails=view.findViewById(R.id.save_details);
        saveText=view.findViewById(R.id.save_text_title);
        if (detailListPojos == null) {
            detailListPojos = new ArrayList<>();
        }

        bundle=getArguments();
        if (bundle!=null){
            isList=bundle.getBoolean("isList");
            if (isList){
            detailListPojos=bundle.getParcelableArrayList("detailsList");
            }else {
                isEdit=bundle.getBoolean("isEdit");
                saveDetails.setVisibility(View.VISIBLE);
                if (isEdit){
                    saveText.setText("Update Details");
                    detailPojo=bundle.getParcelable("detailsPojo");
                }else {

                }

            }
        }

        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);


        saveDetails.setOnClickListener(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (isList){

        for (int i = 0; i < detailListPojos.size(); i++) {
            LatLng latLng = new LatLng(detailListPojos.get(i).getLatitude(), detailListPojos.get(i).getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(detailListPojos.get(i).getTitle())
                    .snippet("sub Title")
            );
        }

        }else {
            if (isEdit){
                latitude=detailPojo.getLatitude();
                longitude=detailPojo.getLongitude();
                LatLng latLng = new LatLng(detailPojo.getLatitude(), detailPojo.getLongitude());
                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(detailPojo.getTitle())
                        .snippet("sub Title"));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 28));
            }else {
                getCurrentLocation();
            }


            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    map.clear();
                    latitude=latLng.latitude;
                    longitude=latLng.longitude;
                    map.addMarker(new MarkerOptions().position(latLng).draggable(true));
                }
            });
            map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    map.clear();
                    latitude=latLng.latitude;
                    longitude=latLng.longitude;
                    map.addMarker(new MarkerOptions().position(latLng).draggable(true));

                }
            });

            map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {
                    latitude=marker.getPosition().latitude;
                    longitude=marker.getPosition().longitude;
                }

                @Override
                public void onMarkerDrag(Marker marker) {
                    latitude=marker.getPosition().latitude;
                    longitude=marker.getPosition().longitude;
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    latitude=marker.getPosition().latitude;
                    longitude=marker.getPosition().longitude;
                }
            });
        }

    }



    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    TAG_CODE_PERMISSION_LOCATION);
        }else {
            map.setMyLocationEnabled(true);
            LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = lm.getBestProvider(criteria, true);
                location = lm.getLastKnownLocation(provider);

            }
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            LatLng latLng = new LatLng(latitude,longitude);
            map.addMarker(new MarkerOptions().position(latLng));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case TAG_CODE_PERMISSION_LOCATION:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_details:
                if (detailPojo==null) {
                    detailPojo = new DetailListPojo("", "", latitude, longitude, "");
                    detailPojo.setLatitude(latitude);
                    detailPojo.setLongitude(longitude);
                    FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    EditInfoFragment editInfoFragment=new EditInfoFragment();
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("detailsPojo",detailPojo);
                    bundle.putBoolean("addPlace",true);
                    editInfoFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments_container,editInfoFragment,"editInfoFrag");
                    fragmentTransaction.addToBackStack("homeFrag");
                    fragmentTransaction.commit();
                }else {
                    getActivity().getSupportFragmentManager().popBackStack();
                    detailPojo.setLatitude(latitude);
                    detailPojo.setLongitude(longitude);
                    FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    EditInfoFragment editInfoFragment=new EditInfoFragment();
                    Bundle bundle=new Bundle();
                    bundle.putParcelable("detailsPojo",detailPojo);
                    bundle.putBoolean("addPlace",false);
                    editInfoFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragments_container,editInfoFragment,"editInfoFrag");
                    fragmentTransaction.addToBackStack("homeFrag");
                    fragmentTransaction.commit();
                }


                break;
        }
    }

    @Override
    public void onDestroy() {
        //map.clear();
        super.onDestroy();


    }
}
