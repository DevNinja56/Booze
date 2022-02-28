package com.my.booze.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my.booze.R;
import com.my.booze.activities.CategoryProductsActivity;
import com.my.booze.activities.OrderDetailsActivity;
import com.my.booze.adapters.OrdersAdapter;
import com.my.booze.adapters.ProductAdapter;
import com.my.booze.models.OrderDataModel;
import com.my.booze.models.OrderListResponseModel;
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

public class CurrentOrdersFragment extends Fragment {

    RecyclerView recyclerview_CurrentOrders;
    OrdersAdapter ordersAdapter;
    ArrayList<OrderDataModel> orders;
    public AVLoadingIndicatorView avi;
    GetDataService service;
    String user_Id;
    TextView no_OrderText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_current_orders, container, false);
        user_Id = Utilities.getString(getActivity(), "user_Id");
        no_OrderText = view.findViewById(R.id.no_OrderText);
        avi = view.findViewById(R.id.avi);
        startAnim();
        recyclerview_CurrentOrders = view.findViewById(R.id.recyclerview_CurrentOrders);
//        Toast.makeText(getActivity(), ""+user_Id, Toast.LENGTH_SHORT).show();
        loadOrders();
        return view;
    }

    public void loadOrders() {
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        RequestBody parameter = RequestBody.create(MediaType.parse("text/plain"),product_Category);
        Call<OrderListResponseModel> call = service.getCurrentOrders(user_Id);

        call.enqueue(new Callback<OrderListResponseModel>() {
            @Override
            public void onResponse(Call<OrderListResponseModel> call, Response<OrderListResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                if (status == 200){
                    List dataList = response.body().getData();
                    if (dataList == null){
//                        Toast.makeText(getActivity(), "No Order Found", Toast.LENGTH_LONG).show();
                        no_OrderText.setVisibility(View.VISIBLE);
                    }
                    else {
                        showOrders(recyclerview_CurrentOrders, dataList);
                    }
                }
                else if (status == 400){
                    stopAnim();
                    no_OrderText.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<OrderListResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
                stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(getActivity(), t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showOrders(RecyclerView recyclerView, List<OrderDataModel> orders) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ordersAdapter = new OrdersAdapter(orders, getActivity());
        recyclerView.setAdapter(ordersAdapter);
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
}