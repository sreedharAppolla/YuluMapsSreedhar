package com.example.yulumapssreedhar.view.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yulumapssreedhar.R;
import com.example.yulumapssreedhar.globals.constants.ConstantUrls;
import com.example.yulumapssreedhar.model.pojoClasses.DetailListPojo;
import com.example.yulumapssreedhar.view.fragments.DetailsFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<DetailListPojo> detailListPojos;

    public DetailListAdapter(Context context, ArrayList<DetailListPojo> detailListPojos) {
        this.context = context;
        this.detailListPojos = detailListPojos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.detail_list_cell,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(detailListPojos.get(position).getTitle());
        Glide.with(context)
                .load(ConstantUrls.BASE_URL+detailListPojos.get(position).getImage())
                .error(R.drawable.map)
                .centerCrop()
                .into(holder.imageView);

        holder.cellClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                DetailsFragment detailsFragment=new DetailsFragment();
                Bundle bundle=new Bundle();
                bundle.putParcelable("detailsPojo",detailListPojos.get(position));
                detailsFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragments_container,detailsFragment,"detailsFrag" );
                fragmentTransaction.addToBackStack("homeFrag");
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return detailListPojos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView title,subTitle;
        RelativeLayout cellClick;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.user_image);
            title=itemView.findViewById(R.id.title);
            subTitle=itemView.findViewById(R.id.sub_title);
            cellClick=itemView.findViewById(R.id.cell_click);


        }
    }
}
