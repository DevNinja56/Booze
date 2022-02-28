package com.my.booze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.booze.R;
import com.my.booze.adapters.OrderProductsAdapter;
import com.my.booze.models.CartProductModel;
import com.my.booze.models.orderDetailsResponseModel;
import com.my.booze.network.GetDataService;
import com.my.booze.network.RetrofitClientInstance;
import com.my.booze.utilities.Utilities;
import com.wang.avi.AVLoadingIndicatorView;
import com.xgc1986.ripplebutton.widget.RippleButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity2 extends AppCompatActivity {
    
    LinearLayout order_Delivered, order_Status;
    String orderStatus, user_Id, order_Id;
    TextView order_StatusText, order_EstimateDeliveredTime, order_SubTotal, order_DeliveryCost, order_TotalCost;
    GetDataService service;
    public AVLoadingIndicatorView avi;
    RecyclerView recyclerview_OrderProducts;
    OrderProductsAdapter cartAdapter;
    ImageView backsign;
    ScrollView main_Layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details2);

        orderStatus = getIntent().getStringExtra("ORDER_STATUS");
        order_Id = getIntent().getStringExtra("ORDER_ID");
        recyclerview_OrderProducts = findViewById(R.id.recyclerview_OrderProducts);
        main_Layout = findViewById(R.id.main_Layout);
         main_Layout.setVisibility(View.GONE);
        backsign = findViewById(R.id.backsign);
        avi = findViewById(R.id.avi);
        user_Id = Utilities.getString(OrderDetailsActivity2.this, "user_Id");
        orderStatus = getIntent().getStringExtra("ORDER_STATUS");
        order_SubTotal = findViewById(R.id.order_SubTotal);
        order_DeliveryCost = findViewById(R.id.order_DeliveryCost);
        order_TotalCost = findViewById(R.id.order_TotalCost);
        order_EstimateDeliveredTime = findViewById(R.id.order_EstimateDeliveredTime);
        order_StatusText = findViewById(R.id.order_StatusText);
        order_Delivered = findViewById(R.id.order_Delivered);
        order_Status = findViewById(R.id.order_Status);
        order_Status.setVisibility(View.VISIBLE);
        Log.i("checkOrderId", ""+order_Id);
        backsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(orderStatus.equals("Placed")) {
            order_Status.setVisibility(View.GONE);
            order_Delivered.setVisibility(View.VISIBLE);
            order_StatusText.setText("Order is Placed");
            loadOrder();
        } else if (orderStatus.equals("Delivered")) {
            order_Status.setVisibility(View.GONE);
            order_Delivered.setVisibility(View.VISIBLE);
            loadOrder();
        } else {
            order_Status.setVisibility(View.VISIBLE);
            order_Delivered.setVisibility(View.GONE);
            loadOrder();
        }
    }

    public void loadOrder() {
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<orderDetailsResponseModel> call = service.getOrderById(order_Id);

        call.enqueue(new Callback<orderDetailsResponseModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<orderDetailsResponseModel> call, Response<orderDetailsResponseModel> response) {
                assert response.body() != null;
                int status = response.body().getStatus();
                Log.i("fakharchecking", "" + status);
                if (status == 200) {
                    if (orderStatus.equals("OnWay")) {
                        order_EstimateDeliveredTime.setText(response.body().getOrder_EstimateDeliveryTime()+" MINS");
                    }
                    main_Layout.setVisibility(View.VISIBLE);
                    stopAnim();
                    List dataList = response.body().getOrder_CartItems();
                    showProducts(recyclerview_OrderProducts, dataList);
                    order_SubTotal.setText("$" + response.body().getOrder_SubTotal());
                    order_DeliveryCost.setText("$" + response.body().getOrder_DeliveryCost());
                    order_TotalCost.setText("$" + response.body().getOrder_TotalCost());
                } else if (status == 400) {
                    stopAnim();
//                    Toast.makeText(CategoryProductsActiv  ity.this, "Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<orderDetailsResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
                stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(OrderDetailsActivity2.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }
        public void showProducts(RecyclerView recyclerview_CartProducts, List< CartProductModel > products) {
            recyclerview_CartProducts.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity2.this));
            recyclerview_CartProducts.setItemAnimator(new DefaultItemAnimator());
            cartAdapter = new OrderProductsAdapter(products, OrderDetailsActivity2.this);
            recyclerview_CartProducts.setAdapter(cartAdapter);
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