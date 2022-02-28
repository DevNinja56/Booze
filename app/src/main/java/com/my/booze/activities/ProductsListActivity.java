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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsListActivity extends AppCompatActivity {

    GetDataService service;
    RecyclerView recyclerview_Products;
    ArrayList<ProductDataModel> products;
    ProductAdapter productAdapter;
    String product_Category, product_SubCategory;
    public AVLoadingIndicatorView avi;
    TextView text_SubCategoryName, text_NoProductAvailable;
    ImageView btn_Cart;
    TextView user_CartItemsCount;
    RelativeLayout cart_bgRound;
    String user_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        user_Id = Utilities.getString(ProductsListActivity.this, "user_Id");
        product_Category = getIntent().getStringExtra("CATEGORY_NAME");
        product_SubCategory = getIntent().getStringExtra("NESTED_CATEGORY_NAME");
        btn_Cart = findViewById(R.id.btn_Cart);
        cart_bgRound = findViewById(R.id.cart_bgRound);
        user_CartItemsCount = findViewById(R.id.user_CartItemsCount);
        avi = findViewById(R.id.avi);
        text_NoProductAvailable = findViewById(R.id.text_NoProductAvailable);
        text_SubCategoryName = findViewById(R.id.text_SubCategoryName);
        text_SubCategoryName.setText(product_SubCategory);
        startAnim();
        recyclerview_Products = findViewById(R.id.recyclerview_Products);
        recyclerview_Products.setNestedScrollingEnabled(false);
        products = new ArrayList<ProductDataModel>();
        recyclerview_Products.setLayoutManager(new LinearLayoutManager(ProductsListActivity.this));
        recyclerview_Products.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter("vertical", products, ProductsListActivity.this);
        recyclerview_Products.setAdapter(productAdapter);
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        checkCart();
        loadProducts();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkCart();
    }


    public void loadProducts() {
//        RequestBody parameter = RequestBody.create(MediaType.parse("text/plain"),product_Category);
        Call<ProductsResponseModel> call = service.getSubCategoryProducts(product_Category, product_SubCategory);

        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call, Response<ProductsResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                Log.i("checkingSubCateg", ""+status);

                if (status == 200){

                    List dataList = response.body().getData();
                    Log.i("checkingSubCateg", ""+dataList);
//                    Log.i("checkingSubCateg", ""+status);
                    if (dataList == null || dataList.size() == 0){
                        stopAnim();
                        text_NoProductAvailable.setVisibility(View.VISIBLE);
//                        Toast.makeText(ProductsListActivity.this, "No Product Found", Toast.LENGTH_LONG).show();
                    }
                    else {
                        showProducts(recyclerview_Products, dataList);
                    }
                }
                else if (status == 400){
                    stopAnim();
                    text_NoProductAvailable.setVisibility(View.VISIBLE);
//                    Toast.makeText(ProductsListActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ProductsResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
                stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(ProductsListActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showProducts(RecyclerView recyclerView, List<ProductDataModel> products) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductsListActivity.this));
        productAdapter = new ProductAdapter("vertical", products, ProductsListActivity.this);
        recyclerView.setAdapter(productAdapter);
        stopAnim();
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
//                    Toast.makeText(ProductsListActivity.this, "Cart Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<CartProductsCountResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                stopAnim();
                Toast.makeText(ProductsListActivity.this, t.getMessage() + "Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }
}