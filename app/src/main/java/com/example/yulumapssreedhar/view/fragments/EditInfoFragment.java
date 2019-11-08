package com.example.yulumapssreedhar.view.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yulumapssreedhar.R;
import com.example.yulumapssreedhar.contract.EditInfoContract;
import com.example.yulumapssreedhar.databinding.EditInfoFragmentBinding;
import com.example.yulumapssreedhar.globals.constants.ConstantStrings;
import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;
import com.example.yulumapssreedhar.presenter.EditInfoPresenter;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditInfoFragment extends Fragment implements EditInfoContract.EditInfoView {

    private Bundle bundle;
    private DetailListPojo detailListPojo;
    private EditInfoFragmentBinding binding;
    private EditInfoPresenter presenter;
    private boolean addPlace = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_info_fragment, container, false);
        View view = binding.getRoot();
        presenter = new EditInfoPresenter(this, getActivity());

        return init(view);
    }

    private View init(View view) {

        bundle = getArguments();
        if (bundle != null) {
            detailListPojo = bundle.getParcelable("detailsPojo");
            addPlace = bundle.getBoolean("addPlace");
            binding.setIsAddDetails(addPlace);
            binding.setDetailsListPojo(detailListPojo);
            binding.setDetailsListPojoRef(detailListPojo);
            binding.setPresenter(presenter);


        }
        return view;
    }


    @Override
    public void uploadImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), ConstantStrings.IMAGE_REQUEST_CODE);
            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), ConstantStrings.IMAGE_REQUEST_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 1) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 11);
            }


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();


            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String mediaPath = cursor.getString(columnIndex);


            cursor.close();
            File file1 = new File(mediaPath);
            File file = null;
            try {
                file = new Compressor(getContext()).compressToFile(file1);

                RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), fileReqBody);
                RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image");
                presenter.uploadImage(true, description, body);
                detailListPojo.setImage(file.getName());
                binding.setDetailsListPojo(detailListPojo);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
