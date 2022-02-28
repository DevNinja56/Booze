package com.my.booze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.my.booze.R;
import com.my.booze.adapters.ProductAdapter;
import com.my.booze.models.CartProductsCountResponseModel;
import com.my.booze.models.CheckProductInCartModel;
import com.my.booze.models.OrderResponseModel;
import com.my.booze.models.ProductDataModel;
import com.my.booze.models.ProductsResponseModel;
import com.my.booze.network.GetDataService;
import com.my.booze.network.RetrofitClientInstance;
import com.my.booze.utilities.Utilities;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;
import com.xgc1986.ripplebutton.widget.RippleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerview_Products;
    RelativeLayout btn_AddToCart;
    TextView text_ProductName, text_CompanyName, min_OrderAmount, product_Description, product_Quantity, text_CategoryName,
            product_Category, product_SubCategory, no_ProductAdded, text_ProductName2, text_ProductPrice, product_AlreadyAdded;
    ArrayList<ProductDataModel> products;
    ProductAdapter productAdapter;
    ImageView backsign, product_Image, btn_Plus, btn_Minus;
//    RecyclerView recyclerview_CartItems;
    RippleButton btn_ViewCart;
    int product_QuantityNumber = 1;
    String product_Id;
    GetDataService service;
    LinearLayout add_toCartLayout, product_Details;
    String userId;
    ImageView btn_Cart;
    TextView user_CartItemsCount;
    RelativeLayout cart_bgRound;
    ScrollView main_Layout;
    public AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        userId = Utilities.getString(ProductDetailsActivity.this, "user_Id");
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        product_Id = getIntent().getStringExtra("PRODUCT_ID");
        backsign = findViewById(R.id.backsign);
//        recyclerview_CartItems = findViewById(R.id.recyclerview_CartItems);
        avi = findViewById(R.id.avi);
        main_Layout = findViewById(R.id.main_Layout);
        product_Image = findViewById(R.id.product_Image);
        btn_Cart = findViewById(R.id.btn_Cart);
        cart_bgRound = findViewById(R.id.cart_bgRound);
        user_CartItemsCount = findViewById(R.id.user_CartItemsCount);
        btn_Plus = findViewById(R.id.btn_Plus);
        btn_Minus = findViewById(R.id.btn_Minus);
        text_ProductName = findViewById(R.id.text_ProductName);
        text_CompanyName = findViewById(R.id.text_CompanyName);
        text_CategoryName = findViewById(R.id.text_CategoryName);
        min_OrderAmount = findViewById(R.id.min_OrderAmount);
        product_Quantity = findViewById(R.id.product_Quantity);
        product_Description = findViewById(R.id.product_Description);
        product_Category = findViewById(R.id.product_Category);
        product_SubCategory = findViewById(R.id.product_SubCategory);
        product_AlreadyAdded = findViewById(R.id.product_AlreadyAdded);
        text_ProductName2 = findViewById(R.id.text_ProductName2);
        text_ProductPrice = findViewById(R.id.text_ProductPrice);
        add_toCartLayout = findViewById(R.id.add_toCartLayout);
        product_Details = findViewById(R.id.product_Details);
        backsign = findViewById(R.id.backsign);
        btn_ViewCart = findViewById(R.id.btn_ViewCart);
        btn_AddToCart = findViewById(R.id.btn_AddToCart);
        recyclerview_Products = findViewById(R.id.recyclerview_Products);
        main_Layout.setVisibility(View.GONE);
        add_toCartLayout.setVisibility(View.GONE);
        startAnim();
        products = new ArrayList<ProductDataModel>();
        recyclerview_Products.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerview_Products.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter("horizontal", products, ProductDetailsActivity.this);
        recyclerview_Products.setAdapter(productAdapter);
        backsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart( v, Integer.parseInt(product_Id), product_QuantityNumber);
            }
        });
        btn_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        checkCart();
        productQuantityManage();
