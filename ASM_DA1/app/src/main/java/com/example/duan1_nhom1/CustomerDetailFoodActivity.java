package com.example.duan1_nhom1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.duan1_nhom1.dao.CartDAO;
import com.example.duan1_nhom1.modul.Cart;
import com.example.duan1_nhom1.modul.Food;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class CustomerDetailFoodActivity extends AppCompatActivity {
    private int soluong = 1;
    private TextView tvName, tvAddPopular, tvNumberPopular, tvPrice, tvDescriber;
    private ImageView imvPhoto, imvMinus, imvPlus;
    private Food food;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("list_cart");

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
        tvDescriber.setText(food.getMoTa());
        Glide.with(CustomerDetailFoodActivity.this).load(food.getHinhAnh()).into(imvPhoto);
        tvPrice.setText("$: "+food.getGia());
        //Bat su kien
        initListener();
    }

    //Anh xa
    private void initUi(){
        tvName = findViewById(R.id.tvNamePopularDetail);
        tvPrice = findViewById(R.id.tvPriceDetail);
        tvDescriber = findViewById(R.id.tvDescribePopularDetail);
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
                addPopular();
            }
        });
    }

    //Them vao gio hang
    private void addPopular() {
        CartDAO cartDAO = new CartDAO(getApplicationContext());
        String maKH = getIntent().getStringExtra("maKH");
        Cart cart = new Cart(maKH+new Date(), soluong, food.getGia()*soluong, food.getMaMA(), maKH);
        cartDAO.insert(cart);
        myRef.child(maKH + new Date()).setValue(cart);
        Intent intent = new Intent(CustomerDetailFoodActivity.this, CustomerActivity.class);
        intent.putExtra("maKH", maKH);
        Toast.makeText(getApplicationContext(), "Da them vao gio hang", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finishAffinity();
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
}