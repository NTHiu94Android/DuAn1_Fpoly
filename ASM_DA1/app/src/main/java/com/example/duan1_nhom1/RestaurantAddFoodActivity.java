package com.example.duan1_nhom1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan1_nhom1.dao.FoodDAO;
import com.example.duan1_nhom1.dao.FoodTypeDAO;
import com.example.duan1_nhom1.modul.Food;
import com.example.duan1_nhom1.modul.TypeOfFood;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RestaurantAddFoodActivity extends AppCompatActivity {

    private EditText edtName, edtDescribe, edtPrice;
    private Spinner spinner;
    private ImageView imv;
    private Button btnAdd, btnCancel;
    private FoodTypeDAO foodTypeDAO;

    private String maNH = "";

    private UploadTask uploadTask;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://duan1---fpoly.appspot.com");
    private StorageReference mountainsRef;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("list_food");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        initUi();

        Intent intent = getIntent();
        maNH = intent.getStringExtra("maNH");
        foodTypeDAO = new FoodTypeDAO(RestaurantAddFoodActivity.this);

        addSpinner();
        initListener();
    }

    //Bat su kien
    private void initListener(){
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pick = new Intent(Intent.ACTION_GET_CONTENT);
                pick.setType("image/*");
                Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent choser = Intent.createChooser(pick, "Select option");
                choser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{photo});
                startActivityForResult(choser, 999);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFirebaseStogare();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtName.setText("");
                edtPrice.setText("");
                edtDescribe.setText("");
            }
        });
    }
    //Anh xa view
    private void initUi() {
        edtName = findViewById(R.id.edtAddNameFood);
        edtDescribe = findViewById(R.id.edtAddDescribeFood);
        edtPrice = findViewById(R.id.edtAddPriceFood);
        spinner = findViewById(R.id.spnAddFoodType);
        imv = findViewById(R.id.imvAddFood);
        btnAdd = findViewById(R.id.btnAddFood);
        btnCancel = findViewById(R.id.btnCancelAddFood);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK){
            if (data.getExtras() != null){
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imv.setImageBitmap(imageBitmap);
            }else {
                Uri uri = data.getData();
                imv.setImageURI(uri);
            }
        }
    }

    //Insert food len firebase
    private void uploadFirebaseStogare() {
        //Kiem loi nhap di lieu
        if(edtName.getText().toString().isEmpty() || edtPrice.getText().toString().isEmpty() || edtDescribe.getText().toString().isEmpty()){
            Toast.makeText(RestaurantAddFoodActivity.this, "Please enter enough information", Toast.LENGTH_SHORT).show();
            return;
        }
        Date date = new Date();
        mountainsRef = storageRef.child("images/"+date+".jpg");
        imv.setDrawingCacheEnabled(true);
        imv.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imv.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("Get_Url", String.valueOf(uri));
                        String maMA = maNH+date;
                        String tenMA = edtName.getText().toString();
                        String mota = edtDescribe.getText().toString();
                        String hinhAnh = String.valueOf(uri);
                        double gia = Double.parseDouble(edtPrice.getText().toString());
                        //Lay ma loai mon an
                        HashMap<String, String> hashMap = (HashMap<String, String>) spinner.getSelectedItem();
                        int maLoaiMA = Integer.parseInt(hashMap.get("IdFoodType"));

                        Food food = new Food(maMA, tenMA, mota, hinhAnh, gia, maNH, maLoaiMA);
                        FoodDAO foodDAO = new FoodDAO(RestaurantAddFoodActivity.this);
                        foodDAO.insert(food);
                        myRef.child(String.valueOf(food.getMaMA())).setValue(food, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error == null){
                                    Toast.makeText(RestaurantAddFoodActivity.this, "Add food succsess", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), RestaurantFoodManagerActivity.class);
                                    intent.putExtra("maNH", maNH);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(RestaurantAddFoodActivity.this, "Add food fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    //Lay ten ma loai mon an
    private void addSpinner() {
        ArrayList<TypeOfFood> list = foodTypeDAO.getAllFoodType();
        ArrayList<HashMap<String, String>> listHM = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("NameFoodType", list.get(i).getTenLoaiMA());
            hashMap.put("IdFoodType", String.valueOf(list.get(i).getMaLoaiMA()));
            listHM.add(hashMap);
        }
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), listHM,
                R.layout.item_spn_type_food, new String[]{"NameFoodType"}, new int[]{R.id.tvSpnTypeFood});
        spinner.setAdapter(adapter);
    }
}