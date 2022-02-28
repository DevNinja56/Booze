package com.my.booze.network;

import com.google.gson.JsonObject;
import com.my.booze.models.CartDataModel;
import com.my.booze.models.CartProductsCountResponseModel;
import com.my.booze.models.CheckProductInCartModel;
import com.my.booze.models.OrderListResponseModel;
import com.my.booze.models.OrderResponseModel;
import com.my.booze.models.PlaceOrderResponseModel;
import com.my.booze.models.ProductsResponseModel;
import com.my.booze.models.SigninResponseModel;
import com.my.booze.models.UserResponseModel;
import com.my.booze.models.orderDetailsResponseModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;

//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import retrofit2.Call;
//import retrofit2.http.Body;
//import retrofit2.http.GET;
//import retrofit2.http.Headers;
//import retrofit2.http.Multipart;
//import retrofit2.http.POST;
//import retrofit2.http.Part;
//
public interface GetDataService {
    @Headers({
            "Accept: application/json",
    })

//    @GET("get-random-product")
//    Call<ProductsResponseModel> getAll();

    @GET("get-random-product")
    Call<ProductsResponseModel> getRandomProduct();

//    @GET("get-wine-products")
//    Call<ProductsResponseModel> getWineProducts();
//
    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("get-specific-category-products")
    Call<ProductsResponseModel> getCategoryProducts(@Field("product_Category") String product_Category);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("get-product-details")
    Call<ProductsResponseModel> getProductDetails(@Field("product_Id") String product_Id);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("update-cart")
    Call<OrderResponseModel> updateCart(@Field("order_UserId") String order_UserId,
                                        @Field("order_Cart") String order_Cart,
                                        @Field("order_PaymentMethod") String order_PaymentMethod,
                                        @Field("order_AddressLatLng") String order_AddressLatLng);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("check-product-in-cart")
    Call<CheckProductInCartModel> checkProductInCartModel(@Field("order_UserId") String order_UserId,
                                                          @Field("product_Id") String product_Id);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("add-to-cart")
    Call<OrderResponseModel> addToCart(@Field("order_UserId") int order_UserId,
                                       @Field("product_Id") int product_Id,
                                       @Field("product_Quantity") int product_Quantity);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("add-to-cart-1")
    Call<CartDataModel> addToCart1(@Field("order_UserId") int order_UserId,
                                       @Field("product_Id") int product_Id,
                                       @Field("product_Quantity") int product_Quantity
                                       );

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("remove-from-cart")
    Call<CartDataModel> removeFromCart(@Field("order_UserId") int order_UserId,
                                       @Field("product_Id") int product_Id);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("sign-up")
    Call<UserResponseModel> SignUp(@Field("user_Name") String user_Name,
                                   @Field("user_Email") String user_Email,
                                   @Field("user_Contact") String user_Contact,
                                   @Field("user_Password") String user_Password);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("sign-in")
    Call<SigninResponseModel> SignIn(@Field("user_Email") String user_Email,
                                     @Field("user_Password") String user_Password);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("save-address")
    Call<UserResponseModel> saveLocation(@Field("user_Id") String user_Id,
                                         @Field("user_Latitude") String user_Latitude,
                                        @Field("user_Longitude") String user_Longitude,
                                        @Field("user_RecentAddress") String user_RecentAddress);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("sub-category-products")
    Call<ProductsResponseModel> getSubCategoryProducts(@Field("product_Category") String product_Category,
                                                       @Field("product_SubCategory") String product_SubCategory);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("get-cart")
    Call<CartDataModel> getCart(@Field("order_UserId") String order_UserId);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("place-order")
    Call<PlaceOrderResponseModel> placeOrder(@Field("order_Id") String order_Id,
                                             @Field("order_UserId") String order_UserId);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("get-order-by-id")
    Call<orderDetailsResponseModel> getOrderDetails(@Field("order_UserId") String order_UserId,
                                                    @Field("order_Id") String order_Id);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("get-current-orders")
    Call<OrderListResponseModel> getCurrentOrders(@Field("order_UserId") String order_UserId);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("get-past-orders")
    Call<OrderListResponseModel> getPastOrders(@Field("order_UserId") String order_UserId);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("get-order-by-id")
    Call<orderDetailsResponseModel> getOrderById(@Field("order_Id") String order_Id);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("search-product")
    Call<ProductsResponseModel> searchProducts(@Field("search_Word") String search_Word);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("get-cart-products-count")
    Call<CartProductsCountResponseModel> getCartProductsCount(@Field("user_Id") String user_Id);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("match-verification-code")
    Call<UserResponseModel> matchVerificationCode(@Field("user_Email") String user_Email,
                                                  @Field("verification_Code") String verification_Code);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("resend-code")
    Call<UserResponseModel> resendVerificationCode(@Field("user_Email") String user_Email);

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("user-info")
    Call<UserResponseModel> getUserInfo(@Field("user_Id") String user_Id);

//    @Headers({
//            "Accept: application/json",
//    })
//    @FormUrlEncoded
//    @POST("get-random-product")
//    Call<ProductsResponseModel> getRandomProduct(@Field("product_Category") String product_Category);
//
//    @Headers({
//            "Accept: application/json",
//    })
//    @POST("get-brick-and-mortar-by-category")
//    Call<BusinessListResponseModel> getBrickAndMortarByCategory(@Body JsonObject jsonBody);
//
//    @Headers({
//            "Accept: application/json",
//    })
//    @POST("get-online-by-category")
//    Call<BusinessListResponseModel> getOnlineByCategory(@Body JsonObject jsonBody);
//
//    @Headers({
//            "Accept: application/json",
//    })
//    @POST("get-nearby-brick-and-mortar-by-category")
//    Call<BusinessListResponseModel> getNearbyBrickAndMortar(@Body JsonObject jsonBody);
//
//    @Headers({
//            "Accept: application/json",
//    })
//    @POST("get-nearby-online-by-category")
//    Call<BusinessListResponseModel> getNearbyOnline(@Body JsonObject jsonBody);
//
//
//    @Headers({
//            "Accept: application/json",
//    })
//
//    @POST("get-business-by-id")
//    Call<BusinessResponseModel> getSingleBusiness(@Body JsonObject jsonBody);
//
//
////    @Headers({
////            "Accept: application/json",
////    })
////    @POST("add-business")
////    Call<BusinessResponseModel> saveBusiness(@Body JsonObject jsonBody);
//    @Headers({
//            "Accept: application/json",
//    })
//    @Multipart
//    @POST("add-business")
//    Call<BusinessResponseModel> saveBusiness(
//                                              @Part MultipartBody.Part business_Logo,
//                                              @Part("business_Name") RequestBody business_Name,
//                                              @Part("owner_Name") RequestBody owner_Name,
//                                              @Part("email") RequestBody email,
//                                              @Part("street_Address") RequestBody street_Address,
//                                              @Part("street_Address2") RequestBody street_Address2,
//                                              @Part("city") RequestBody city,
//                                              @Part("state") RequestBody state,
//                                              @Part("zip_Code") RequestBody zip_Code,
//                                              @Part("phone_No") RequestBody phone_No,
//                                              @Part("opening_Time") RequestBody opening_Time,
//                                              @Part("closing_Time") RequestBody closing_Time,
//                                              @Part("business_Type") RequestBody business_Type,
//                                              @Part("category_Id") RequestBody category_Id,
//                                              @Part("business_Description") RequestBody business_Description,
//                                              @Part("website") RequestBody website,
//                                              @Part("facebook_Link") RequestBody facebook_Link,
//                                              @Part("google_Link") RequestBody google_Link,
//                                              @Part("youtube_Link") RequestBody youtube_Link,
//                                              @Part("travel_DetailsAndFee") RequestBody travel_DetailsAndFee,
//                                              @Part("location_Latitude") RequestBody location_Latitude,
//                                              @Part("location_Longitude") RequestBody location_Longitude
//
//    );

//    @FormUrlEncoded
//    @GET("login")
//    Call<Blog> loginUser(@Field("email") String email,
//                         @Field("password") String password);
//
//    @Headers({
//            "Accept: application/json",
//    })
//    @FormUrlEncoded
//    @POST("signup")
//    Call<AuthResponseModel> registerUser(@Field("f_name") String f_name,
//                                         @Field("l_name") String l_name,
//                                         @Field("email") String email,
//                                         @Field("password") String password,
//                                         @Field("phone") String phone);
//
//    @Headers({
//            "Accept: application/json",
//    })
//    @FormUrlEncoded
//    @POST("trending_posts")
//    Call<PostResponseModel> trendingPosts(@Field("user_id") String user_id);
//
//    @Headers({
//            "Accept: application/json",
//    })
//    @FormUrlEncoded
//    @POST("collection_posts")
//    Call<PostResponseModel> getAllPosts(@Field("user_id") String user_id);
//
//    @Headers({
//            "Accept: application/json",
//    })
//    @FormUrlEncoded
//    @POST("get_favroute")
//    Call<PostResponseModel> getUserFavourites(@Field("user_id") String user_id);
//
//    @Headers({
//            "Accept: application/json",
//    })
//    @FormUrlEncoded
//    @POST("post_by_collections")
//    Call<PostResponseModel> getPostsByCollection(@Field("user_id") String user_id,
//                                                 @Field("collection_name") String collection_name
//    );
//
//    @FormUrlEncoded
//    @POST("reset_password")
//    Call<AuthResponseModel> resetUserPassword(@Field("user_id") String user_id,
//                                              @Field("password") String password,
//                                              @Field("conf_password") String conf_password
//    );
//
//    @FormUrlEncoded
//    @POST("update_phone")
//    Call<UpdateMobileNumberModel> updateMobileNumber(@Field("user_id") String user_id,
//                                                     @Field("phone") String phone
//    );
//
//    @FormUrlEncoded
//    @POST("add_favroute")
//    Call<AddFavouriteResponseModel> add_Favourites(@Field("user_id") String user_id,
//                                                   @Field("post_id") String post_id
//    );
//    @FormUrlEncoded
//    @POST("qoutation")
//    Call<AuthResponseModel> getQuotation(@Field("f_name") String f_name,
//                                         @Field("l_name") String l_name,
//                                         @Field("email") String email,
//                                         @Field("phone") String phone,
//                                         @Field("post_id") String post_id,
//                                         @Field("post_title") String post_title);
//
//    @GET("collections")
//    Call<CollectionsResponseModel> getAllCollections();
//
//    @GET("tranding_collections")
//    Call<CollectionsResponseModel> getTrendingCollection();
}
