package com.example.duan1_nhom1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1_nhom1.dao.CartDAO;
import com.example.duan1_nhom1.dao.CustomerDAO;
import com.example.duan1_nhom1.dao.FoodDAO;
import com.example.duan1_nhom1.dao.OrderDAO;
import com.example.duan1_nhom1.dao.RestaurantDAO;
import com.example.duan1_nhom1.dao.RestaurantOwnerDAO;
import com.example.duan1_nhom1.modul.Cart;
import com.example.duan1_nhom1.modul.Customer;
import com.example.duan1_nhom1.modul.Food;
import com.example.duan1_nhom1.modul.Order;
import com.example.duan1_nhom1.modul.Restaurant;
import com.example.duan1_nhom1.modul.RestaurantOwners;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private CheckBox chkRemember;
    private CustomerDAO customerDAO;
    private RestaurantOwnerDAO restaurantOwnerDAO;
    private RestaurantDAO restaurantDAO;
    private FoodDAO foodDAO;
    private CartDAO cartDAO;
    private OrderDAO orderDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Anh xa
        edtUser = findViewById(R.id.edtUsername);
        edtPass = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUpLogin);
        chkRemember = findViewById(R.id.chkRemember);
        //Tao lop DAO
        customerDAO = new CustomerDAO(getApplicationContext());
        restaurantOwnerDAO = new RestaurantOwnerDAO(getApplicationContext());
        restaurantDAO = new RestaurantDAO(getApplicationContext());
        foodDAO = new FoodDAO(getApplicationContext());
        cartDAO = new CartDAO(getApplicationContext());
        orderDAO = new OrderDAO(getApplicationContext());
        //Lay du lieu tu firebase ve sqlite
        getCustomerFromFirebase();
        getRestaurantOwnweFromFirebase();
        getRestaurantFromFirebase();
        getFoodFromFirebase();
        getCartFromFirebase();
        getOrderFromFirebase();
        //Luu thong tin dang nhap
        restoringPreferences();
        //Bat su kien
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreferences();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
    //Set thong tin dang nhap len edt
    private void restoringPreferences() {
        SharedPreferences preferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        boolean chk = preferences.getBoolean("check", false);
        if(chk){
            String user = preferences.getString("user", "");
            String pass = preferences.getString("pass", "");
            chk = true;
            edtUser.setText(user);
            edtPass.setText(pass);
        }
        chkRemember.setChecked(chk);
    }
    //Ham luu thong tin dang nhap
    private void savePreferences() {
        Intent intent;
        String user = edtUser.getText().toString();
        String pass = edtPass.getText().toString();
        boolean chk = chkRemember.isChecked();
        SharedPreferences preferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(user.equals("")||pass.equals(""))
            Toast.makeText(LoginActivity.this, "Vui lòng nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
        else{
            Boolean checkuserpassCustomer = customerDAO.checkusernamepassword(user, pass);
            Boolean checkuserpassRestaurantOwners = restaurantOwnerDAO.checkusernamepassword(user, pass);
            if(checkuserpassCustomer){//Neu tai khoan trong bang khach hang
                //Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                if(chk){
                    editor.putString("user", user);
                    editor.putString("pass", pass);
                }else {
                    editor.clear();
                }
                editor.putBoolean("check", chk);
                editor.apply();
                //put doi tuong customer vào CustomerActivity
                intent = new Intent(getApplicationContext(), CustomerActivity.class);
                Customer customer = customerDAO.getCustomer(user);
                intent.putExtra("object", customer);
                intent.putExtra("maKH", user);
                startActivity(intent);
            }else if(checkuserpassRestaurantOwners){//Neu tai khoan trong bang chu nha hang
                if(chk){
                    editor.putString("user", user);
                    editor.putString("pass", pass);
                }else {
                    editor.clear();
                }
                editor.putBoolean("check", chk);
                editor.apply();
                //put doi tuong restaurant_owners vào RestaurantOwnersActivity
                intent = new Intent(getApplicationContext(), RestaurantOwnersActivity.class);
                RestaurantOwners restaurantOwners = restaurantOwnerDAO.getRestaurantOwner(user);
                intent.putExtra("object", restaurantOwners);
                intent.putExtra("username", user);
                startActivity(intent);
            } else{
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    //Lay du lieu tu firebase add vao sqlite
    private void getCustomerFromFirebase(){
        if(customerDAO.getAllCustomer().size()>0){
            customerDAO.getAllCustomer().clear();
            customerDAO.deleteAll();
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_customer");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(customerDAO.getAllCustomer().size() == 0){
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        Customer customer = postSnapshot.getValue(Customer.class);
                        customerDAO.insertCustomer(customer);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(LoginActivity.this, "Load list_customer fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRestaurantOwnweFromFirebase(){
        if(restaurantOwnerDAO.getAllRestaurantOwner().size()>0){
            restaurantOwnerDAO.getAllRestaurantOwner().clear();
            restaurantOwnerDAO.deleteAll();
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_restaurant_owner");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(restaurantOwnerDAO.getAllRestaurantOwner().size() == 0){
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        RestaurantOwners restaurantOwners = postSnapshot.getValue(RestaurantOwners.class);
                        restaurantOwnerDAO.insertRestaurantOwner(restaurantOwners);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(LoginActivity.this, "Load list_restaurant_owner fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRestaurantFromFirebase(){
        if(restaurantDAO.getAllRestaurant().size()>0){
            restaurantDAO.getAllRestaurant().clear();
            restaurantDAO.deleteAllRestaurant();
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_restaurant");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(restaurantDAO.getAllRestaurant().size() == 0){
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        Restaurant restaurant = postSnapshot.getValue(Restaurant.class);
                        restaurantDAO.insert(restaurant);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(LoginActivity.this, "Load list_restaurant fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getFoodFromFirebase(){
        if(foodDAO.getAllFood().size()>0){
            foodDAO.getAllFood().clear();
            foodDAO.deleteAll();
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_food");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(foodDAO.getAllFood().size() == 0){
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        Food food = postSnapshot.getValue(Food.class);
                        foodDAO.insert(food);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(LoginActivity.this, "Load list_food fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getCartFromFirebase(){
        if(cartDAO.getAllCart().size()>0){
            cartDAO.getAllCart().clear();
            cartDAO.deleteAll();
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_cart");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(cartDAO.getAllCart().size() == 0){
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        Cart cart = postSnapshot.getValue(Cart.class);
                        cartDAO.insert(cart);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(LoginActivity.this, "Load list_cart fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOrderFromFirebase(){
        if(orderDAO.getAllOrder().size()>0){
            orderDAO.getAllOrder().clear();
            orderDAO.deleteAll();
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_order");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(orderDAO.getAllOrder().size() == 0){
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        // TODO: handle the post
                        Order order = postSnapshot.getValue(Order.class);
                        orderDAO.insert(order);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(LoginActivity.this, "Load list_order fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Lay du lieu tu firebase ve sqlite
        getCustomerFromFirebase();
        getRestaurantOwnweFromFirebase();
        getRestaurantFromFirebase();
        getFoodFromFirebase();
        getOrderFromFirebase();
    }
}