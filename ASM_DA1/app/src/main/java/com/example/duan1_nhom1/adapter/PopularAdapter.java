package com.example.duan1_nhom1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duan1_nhom1.R;
import com.example.duan1_nhom1.modul.Restaurant;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder>{
    private ArrayList<Restaurant> list;
    private Context context;

    public void getData(ArrayList<Restaurant> list, Context context){
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        Restaurant restaurant = list.get(position);

        holder.tvName.setText(restaurant.getTenNH());
        holder.tvAddress.setText(restaurant.getDiaChi());
        holder.imv.setImageResource(restaurant.getHinhAnh());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //((CustomerActivity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(list.size()>0){
            return list.size();
        }
        return 0;
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvAddress;
        private ImageView imv;
        private LinearLayout layout;
        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemNamePopular);
            tvAddress = itemView.findViewById(R.id.tvItemAddressPopular);
            imv = itemView.findViewById(R.id.imvItemPopular);
            layout = itemView.findViewById(R.id.layoutPopularItem);
        }
    }
}
