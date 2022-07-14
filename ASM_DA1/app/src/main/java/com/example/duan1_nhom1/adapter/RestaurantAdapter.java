package com.example.duan1_nhom1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.duan1_nhom1.R;
import com.example.duan1_nhom1.modul.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter extends BaseAdapter {
    private ArrayList<Restaurant> list;
    private Context context;

    public RestaurantAdapter(ArrayList<Restaurant> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private static class ViewHolder{
        TextView tvNameRestaurant, tvAddressRestaurant;
        ImageView imvRestaurant;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if(view == null){
            view = inflater.inflate(R.layout.item_restaurant, viewGroup, false);
            holder = new ViewHolder();
            holder.tvNameRestaurant = view.findViewById(R.id.tvNameRestaurantItem);
            holder.tvAddressRestaurant = view.findViewById(R.id.tvAddressRestaurantItem);
            holder.imvRestaurant = view.findViewById(R.id.imvRestaurantItem);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.imvRestaurant.setImageResource(list.get(i).getHinhAnh());
        holder.tvNameRestaurant.setText(list.get(i).getTenNH());
        holder.tvAddressRestaurant.setText(list.get(i).getDiaChi());

        return view;
    }
}
