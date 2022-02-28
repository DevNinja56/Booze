package com.my.booze.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my.booze.R;
import com.my.booze.activities.CartActivity;
import com.my.booze.activities.CategoryProductsActivity;
import com.my.booze.activities.MapsActivity;
import com.my.booze.activities.ProductDetailsActivity;
import com.my.booze.activities.SearchProductsActivity;
import com.my.booze.adapters.BrandAdapter;
import com.my.booze.adapters.CategoryAdapter;
import com.my.booze.adapters.ProductAdapter;
import com.my.booze.models.BrandModel;
import com.my.booze.models.CartProductsCountResponseModel;
import com.my.booze.models.CategoryModel;
import com.my.booze.models.ProductDataModel;
import com.my.booze.models.ProductsResponseModel;
import com.my.booze.network.GetDataService;
import com.my.booze.network.RetrofitClientInstance;
import com.my.booze.utilities.Utilities;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopFragment extends Fragment {

    GetDataService service;
//    RecyclerView recyclerview_Category, recyclerview_FeaturedProducts,
//            recyclerview_TopProducts, recyclerview_TopWine, recyclerview_Brands;
    RecyclerView recyclerview_Category, recyclerview_TopBeerProducts, recyclerview_TopMixerProducts,
            recyclerview_TopSmokeProducts, recyclerview_TopWine, recyclerview_LiquorProducts, recyclerview_Brands;
    ArrayList<CategoryModel> categories;
    ArrayList<ProductDataModel> wine_Products, liquor_Products, beer_Products, mixer_Products, smoke_Products;
    ArrayList<BrandModel> brands;
    CategoryAdapter categoryAdapter;
    ProductAdapter wine_Adapter, liquor_Adapter, beer_Adapter, mixer_Adapter, smoke_Adapter;
    BrandAdapter brandAdapter;
    TextView random_ProductName1, random_ProductName2, random_ProductDescription1, random_ProductDescription2,
            random_ProductPrice1, random_ProductPrice2, user_CartItemsCount;
    ImageView random_ProductImage1, random_ProductImage2, btn_Cart;
    LinearLayout random_Product1, random_Product2;
    int cartItemsCount;
    RelativeLayout cart_bgRound;
    EditText search_EditText;
    ImageView btn_Search;
    TextView txt_Address;
    String user_Id;
    TextView wine_SeeAll, liquor_SeeAll, beer_SeeAll, mixer_SeeAll, smokes_SeeAll;
    public AVLoadingIndicatorView avi;
    LinearLayout main_Layout2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        user_Id = Utilities.getString(getActivity(), "user_Id");
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        main_Layout2 = view.findViewById(R.id.main_Layout2);
        avi = view.findViewById(R.id.avi);
        wine_SeeAll = view.findViewById(R.id.wine_SeeAll);
        liquor_SeeAll = view.findViewById(R.id.liquor_SeeAll);
        beer_SeeAll = view.findViewById(R.id.beer_SeeAll);
        mixer_SeeAll = view.findViewById(R.id.mixer_SeeAll);
        smokes_SeeAll = view.findViewById(R.id.smokes_SeeAll);
        txt_Address = view.findViewById(R.id.txt_Address);
        btn_Search = view.findViewById(R.id.btn_Search);
        search_EditText = view.findViewById(R.id.search_EditText);
        btn_Cart = view.findViewById(R.id.btn_Cart);
        user_CartItemsCount = view.findViewById(R.id.user_CartItemsCount);
        cart_bgRound = view.findViewById(R.id.cart_bgRound);
        random_Product1 = view.findViewById(R.id.random_Product1);
        random_Product2 = view.findViewById(R.id.random_Product2);
        random_ProductName1 = view.findViewById(R.id.random_ProductName1);
        random_ProductName2 = view.findViewById(R.id.random_ProductName2);
        random_ProductDescription1 = view.findViewById(R.id.random_ProductDescription1);
        random_ProductDescription2 = view.findViewById(R.id.random_ProductDescription2);
        random_ProductPrice1 = view.findViewById(R.id.random_ProductPrice1);
        random_ProductPrice2 = view.findViewById(R.id.random_ProductPrice2);
        random_ProductImage1 = view.findViewById(R.id.random_ProductImage1);
        random_ProductImage2 = view.findViewById(R.id.random_ProductImage2);
        recyclerview_Category = view.findViewById(R.id.recyclerview_Category);
        startAnim();

        checkCart();
        txt_Address.setText(Utilities.getString(getActivity(), "User_RecentAddress") + "  >");
        btn_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });
        cart_bgRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });
        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchProductsActivity.class);
                intent.putExtra("search_Word", ""+search_EditText.getText().toString());
                startActivity(intent);
            }
        });

        seeAllListeners();

        categories = new ArrayList<CategoryModel>();
        categories.add(new CategoryModel(1, R.drawable.ic_small_wine_glass, "Wine"));
        categories.add(new CategoryModel(2, R.drawable.ic_small_liquor, "Liquor"));
        categories.add(new CategoryModel(3, R.drawable.ic_small_beer, "Beer"));
        categories.add(new CategoryModel(4, R.drawable.ic_small_mixers, "Mixers"));
        categories.add(new CategoryModel(5, R.drawable.ic_small_smoking, "Smokes"));
        recyclerview_Category.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_Category.setItemAnimator(new DefaultItemAnimator());
        categoryAdapter = new CategoryAdapter(categories, getActivity());
        recyclerview_Category.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String categoryName = categories.get(position).getCategoryName();
                Intent intent = new Intent(getActivity(), CategoryProductsActivity.class);
                intent.putExtra("product_Category", categoryName);
                startActivity(intent);
            }
        });

        recyclerview_TopBeerProducts = view.findViewById(R.id.recyclerview_TopBeer);
        beer_Products = new ArrayList<ProductDataModel>();
        wine_Products = new ArrayList<ProductDataModel>();
        liquor_Products = new ArrayList<ProductDataModel>();
        smoke_Products = new ArrayList<ProductDataModel>();
        mixer_Products = new ArrayList<ProductDataModel>();
