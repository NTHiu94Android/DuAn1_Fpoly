package com.example.duan1_nhom1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.duan1_nhom1.modul.Food;

public class RestaurantDetailFoodActivity extends AppCompatActivity {
    private ImageView imv;
    private TextView tvName, tvDescribe, tvPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food_restaurant);
        initUi();
        //Lay Food tu FoodAdapter
        Intent intent = getIntent();
        Food food = (Food) intent.getSerializableExtra("food");
        //Set du lieu len view
        Glide.with(RestaurantDetailFoodActivity.this).load(food.getHinhAnh()).into(imv);
        tvName.setText(food.getTenMA());
        tvDescribe.setText(food.getMoTa());
        tvPrice.setText("Gia: " + food.getGia() + "VND");
    }
    //Anh xa
    private void initUi() {
        imv = findViewById(R.id.imvFoodDetail);
        tvName = findViewById(R.id.tvNameFoodDetail);
        tvDescribe = findViewById(R.id.tvDescribeFoodDetail);
        tvPrice = findViewById(R.id.tvPriceFoodDetail);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}