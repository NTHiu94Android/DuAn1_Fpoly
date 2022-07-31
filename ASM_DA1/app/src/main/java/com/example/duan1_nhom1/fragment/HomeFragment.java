package com.example.duan1_nhom1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom1.R;
import com.example.duan1_nhom1.adapter.CategoriesAdapter;
import com.example.duan1_nhom1.adapter.PopularAdapter;
import com.example.duan1_nhom1.adapter.ProductAdapter;
import com.example.duan1_nhom1.dao.CustomerDAO;
import com.example.duan1_nhom1.dao.FoodDAO;
import com.example.duan1_nhom1.dao.FoodTypeDAO;
import com.example.duan1_nhom1.dao.RestaurantDAO;
import com.example.duan1_nhom1.modul.Customer;
import com.example.duan1_nhom1.modul.Food;
import com.example.duan1_nhom1.modul.Restaurant;
import com.example.duan1_nhom1.modul.TypeOfFood;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private TextView tvAccount;
    private Button btnOrderNow;
    private RecyclerView rcvCategories, rcvPopular, rcvProduct;
    private CategoriesAdapter categoriesAdapter;
    private PopularAdapter popularAdapter;
    private ProductAdapter productAdapter;
    private FoodTypeDAO foodTypeDAO;
    private FoodDAO foodDAO;
    private RestaurantDAO restaurantDAO;
    private ArrayList<TypeOfFood> list_categories;
    private ArrayList<Food> list_product;
    private ArrayList<Restaurant> list_restaurant;
    private String username = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnOrderNow = view.findViewById(R.id.btnBannerHome);
        tvAccount = view.findViewById(R.id.tvAccount);
        rcvCategories = view.findViewById(R.id.rcvCategories);
        rcvPopular = view.findViewById(R.id.rcvPopular);
        rcvProduct = view.findViewById(R.id.rcvProduct);
        //Lay username tu LoginActivity hien thi len textview
        Intent intent = getActivity().getIntent();
        username = intent.getStringExtra("username");
        if(intent.getStringExtra("username") == null){
            username = intent.getStringExtra("maKH");
        }
        //Set ma tai khoan len textview
        CustomerDAO customerDAO = new CustomerDAO(getActivity());
        Customer customer = customerDAO.getCustomer(username);
        tvAccount.setText(customer.getTenKH());
        btnOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.viewPager, CartFargment.newInstance()).commit();
            }
        });
        //Set data len recycleview
        setRcvCategories();
        setRcvPopular();
        setRcvProduct();

        return view;
    }
    //Set data len rcvCategories
    private void setRcvCategories(){
        foodTypeDAO = new FoodTypeDAO(getActivity());
        list_categories = foodTypeDAO.getAllFoodType();
        categoriesAdapter = new CategoriesAdapter();
        categoriesAdapter.getData(list_categories, getActivity(), username);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rcvCategories.setAdapter(categoriesAdapter);
        rcvCategories.setLayoutManager(manager);
    }
    //Set data lem rcvPopular
    private void setRcvPopular(){
        restaurantDAO = new RestaurantDAO(getActivity());
        list_restaurant = restaurantDAO.getAllRestaurant();
        popularAdapter = new PopularAdapter();
        popularAdapter.getData(list_restaurant, getActivity(), username);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rcvPopular.setLayoutManager(manager);
        rcvPopular.setAdapter(popularAdapter);
    }
    //Set data len rcvProduct
    private void setRcvProduct(){
        foodDAO = new FoodDAO(getActivity());
        list_product = foodDAO.getAllFood();
        productAdapter = new ProductAdapter();
        productAdapter.getData(list_product, getActivity(), username);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rcvProduct.setLayoutManager(manager);
        rcvProduct.setAdapter(productAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        //Lay username tu CartActivity hien thi len textview
        Intent intent = getActivity().getIntent();
        Customer customer = (Customer) intent.getSerializableExtra("object");
    }
}