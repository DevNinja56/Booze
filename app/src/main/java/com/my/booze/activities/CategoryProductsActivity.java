package com.my.booze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my.booze.R;
import com.my.booze.adapters.NestedCategoryAdapter;
import com.my.booze.adapters.ProductAdapter;
import com.my.booze.models.CartProductsCountResponseModel;
import com.my.booze.models.NestedCategoryModel;
import com.my.booze.models.ProductDataModel;
import com.my.booze.models.ProductsResponseModel;
import com.my.booze.network.GetDataService;
import com.my.booze.network.RetrofitClientInstance;
import com.my.booze.utilities.Utilities;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProductsActivity extends AppCompatActivity {

    GetDataService service;
    RecyclerView  recyclerview_Products, recyclerview_NestedCategory;
    ArrayList<ProductDataModel> products;
    ProductAdapter productAdapter;
    ArrayList<NestedCategoryModel> nestedCategory;
    NestedCategoryAdapter nestedCategoryAdapter;
    String product_Category;
    public AVLoadingIndicatorView avi;
    int cartItemsCount;
    RelativeLayout cart_bgRound;
    ImageView btn_Cart, backsign;
    TextView user_CartItemsCount;
    String user_Id;
    LinearLayout section_NestedCategory;
    TextView category_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        user_Id = Utilities.getString(CategoryProductsActivity.this, "user_Id");
        product_Category = getIntent().getStringExtra("product_Category");
        section_NestedCategory = findViewById(R.id.section_NestedCategory);
        category_Name = findViewById(R.id.category_Name);
        backsign = findViewById(R.id.backsign);
        avi = findViewById(R.id.avi);
        category_Name.setText(product_Category);
        startAnim();
        backsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerview_NestedCategory = findViewById(R.id.recyclerview_NestedCategory);
        nestedCategory = new ArrayList<NestedCategoryModel>();
        loadNestedCategories();

        btn_Cart = findViewById(R.id.btn_Cart);
        user_CartItemsCount = findViewById(R.id.user_CartItemsCount);
        cart_bgRound = findViewById(R.id.cart_bgRound);
        recyclerview_Products = findViewById(R.id.recyclerview_Products);
        recyclerview_Products.setNestedScrollingEnabled(false);
        products = new ArrayList<ProductDataModel>();
//        products.add(new ProductDataModel(1, R.drawable.ic_botel, "Campo Viejo", "Tempranilo", "$ 12.99"));
//        products.add(new ProductDataModel(1, R.drawable.ic_botel, "Campo Viejo", "Tempranilo", "$ 12.99"));
//        products.add(new ProductDataModel(1, R.drawable.ic_botel, "Campo Viejo", "Tempranilo", "$ 12.99"));
//        products.add(new ProductDataModel(1, R.drawable.ic_botel, "Campo Viejo", "Tempranilo", "$ 12.99"));
//        products.add(new ProductDataModel(1, R.drawable.ic_botel, "Campo Viejo", "Tempranilo", "$ 12.99"));
        recyclerview_Products.setLayoutManager(new LinearLayoutManager(CategoryProductsActivity.this));
        recyclerview_Products.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter("vertical", products, CategoryProductsActivity.this);
        recyclerview_Products.setAdapter(productAdapter);
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                int reminderId = products.get(position).getId();
//                Toast.makeText(getActivity(), "12", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CategoryProductsActivity.this, ProductDetailsActivity.class);
                startActivity(intent);
            }
        });
        checkCart();
        loadProducts();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkCart();
    }

    public void loadProducts() {
//        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        RequestBody parameter = RequestBody.create(MediaType.parse("text/plain"),product_Category);
        Call<ProductsResponseModel> call = service.getCategoryProducts(product_Category);

        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call, Response<ProductsResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                if (!String.valueOf(status).isEmpty() && status == 200){
                    List dataList = response.body().getData();
                    if (dataList == null){
                        Toast.makeText(CategoryProductsActivity.this, "No Product Found", Toast.LENGTH_LONG).show();
                    }
                    else {
                        showProducts(recyclerview_Products, dataList);
                    }
                }
                else if (!String.valueOf(status).isEmpty() && status == 400){
                    stopAnim();
//                    Toast.makeText(CategoryProductsActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ProductsResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
                stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(CategoryProductsActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showProducts(RecyclerView recyclerView, List<ProductDataModel> products) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoryProductsActivity.this));
        productAdapter = new ProductAdapter("vertical", products, CategoryProductsActivity.this);
        recyclerView.setAdapter(productAdapter);
        stopAnim();
    }

    public void loadNestedCategories() {
        Toast.makeText(this, ""+product_Category, Toast.LENGTH_SHORT).show();
        if(product_Category.equals(""+getResources().getString(R.string.Wine))) {
            nestedCategory.add(new NestedCategoryModel(2, R.drawable.white_wine,
                    getResources().getString(R.string.Wine), ""+getResources().getString(R.string.White_Vine)));
            nestedCategory.add(new NestedCategoryModel(3, R.drawable.glass_ic, getResources().getString(R.string.Wine),
                    ""+getResources().getString(R.string.Red_Vine)));
            nestedCategory.add(new NestedCategoryModel(1, R.drawable.glass_ic,getResources().getString(R.string.Wine),
                    ""+getResources().getString(R.string.Champagne)));
            nestedCategory.add(new NestedCategoryModel(4, R.drawable.glass_ic, getResources().getString(R.string.Wine),
                    ""+getResources().getString(R.string.Rose)));
            nestedCategory.add(new NestedCategoryModel(5, R.drawable.glass_ic, getResources().getString(R.string.Wine),
                    ""+getResources().getString(R.string.Champagne)));
        } else if(product_Category.equals(""+getResources().getString(R.string.Liquor))) {
            nestedCategory.add(new NestedCategoryModel(2, R.drawable.white_wine,
                    getResources().getString(R.string.Wine), ""+getResources().getString(R.string.Congnac)));
            nestedCategory.add(new NestedCategoryModel(3, R.drawable.glass_ic, getResources().getString(R.string.Gin),
                    ""+getResources().getString(R.string.Red_Vine)));
            nestedCategory.add(new NestedCategoryModel(1, R.drawable.glass_ic,getResources().getString(R.string.Rhum),
                    ""+getResources().getString(R.string.Champagne)));
            nestedCategory.add(new NestedCategoryModel(4, R.drawable.glass_ic, getResources().getString(R.string.Tequila),
                    ""+getResources().getString(R.string.Rose)));
            nestedCategory.add(new NestedCategoryModel(5, R.drawable.glass_ic, getResources().getString(R.string.Vodka),
                    ""+getResources().getString(R.string.Champagne)));
            nestedCategory.add(new NestedCategoryModel(6, R.drawable.glass_ic, getResources().getString(R.string.Whishey_Blend),
                    ""+getResources().getString(R.string.Champagne)));
        } else if(product_Category.equals(""+getResources().getString(R.string.Mixers))) {
            nestedCategory.add(new NestedCategoryModel(2, R.drawable.white_wine,
                    getResources().getString(R.string.Wine), ""+getResources().getString(R.string.Soft_Drink)));
            nestedCategory.add(new NestedCategoryModel(3, R.drawable.glass_ic, getResources().getString(R.string.Juice),
                    ""+getResources().getString(R.string.Red_Vine)));
            nestedCategory.add(new NestedCategoryModel(1, R.drawable.glass_ic,getResources().getString(R.string.Mixers),
                    ""+getResources().getString(R.string.Champagne)));
        } else if(product_Category.equals(""+getResources().getString(R.string.Smokes))) {
            nestedCategory.add(new NestedCategoryModel(2, R.drawable.white_wine,
                    getResources().getString(R.string.Wine), ""+getResources().getString(R.string.Cigarette)));
            nestedCategory.add(new NestedCategoryModel(3, R.drawable.glass_ic, getResources().getString(R.string.Hookah_Flavor),
                    ""+getResources().getString(R.string.Red_Vine)));
            nestedCategory.add(new NestedCategoryModel(1, R.drawable.glass_ic,getResources().getString(R.string.Hookah_Charcoal),
                    ""+getResources().getString(R.string.Champagne)));
            nestedCategory.add(new NestedCategoryModel(4, R.drawable.glass_ic, getResources().getString(R.string.Cigar),
                    ""+getResources().getString(R.string.Rose)));
            nestedCategory.add(new NestedCategoryModel(5, R.drawable.glass_ic, getResources().getString(R.string.Hookah_Accessories),
                    ""+getResources().getString(R.string.Champagne)));
            nestedCategory.add(new NestedCategoryModel(6, R.drawable.glass_ic, getResources().getString(R.string.Vape_Refill),
                    ""+getResources().getString(R.string.Champagne)));
        } else {
            section_NestedCategory.setVisibility(View.GONE);
        }

        recyclerview_NestedCategory.setLayoutManager(new LinearLayoutManager(CategoryProductsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerview_NestedCategory.setItemAnimator(new DefaultItemAnimator());
        nestedCategoryAdapter = new NestedCategoryAdapter(nestedCategory, CategoryProductsActivity.this);
        recyclerview_NestedCategory.setAdapter(nestedCategoryAdapter);
    }

    void startAnim(){
        avi.show();
        // or avi.smoothToShow();
    }

    void stopAnim(){
        avi.hide();
        // or avi.smoothToHide();
    }

    public void checkCart() {
        Call<CartProductsCountResponseModel> call = service.getCartProductsCount(user_Id);
        call.enqueue(new Callback<CartProductsCountResponseModel>() {
            @Override
            public void onResponse(Call<CartProductsCountResponseModel> call, Response<CartProductsCountResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                if (status == 200){
                    int cartItemsCount = response.body().getCart_ProductsCount();
                    if(cartItemsCount == 0) {
                        cart_bgRound.setVisibility(View.GONE);
                    } else if (cartItemsCount > 0) {
                        cart_bgRound.setVisibility(View.VISIBLE);
                        user_CartItemsCount.setText(""+cartItemsCount);
                    }
                }
                else if (status == 400){
//                    stopAnim();
                    cart_bgRound.setVisibility(View.GONE);
//                    Toast.makeText(CategoryProductsActivity.this, "Cart Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<CartProductsCountResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                stopAnim();
                Toast.makeText(CategoryProductsActivity.this, t.getMessage() + "Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }


}