package com.example.yulumapssreedhar.view.fragments;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yulumapssreedhar.R;
import com.example.yulumapssreedhar.contract.DetailContract;
import com.example.yulumapssreedhar.databinding.DetailsFragmentBinding;
import com.example.yulumapssreedhar.globals.constants.ConstantUrls;
import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;
import com.example.yulumapssreedhar.presenter.DetailPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements DetailContract.DetailView {
    private ImageView imageView;
    private Bundle bundle;
    private DetailListPojo detailListPojo;
    private DetailsFragmentBinding binding;
    private DetailPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.details_fragment,container,false);
        View view=binding.getRoot();
        presenter=new DetailPresenter(this,getActivity());
        binding.setPresenter(presenter);
        return init(view);
    }

    private View init(View view){
        imageView=view.findViewById(R.id.user_image);
        bundle=getArguments();
        if (bundle!=null){
            detailListPojo=bundle.getParcelable("detailsPojo");
            if (detailListPojo!=null){

                binding.setDetailsModel(detailListPojo);

                Glide.with(getContext())
                        .load(ConstantUrls.BASE_URL+detailListPojo.getImage())
                        .error(R.drawable.map)
                        .centerCrop()
                        .into(imageView);

            }
        }

        return view;
    }

}
