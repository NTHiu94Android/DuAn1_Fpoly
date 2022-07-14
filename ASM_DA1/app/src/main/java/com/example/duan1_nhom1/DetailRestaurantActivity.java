package com.example.duan1_nhom1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duan1_nhom1.modul.Restaurant;

public class DetailRestaurantActivity extends AppCompatActivity {

    private Restaurant restaurant;
    private TextView tvNameRestaurant, tvAddressRestaurant, tvDescribeRestaurant;
    private ImageView imvRestaurant;
    private LinearLayout layout_QLDH, layout_QLMA, layout_LSDH, layout_ThongKe, layout_QLTK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restaurant);
        initUi();
        //Lay restaurant tu RestaurantOwnerActivity
        Intent intent = getIntent();
        restaurant = (Restaurant) intent.getSerializableExtra("restaurant");
        //Set thong tin len layout
        tvNameRestaurant.setText(restaurant.getTenNH());
        tvAddressRestaurant.setText(restaurant.getDiaChi());
        tvDescribeRestaurant.setText(restaurant.getMoTa());
        imvRestaurant.setImageResource(restaurant.getHinhAnh());
        //Bat su kien
        initListener();
    }
    //Anh xa
    private void initUi(){
        tvNameRestaurant = findViewById(R.id.tvNameRestaurantDetail);
        tvAddressRestaurant = findViewById(R.id.tvAddressRestaurantDetail);
        tvDescribeRestaurant = findViewById(R.id.tvDescribeRestaurantDetail);
        imvRestaurant = findViewById(R.id.imvRestaurantDetail);
        layout_QLDH = findViewById(R.id.layout_QLDH);
        layout_QLMA = findViewById(R.id.layout_QLMA);
        layout_LSDH = findViewById(R.id.layout_LSDH);
        layout_ThongKe = findViewById(R.id.layout_ThongKe);
        layout_QLTK = findViewById(R.id.layout_QLTK);
    }
    //Bat su kien
    private void initListener(){
        layout_QLDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        layout_QLMA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodManagerActivity.class);
                intent.putExtra("maNH", restaurant.getMaNH());
                startActivity(intent);
            }
        });
        layout_LSDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        layout_ThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        layout_QLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailRestaurantActivity.this, InforRestaurantOwnerActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            }
        });
    }
}