//        products.add(new ProductDataModel(1, R.drawable.ic_botel, "Campo Viejo", "Tempranilo", "$ 12.99"));
//        products.add(new ProductDataModel(1, R.drawable.ic_botel, "Campo Viejo", "Tempranilo", "$ 12.99"));
//        products.add(new ProductDataModel(1, R.drawable.ic_botel, "Campo Viejo", "Tempranilo", "$ 12.99"));
//        products.add(new ProductDataModel(1, R.drawable.ic_botel, "Campo Viejo", "Tempranilo", "$ 12.99"));
//        products.add(new ProductDataModel(1, R.drawable.ic_botel, "Campo Viejo", "Tempranilo", "$ 12.99"));
        recyclerview_TopBeerProducts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_TopBeerProducts.setItemAnimator(new DefaultItemAnimator());
        beer_Adapter = new ProductAdapter("horizontal", beer_Products, getActivity());
        recyclerview_TopBeerProducts.setAdapter(beer_Adapter);
        loadCategoryProducts(recyclerview_TopBeerProducts,  beer_Adapter, getResources().getString(R.string.Beer));
//        beer_Adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
//                int productId = beer_Products.get(position).getProduct_Id();
//                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
//                intent.putExtra("PRODUCT_ID", ""+productId);
//                startActivity(intent);
//            }
//        });

        recyclerview_TopMixerProducts = view.findViewById(R.id.recyclerview_TopMixer);
        recyclerview_TopMixerProducts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_TopMixerProducts.setItemAnimator(new DefaultItemAnimator());
        mixer_Adapter = new ProductAdapter("horizontal",mixer_Products, getActivity());
        recyclerview_TopMixerProducts.setAdapter(mixer_Adapter);
        loadCategoryProducts(recyclerview_TopMixerProducts,  mixer_Adapter, getResources().getString(R.string.Mixers));
        mixer_Adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int productId = mixer_Products.get(position).getProduct_Id();
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra("PRODUCT_ID", ""+productId);
                startActivity(intent);
            }
        });

        recyclerview_TopWine = view.findViewById(R.id.recyclerview_TopWine);
        recyclerview_TopWine.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_TopWine.setItemAnimator(new DefaultItemAnimator());
        wine_Adapter = new ProductAdapter("horizontal",wine_Products, getActivity());
        recyclerview_TopWine.setAdapter(wine_Adapter);
        loadCategoryProducts(recyclerview_TopWine,  wine_Adapter, getResources().getString(R.string.Wine));
        wine_Adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int productId = wine_Products.get(position).getProduct_Id();
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra("PRODUCT_ID", ""+productId);
                startActivity(intent);
            }
        });

        recyclerview_LiquorProducts = view.findViewById(R.id.recyclerview_TopLiquor);
        recyclerview_LiquorProducts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_LiquorProducts.setItemAnimator(new DefaultItemAnimator());
        liquor_Adapter = new ProductAdapter("horizontal",liquor_Products, getActivity());
        recyclerview_LiquorProducts.setAdapter(liquor_Adapter);
        loadCategoryProducts(recyclerview_LiquorProducts,  liquor_Adapter, getResources().getString(R.string.Liquor));
        liquor_Adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int productId = liquor_Products.get(position).getProduct_Id();
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra("PRODUCT_ID", ""+productId);
                startActivity(intent);
            }
        });

        recyclerview_TopSmokeProducts = view.findViewById(R.id.recyclerview_TopSmoke);
        recyclerview_TopSmokeProducts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_TopSmokeProducts.setItemAnimator(new DefaultItemAnimator());
        smoke_Adapter = new ProductAdapter("horizontal",smoke_Products, getActivity());
        recyclerview_TopSmokeProducts.setAdapter(smoke_Adapter);
        loadCategoryProducts(recyclerview_TopSmokeProducts, smoke_Adapter, getResources().getString(R.string.Smokes));
        smoke_Adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int productId = smoke_Products.get(position).getProduct_Id();
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra("PRODUCT_ID", ""+productId);
                startActivity(intent);
            }
        });

        loadRandomProduct1();

