package com.my.booze.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.internal.$Gson$Preconditions;
import com.my.booze.R;
import com.my.booze.adapters.ProductAdapter;
import com.my.booze.models.CartDataModel;
import com.my.booze.models.CartProductModel;
import com.my.booze.models.OrderResponseModel;
import com.my.booze.models.PlaceOrderResponseModel;
import com.my.booze.models.ProductDataModel;
import com.my.booze.models.ProductsResponseModel;
import com.my.booze.network.GetDataService;
import com.my.booze.network.RetrofitClientInstance;
import com.my.booze.utilities.Utilities;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;
import com.xgc1986.ripplebutton.widget.RippleButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements OnMapReadyCallback {

    ImageView backsign;
    static RippleButton btn_PlaceOrder;
    static RecyclerView recyclerview_CartProducts;
    static TextView order_SubTotal, order_DeliveryCost, order_TotalCost, order_RemainingAmount;
    GetDataService service;
    String user_Id;
    static ArrayList<CartProductModel> products;
    static CartAdapter cartAdapter;
    public AVLoadingIndicatorView avi;
    static ScrollView root_View;
    static LinearLayout add_MoreItems;
    Double lat, lng;
    LatLng latLng;
    private GoogleMap mMap;
    TextView btn_EditAddress, user_Address;
    String order_Id;
    public static TextView cart_EmptyText;
    String orderRemainingAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        products = new ArrayList<CartProductModel>();
        user_Id = Utilities.getString(CartActivity.this, "user_Id");
        cart_EmptyText = findViewById(R.id.cart_EmptyText);
        root_View = findViewById(R.id.root_View);
        avi = findViewById(R.id.avi);
        user_Address = findViewById(R.id.user_Address);
        btn_EditAddress = findViewById(R.id.btn_EditAddress);
        recyclerview_CartProducts = findViewById(R.id.recyclerview_CartProducts);
        order_SubTotal = findViewById(R.id.order_SubTotal);
        order_DeliveryCost = findViewById(R.id.order_DeliveryCost);
        order_TotalCost = findViewById(R.id.order_TotalCost);
        order_RemainingAmount = findViewById(R.id.order_RemainingAmount);
        backsign = findViewById(R.id.backsign);
        add_MoreItems = findViewById(R.id.add_MoreItems);
        btn_PlaceOrder = findViewById(R.id.btn_PlaceOrder);
        root_View.setVisibility(View.GONE);
        btn_PlaceOrder.setVisibility(View.GONE);
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_maps);
        supportMapFragment.getMapAsync(CartActivity.this);
        startAnim();
        backsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder(v);
            }
        });
        btn_EditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        cart_EmptyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));
            }
        });
        loadCart();
    }

    public void loadCart() {

        Call<CartDataModel> call = service.getCart(user_Id);
        call.enqueue(new Callback<CartDataModel>() {
            @Override
            public void onResponse(Call<CartDataModel> call, Response<CartDataModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                Log.i("fakharchecking", ""+status);
                if (status == 200){

                    root_View.setVisibility(View.VISIBLE);
                    btn_PlaceOrder.setVisibility(View.VISIBLE);
                    stopAnim();
                    List dataList = response.body().getOrder_CartItems();
                    showProducts(CartActivity.this, recyclerview_CartProducts, dataList);
                    order_Id = ""+response.body().getOrder_Id();
                    order_SubTotal.setText("$"+response.body().getOrder_SubTotal());
                    order_DeliveryCost.setText("$"+response.body().getOrder_DeliveryCost());
                    order_TotalCost.setText("$"+response.body().getOrder_TotalCost());
                    orderRemainingAmount =  response.body().getOrder_RemainingMinimum();
                    order_RemainingAmount.setText("$"+response.body().getOrder_RemainingMinimum());
                    if(response.body().getOrder_RemainingMinimum().equals("0")) {
                        add_MoreItems.setVisibility(View.GONE);
                    }
                    lat = Double.parseDouble(response.body().getOrder_AddressLatitude());
                    lng = Double.parseDouble(response.body().getOrder_AddressLongitude());
                    latLng = new LatLng(lat, lng);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 350));
                    mMap.clear();
                    Utilities.getAddress(CartActivity.this, lat, lng);
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_order_location_pin)));
                }
                else if (status == 400){
//                    Toast.makeText(CartActivity.this, "no cart", Toast.LENGTH_SHORT).show();
                    root_View.setVisibility(View.GONE);
                    cart_EmptyText.setVisibility(View.VISIBLE);
                    stopAnim();
//                    Toast.makeText(CategoryProductsActiv  ity.this, "Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<CartDataModel> call, Throwable t) {
//                progressDialog.dismiss();
                stopAnim();
                Log.i("checkingResponse", t.getMessage());
//                Toast.makeText(CartActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void showProducts(Context context, RecyclerView recyclerview_CartProducts, List<CartProductModel> products) {
        recyclerview_CartProducts.setLayoutManager(new LinearLayoutManager(context));
        recyclerview_CartProducts.setItemAnimator(new DefaultItemAnimator());
        cartAdapter = new CartAdapter(products, context);
        recyclerview_CartProducts.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
    }

    public void placeOrder(View v) {
        if(!orderRemainingAmount.equals("0")) {
            Snackbar snackbar = Snackbar.make(v, "Minimum order amount is $24", Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(ContextCompat.getColor(CartActivity.this, R.color.red));
            snackbar.show();
            return;
        }
        ProgressDialog progressDialog;
        progressDialog  = new ProgressDialog(CartActivity.this);
        progressDialog.setTitle("Your order is placing...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        Call<PlaceOrderResponseModel> call = service.placeOrder(order_Id, user_Id);
        call.enqueue(new Callback<PlaceOrderResponseModel>() {
            @Override
            public void onResponse(Call<PlaceOrderResponseModel> call, Response<PlaceOrderResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                Log.i("fakharchecking", ""+status);
                if (status == 200){
                    Utilities.saveString(CartActivity.this, "user_CartItemsCount", ""+0);
                    progressDialog.dismiss();
                    Intent intent = new Intent(CartActivity.this, OrderDetailsActivity.class);
                    intent.putExtra("ORDER_STATUS", "Placed");
                    intent.putExtra("ORDER_ID", order_Id);
                    startActivity(intent);
                }
                else if (status == 400){
                    progressDialog.dismiss();
//                    Toast.makeText(CategoryProductsActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<PlaceOrderResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
                stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(CartActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public static class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
        List<CartProductModel> list;
        Context context;

        public CartAdapter(List<CartProductModel> list, Context context) {
            this.list = list;
            this.context = context;
        }

        private CartAdapter.OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(CartAdapter.OnItemClickListener listener) {
            mListener = listener;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @NonNull
        @Override
        public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v;
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
            return new CartAdapter.ViewHolder(v);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
            CartProductModel model = list.get(position);
            Picasso.get().load(model.getproduct_Picture()).into(holder.product_Picture);
            holder.product_Name.setText(model.getProduct_Name());
            holder.product_Quantity.setText(model.getProduct_Quantity());
            holder.product_Price.setText("$" + model.getProduct_Price());
            holder.cart_ProductsItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(context, ""+list.get(position).getProduct_Id(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, ProductDetailsActivity.class);
//                intent.putExtra("PRODUCT_ID", ""+list.get(position).getProduct_Id());
//                context.startActivity(intent);
                }
            });
            holder.plus_Sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = Integer.parseInt(holder.product_Quantity.getText().toString()) + 1;
                    holder.product_Quantity.setText(""+quantity);
                    Log.i("checking_Data111", ""+list.get(position).getProduct_Id());
                    Log.i("checking_Data111", ""+list.get(position).getProduct_Price());
                    addToCart1(v, list.get(position).getProduct_Id());
                }
            });
            holder.minus_Sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = Integer.parseInt(holder.product_Quantity.getText().toString()) - 1;
                    holder.product_Quantity.setText(""+quantity);
                    Log.i("checking_Data11", ""+list.get(position).getProduct_Id());
                    removeFromCart(v, list.get(position).getProduct_Id());
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView product_Picture, minus_Sign, plus_Sign;
            public TextView product_Name, product_Price, product_Quantity;
            RelativeLayout cart_ProductsItem;

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                minus_Sign = itemView.findViewById(R.id.minus_Sign);
                plus_Sign = itemView.findViewById(R.id.plus_Sign);
                product_Picture = itemView.findViewById(R.id.product_Picture);
                product_Name = itemView.findViewById(R.id.product_Name);
                product_Price = itemView.findViewById(R.id.product_Price);
                product_Quantity = itemView.findViewById(R.id.product_Quantity);
                cart_ProductsItem = itemView.findViewById(R.id.cart_ProductsItem);
                cart_ProductsItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            Log.v("Enter OnClick", "Enter Click");
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                mListener.onItemClick(position);
                            }
                        }
                    }
                });
            }
        }
        GetDataService service;
        public void addToCart1(View v, int product_Id) {
            service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            String userId = Utilities.getString(context, "user_Id");
            Call<CartDataModel> call = service.addToCart1(Integer.parseInt(userId), product_Id, 1);

            call.enqueue(new Callback<CartDataModel>() {
                @Override
                public void onResponse(Call<CartDataModel> call, Response<CartDataModel> response) {
                    assert response.body() != null;
                    int status  = response.body().getStatus();
                    Log.i("checking_Data11", ""+product_Id);
                    Log.i("checking_Data11", ""+response.body().getMsg());
                    Log.i("checking_Data11", ""+response.body());
//                Log.i("checking_Data11", ""+status);
                    if (status == 200){
                        Snackbar snackbar = Snackbar.make(v, "Product's quantity increased!", Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.colorGreen));
                        snackbar.show();
                        List dataList = response.body().getOrder_CartItems();
//                        cartAdapter.notifyDataSetChanged();
//                        recyclerview_CartProducts.removeAllViews();
                        Toast.makeText(context, ""+dataList.size(), Toast.LENGTH_SHORT).show();
                        showProducts(context, recyclerview_CartProducts, dataList);
                        order_SubTotal.setText("$"+response.body().getOrder_SubTotal());
                        order_DeliveryCost.setText("$"+response.body().getOrder_DeliveryCost());
                        order_TotalCost.setText("$"+response.body().getOrder_TotalCost());
                        order_RemainingAmount.setText("$"+response.body().getOrder_RemainingMinimum());
//                        if(response.body().getOrder_RemainingMinimum().equals("0")) {
//                            add_MoreItems.setVisibility(View.GONE);
//                        }

                    }
                    else if (status == 400){
                    }
                }
                @Override
                public void onFailure(Call<CartDataModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Utilities.stopAnim();
                    Log.i("checkingResponse", t.getMessage());
                    Toast.makeText(context, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
                }
            });
        }

        public void removeFromCart(View v, int product_Id) {
            service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            String userId = Utilities.getString(context, "user_Id");
            Call<CartDataModel> call = service.removeFromCart(Integer.parseInt(userId), product_Id);

            call.enqueue(new Callback<CartDataModel>() {
                @Override
                public void onResponse(Call<CartDataModel> call, Response<CartDataModel> response) {
                    assert response.body() != null;
                    int status  = response.body().getStatus();
                    Log.i("checking_Data11", ""+product_Id);
                    Log.i("checking_Data11", ""+response.body().getMsg());
                    Log.i("checking_Data11", ""+response.body().getStatus());
//                Log.i("checking_Data11", ""+status);
                    if (status == 200){
                        Snackbar snackbar = Snackbar.make(v, "Product's quantity decreased!", Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.red));
                        snackbar.show();
                        List dataList = response.body().getOrder_CartItems();
                        showProducts(context, recyclerview_CartProducts, dataList);
                        order_SubTotal.setText("$"+response.body().getOrder_SubTotal());
                        order_DeliveryCost.setText("$"+response.body().getOrder_DeliveryCost());
                        order_TotalCost.setText("$"+response.body().getOrder_TotalCost());
                        order_RemainingAmount.setText("$"+response.body().getOrder_RemainingMinimum());
                        if(response.body().getOrder_RemainingMinimum().equals("0")) {
                            add_MoreItems.setVisibility(View.GONE);
                        }
                        cartAdapter.notifyDataSetChanged();
                    }
                    else if (status == 400){
                        root_View.setVisibility(View.GONE);
                        cart_EmptyText.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onFailure(Call<CartDataModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Utilities.stopAnim();
                    Log.i("checkingResponse", t.getMessage());
                    Toast.makeText(context, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}