//        Log.i("checking_Data", ""+product_Id);
        loadProductDetails(product_Id);
        checkProductInCart(product_Id);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkCart();
    }

    public void productQuantityManage() {
        product_Quantity.setText(""+ product_QuantityNumber);
        btn_Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product_QuantityNumber == 10) {
                    Snackbar snackbar = Snackbar.make(v, "You can order max 10 quantity against a product", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(ContextCompat.getColor(ProductDetailsActivity.this, R.color.red));
                    snackbar.show();
                } else {
                    product_QuantityNumber++;
                    product_Quantity.setText(""+ product_QuantityNumber);
                }
            }
        });
        btn_Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product_QuantityNumber == 1) {
                    Snackbar snackbar = Snackbar.make(v, "You can't order less than 1 quantity against a product", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(ContextCompat.getColor(ProductDetailsActivity.this, R.color.red));
                    snackbar.show();
                } else {
                    product_QuantityNumber--;
                    product_Quantity.setText(""+ product_QuantityNumber);
                }
            }
        });
    }

    public void loadProductDetails(String product_Id) {

        Call<ProductsResponseModel> call = service.getProductDetails(product_Id);

        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call, Response<ProductsResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                if (status == 200){
                    List dataList = response.body().getData();
                    Log.i("checking_Data", ""+product_Id);
                    Log.i("checking_Data", ""+response.body().getStatus());
                    Log.i("checking_Data", ""+response.body().getMsg());
                    if (dataList.isEmpty()){
//                        Toast.makeText(getActivity(), "No Product Found", Toast.LENGTH_LONG).show();
                    }
                    else {
                        List<ProductDataModel> product = dataList;
                        Log.i("checkingData", ""+product);
                        Picasso.get().load(product.get(0).getProduct_Picture()).into(product_Image);
                        text_ProductName.setText(product.get(0).getProduct_Name());
                        product_Description.setText(product.get(0).getProduct_Description());
                        text_CategoryName.setText(product.get(0).getProduct_Category());
                        product_Category.setText(product.get(0).getProduct_Category());
                        product_SubCategory.setText(product.get(0).getProduct_SubCategory());
                        text_ProductName2.setText(product.get(0).getProduct_Name());
                        text_ProductPrice.setText("$"+product.get(0).getProduct_Price());
                        main_Layout.setVisibility(View.VISIBLE);
                        add_toCartLayout.setVisibility(View.VISIBLE);
                        stopAnim();
                        loadRelatedProducts(recyclerview_Products, productAdapter, product.get(0).getProduct_Category());
//                        Utilities.stopAnim();
//                        showProducts(recyclerview_Products, dataList);
                    }
                }

                else if (status == 400){
                    stopAnim();
//                    Toast.makeText(ProductDetailsActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<ProductsResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Utilities.stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(ProductDetailsActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadRelatedProducts(RecyclerView recyclerView, ProductAdapter adapter, String product_Category) {
        Call<ProductsResponseModel> call = service.getCategoryProducts(product_Category);

        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call, Response<ProductsResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                if (!String.valueOf(status).isEmpty() && status == 200){
                    List dataList = response.body().getData();
                    Log.i("checking_Data", ""+dataList);
                    if (dataList == null){
//                        Toast.makeText(getActivity(), "No Product Found", Toast.LENGTH_LONG).show();
                    }
                    else {
                        showProducts(recyclerView, dataList, adapter);
//                        Utilities.stopAnim();
//                        showProducts(recyclerview_Products, dataList);
                    }
                }
                else if (!String.valueOf(status).isEmpty() && status == 400){
                    stopAnim();
//                    Toast.makeText(ProductDetailsActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<ProductsResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Utilities.stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(ProductDetailsActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showProducts(RecyclerView recyclerView, List<ProductDataModel> products, ProductAdapter adapter) {
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Collections.shuffle(products);
        adapter = new ProductAdapter("horizontal", products, ProductDetailsActivity.this);
        recyclerView.setAdapter(adapter);
//        stopAnim();
    }

    public void checkProductInCart(String product_Id) {
        Call<CheckProductInCartModel> call = service.checkProductInCartModel( userId, product_Id);

        call.enqueue(new Callback<CheckProductInCartModel>() {
            @Override
            public void onResponse(Call<CheckProductInCartModel> call, Response<CheckProductInCartModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                Log.i("checking_Data11", ""+product_Id);
                Log.i("checking_Data11", ""+response.body().getMsg());
                Log.i("checking_Data11", ""+response.body().getStatus());
//                Log.i("checking_Data11", ""+status);
                if (status == 200){
                    add_toCartLayout.setVisibility(View.GONE);
                    btn_ViewCart.setVisibility(View.VISIBLE);
                    product_AlreadyAdded.setVisibility(View.VISIBLE);
                    product_Details.setVisibility(View.GONE);
                    int order_RemainingMinimum = response.body().getData();
                    String msg = response.body().getMsg();
                    Log.i("checking_Data11", ""+order_RemainingMinimum);
//                    if (dataList == null){
////                        Toast.makeText(getActivity(), "No Product Found", Toast.LENGTH_LONG).show();
//                    }
//                    else {
//                        Log.i("checkingData", ""+product.get(0).getProduct_Name());
//                        Picasso.get().load(product.get(0).getProduct_Picture()).into(product_Image);
                        min_OrderAmount.setText(""+order_RemainingMinimum);
                        btn_ViewCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                                intent.putExtra("USER_ID", 5);
                                startActivity(intent);
                            }
                        });
//                    }
                }
                else if (status == 201) {
                    int order_RemainingMinimum = response.body().getData();
                    min_OrderAmount.setText(""+order_RemainingMinimum);
                }
                else if (status == 400){
//                    Utilities.stopAnim();
                    add_toCartLayout.setVisibility(View.VISIBLE);
                    btn_ViewCart.setVisibility(View.GONE);
                    product_AlreadyAdded.setVisibility(View.GONE);
                    product_Details.setVisibility(View.VISIBLE);
//                    Toast.makeText(ProductDetailsActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<CheckProductInCartModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Utilities.stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(ProductDetailsActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addToCart(View v, int product_Id, int product_QuantityNumber) {
        Call<OrderResponseModel> call = service.addToCart(Integer.parseInt(userId), product_Id, product_QuantityNumber);

        call.enqueue(new Callback<OrderResponseModel>() {
            @Override
            public void onResponse(Call<OrderResponseModel> call, Response<OrderResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                Log.i("checking_Data11", ""+product_Id);
                Log.i("checking_Data11", ""+response.body().getMsg());
                Log.i("checking_Data11", ""+response.body().getStatus());
//                Log.i("checking_Data11", ""+status);
                if (status == 200){
                    add_toCartLayout.setVisibility(View.GONE);
                    btn_ViewCart.setVisibility(View.VISIBLE);
                    product_AlreadyAdded.setText("This Product is Added in Cart!");
                    product_AlreadyAdded.setVisibility(View.VISIBLE);
                    product_Details.setVisibility(View.GONE);
                    String order_RemainingMinimum = response.body().getData().getOrder_RemainingMinimum();
                    Log.i("checking_Data11", ""+order_RemainingMinimum);
//                    if (dataList == null){
////                        Toast.makeText(getActivity(), "No Product Found", Toast.LENGTH_LONG).show();
//                    }
//                    else {
//                        Log.i("checkingData", ""+product.get(0).getProduct_Name());
//                        Picasso.get().load(product.get(0).getProduct_Picture()).into(product_Image);
                    int cart_Items = response.body().getData().getOrder_TotalProducts();
                    Utilities.saveString(ProductDetailsActivity.this, "cart_Items", ""+cart_Items);
                    min_OrderAmount.setText(""+order_RemainingMinimum);
                    Utilities.saveString(ProductDetailsActivity.this, "user_CartItemsCount", ""+cart_Items);
                    checkCart();
                    Snackbar snackbar = Snackbar.make(v, "Product Successfully Added in Cart!", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(ContextCompat.getColor(ProductDetailsActivity.this, R.color.colorGreen));
                    snackbar.show();
                    btn_ViewCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
                            intent.putExtra("USER_ID", 5);
                            startActivity(intent);
                        }
                    });
                }
                else if (status == 400){
//                    Utilities.stopAnim();
                    add_toCartLayout.setVisibility(View.VISIBLE);
                    btn_ViewCart.setVisibility(View.GONE);
                    product_AlreadyAdded.setVisibility(View.GONE);
                    product_Details.setVisibility(View.VISIBLE);
//                    Toast.makeText(ProductDetailsActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<OrderResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Utilities.stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(ProductDetailsActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }



    public void checkCart() {
        Call<CartProductsCountResponseModel> call = service.getCartProductsCount(userId);
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
//                    Toast.makeText(ProductDetailsActivity.this, "Cart Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<CartProductsCountResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                stopAnim();
                Toast.makeText(ProductDetailsActivity.this, t.getMessage() + "Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }
    void startAnim(){
        avi.show();
        // or avi.smoothToShow();
    }

    void stopAnim(){
        avi.hide();
        // or avi.smoothToHide();
    }


}