//        recyclerview_Brands = view.findViewById(R.id.recyclerview_Brands);
//        brands = new ArrayList<BrandModel>();
//        brands.add(new BrandModel(1, R.drawable.ic_brand, "Tempranilo"));
//        brands.add(new BrandModel(1, R.drawable.ic_brand, "Tempranilo"));
//        brands.add(new BrandModel(1, R.drawable.ic_brand, "Tempranilo"));
//        brands.add(new BrandModel(1, R.drawable.ic_brand, "Tempranilo"));
//        brands.add(new BrandModel(1, R.drawable.ic_brand, "Tempranilo"));
//        recyclerview_Brands.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        recyclerview_Brands.setItemAnimator(new DefaultItemAnimator());
//        brandAdapter = new BrandAdapter(brands, getActivity());
//        recyclerview_Brands.setAdapter(brandAdapter);


        return view;
    }

    public void loadCategoryProducts(RecyclerView recyclerView, ProductAdapter adapter, String product_Category) {
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
//                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<ProductsResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Utilities.stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(getActivity(), t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showProducts(RecyclerView recyclerView, List<ProductDataModel> products, ProductAdapter adapter) {
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Collections.shuffle(products);
        adapter = new ProductAdapter("horizontal", products, getActivity());
        recyclerView.setAdapter(adapter);
//        stopAnim();
    }

    private void loadRandomProduct1() {

        Call<ProductsResponseModel> call = service.getRandomProduct();
        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call, Response<ProductsResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                if (!String.valueOf(status).isEmpty() && status == 200){
                    List dataList = response.body().getData();
                    if (dataList == null){
                        Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_LONG).show();
                    }
                    else {
                        List<ProductDataModel> product = dataList;
                        Log.i("checkingData", ""+product.get(0).getProduct_Name());
                        random_ProductName1.setText(product.get(0).getProduct_Name());
                        random_ProductDescription1.setText(product.get(0).getProduct_Description());
                        random_ProductPrice1.setText("$"+product.get(0).getProduct_Price());
                        Picasso.get().load(product.get(0).getProduct_Picture()).into(random_ProductImage1);
                        stopAnim();
                        main_Layout2.setVisibility(View.VISIBLE);
                        random_Product1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                                intent.putExtra("PRODUCT_ID", ""+product.get(0).getProduct_Id());
                                startActivity(intent);
                            }
                        });
                        random_ProductName2.setText(product.get(1).getProduct_Name());
                        random_ProductDescription2.setText(product.get(1).getProduct_Description());
                        random_ProductPrice2.setText("$"+product.get(1).getProduct_Price());
                        Picasso.get().load(product.get(1).getProduct_Picture()).into(random_ProductImage2);
                        random_Product2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity(), ""+product.get(1).getProduct_Id(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                                intent.putExtra("PRODUCT_ID", ""+product.get(1).getProduct_Id());
                                startActivity(intent);
                            }
                        });

                    }
                }
                else if (!String.valueOf(status).isEmpty() && status == 400){
                    stopAnim();
//                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ProductsResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                stopAnim();
                Toast.makeText(getActivity(), t.getMessage() + "Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void checkCart() {
        Call<CartProductsCountResponseModel> call = service.getCartProductsCount(user_Id);
        call.enqueue(new Callback<CartProductsCountResponseModel>() {
            @Override
            public void onResponse(Call<CartProductsCountResponseModel> call, Response<CartProductsCountResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                if (status == 200){
                    cartItemsCount = response.body().getCart_ProductsCount();
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
//                    Toast.makeText(getActivity(), "Cart Failed", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<CartProductsCountResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                stopAnim();
                Toast.makeText(getActivity(), t.getMessage() + "Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        checkCart();
    }

    public void seeAllListeners() {
        wine_SeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryProductsActivity.class);
                intent.putExtra("product_Category", ""+getResources().getString(R.string.Wine));
                startActivity(intent);
            }
        });
        liquor_SeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryProductsActivity.class);
                intent.putExtra("product_Category", ""+getResources().getString(R.string.Liquor));
                startActivity(intent);
            }
        });
        beer_SeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryProductsActivity.class);
                intent.putExtra("product_Category", ""+getResources().getString(R.string.Beer));
                startActivity(intent);
            }
        });
        mixer_SeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryProductsActivity.class);
                intent.putExtra("product_Category", ""+getResources().getString(R.string.Mixers));
                startActivity(intent);
            }
        });
        smokes_SeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryProductsActivity.class);
                intent.putExtra("product_Category", ""+getResources().getString(R.string.Smokes));
                startActivity(intent);
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