package com.example.duan1_nhom1;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.duan1_nhom1.modul.Food;

public class DetailFoodActivity extends AppCompatActivity {
    private long back;
    private int soluong = 1;
    private TextView tvName, tvAddPopular, tvNumberPopular, tvPrice;
    private ImageView imvPhoto, imvMinus, imvPlus;
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);
        //Anh xa
        initUi();
        //Lay popular tu HomeActivity
        Intent intent = getIntent();
        food = (Food) intent.getSerializableExtra("food");
        //Set data len view
        tvName.setText(food.getTenMA());
        Glide.with(DetailFoodActivity.this).load(food.getHinhAnh()).into(imvPhoto);
        tvPrice.setText("$: "+food.getGia());
        //Bat su kien
        initListener();
    }

    //Anh xa
    private void initUi(){
        tvName = findViewById(R.id.tvNamePopularDetail);
        tvPrice = findViewById(R.id.tvPriceDetail);
        imvPhoto = findViewById(R.id.imvPopularDetail);
        tvAddPopular = findViewById(R.id.tvAddDetail);
        tvNumberPopular = findViewById(R.id.tvTotalPopularDetail);
        imvMinus = findViewById(R.id.imvMinus);
        imvPlus = findViewById(R.id.imvPlus);
    }
    //Bat su kien
    private void initListener(){
        imvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusPopolar();
            }
        });

        imvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusPopular();
            }
        });

        tvAddPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addPopular();
                finish();
            }
        });
    }

    //Tao nut back
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            startActivity(new Intent(DetailFoodActivity.this, CustomerActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //Them vao gio hang
    private void addPopular() {
        Intent intent = new Intent(DetailFoodActivity.this, CustomerActivity.class);
        intent.putExtra("add_cart", food);
        Toast.makeText(getApplicationContext(), "Da thm vao gio hang", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    //Bot so luong
    private void minusPopular() {
        if(soluong <= 1){
            return;
        }
        soluong--;
        tvNumberPopular.setText(String.valueOf(soluong));
        tvPrice.setText("$: "+soluong*food.getGia());
    }

    //Them so luong
    private void plusPopolar() {
        soluong++;
        tvPrice.setText("$: "+soluong*food.getGia());
        tvNumberPopular.setText(String.valueOf(soluong));
    }

//    //Nhan tro lai 2 lan thoat app
//    @Override
//    public void onBackPressed() {
//        if(back + 2000 > System.currentTimeMillis()){
//            super.onBackPressed();
//            return;
//        }else {
//            Toast.makeText(DetailFoodActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
//        }
//        back = System.currentTimeMillis();
//    }
}