package com.abazeer.abazeerapp.api;

import com.abazeer.abazeerapp.model.DataResponse;
import com.abazeer.abazeerapp.model.ItemModel;
import com.abazeer.abazeerapp.model.LocationModel;
import com.abazeer.abazeerapp.model.LoginResponse;
import com.abazeer.abazeerapp.model.OrderItemModel;
import com.abazeer.abazeerapp.model.OrderModel;
import com.abazeer.abazeerapp.model.ProductModel;
import com.abazeer.abazeerapp.model.ProductsModel;
import com.abazeer.abazeerapp.model.ReturnItemModel;
import com.abazeer.abazeerapp.model.ReturnOrderModel;
import com.abazeer.abazeerapp.model.StanderResponse;
import com.abazeer.abazeerapp.model.YearModel;
import com.abazeer.abazeerapp.model.ZoneModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String Password, @Field("cat_id") int cat_id);
    @Headers("Accept: application/json")
    @GET("getOrders")
    Call<DataResponse<OrderModel>> getOrder(@Header("Authorization") String Authorization);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("getItems")
    Call<DataResponse<OrderItemModel>> getItems(@Header("Authorization") String Authorization, @Field("name") String name);

    @Headers("Accept: application/json")
//    @FormUrlEncoded
    @POST("delivered")
    Call<StanderResponse> delivered(@Header("Authorization") String Authorization, @Body JsonObject order);

    @Headers("Accept: application/json")
    @GET("getReturns")
    Call<DataResponse<ReturnOrderModel>> getReturns(@Header("Authorization") String Authorization);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("getReturnsItems")
    Call<DataResponse<ReturnItemModel>> getReturnsItems(@Header("Authorization") String Authorization, @Field("return_id") int return_id);



    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("product")
    Call<DataResponse<ProductsModel>> searchproduct(@Header("Authorization") String Authorization, @Field("q") String q);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("location")
    Call<DataResponse<LocationModel>> addlocation(@Header("Authorization") String Authorization, @Field("name") String name, @Field("zone_id") int zone_id);

    @Headers("Accept: application/json")
    @GET("zone")
    Call<DataResponse<ZoneModel>> zone(@Header("Authorization") String Authorization);

    @Headers("Accept: application/json")
    @GET("odoolocation")
    Call<DataResponse<ZoneModel>> getOdooLocation(@Header("Authorization") String Authorization);


    @Headers("Accept: application/json")
    @GET("year")
    Call<DataResponse<YearModel>> year(@Header("Authorization") String Authorization);

    @Headers("Accept: application/json")
    @GET("zone/{zone}")
    Call<DataResponse<LocationModel>> location(@Header("Authorization") String Authorization, @Path("zone") int zone);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("invind")
    Call<DataResponse<LocationModel>> getOdooLocationZone(@Header("Authorization") String Authorization, @Field("odoo_location") int location);

    @Headers("Accept: application/json")
    @GET("location/{location}")
    Call<DataResponse<ProductModel>> products(@Header("Authorization") String Authorization, @Path("location") int location);


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("inventory")
    Call<DataResponse<ProductModel>> addproducts(@Header("Authorization") String Authorization,
                                                 @Field("location_id") int location,
                                                 @Field("expirydate") String expirydate,
                                                 @Field("product_id") int product_id,
                                                 @Field("product_name") String product_name,
                                                 @Field("description") String description,
                                                 @Field("quantity") String quantity,
                                                 @Field("quantitycheck") int quantitycheck,
                                                 @Field("unit_name") String unit_name,
                                                 @Field("unit_id") int unit_id,
                                                 @Field("lot_id") int lot_id,
                                                 @Field("lot_name") String lot_name,
                                                 @Field("odoo_location_id") int odoo_location_id);



    @PUT("inventory/{inventory}")
    Call<DataResponse<ProductModel>> updateproducts(@Header("Authorization") String Authorization, @Path("inventory") int id,@Body JsonObject data);
    @DELETE("inventory/{inventory}")
    Call<DataResponse<ProductModel>> deleteproducts(@Header("Authorization") String Authorization, @Path("inventory") int id);

    @Headers("Accept: application/json")
    @GET("inventory/{inventory}")
    Call<DataResponse<ItemModel>> product(@Header("Authorization") String Authorization, @Path("inventory") int inventory);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("odoocountquantity")
    Call<DataResponse<ProductModel>> addproductsodoo(@Header("Authorization") String Authorization,
                                                 @Field("location_id") int location,
                                                 @Field("expirydate") String expirydate,
                                                 @Field("product_id") int product_id,
                                                 @Field("product_name") String product_name,
                                                 @Field("description") String description,
                                                 @Field("quantity") String quantity,
                                                 @Field("quantitycheck") int quantitycheck,
                                                 @Field("unit_name") String unit_name,
                                                 @Field("unit_id") int unit_id,
                                                 @Field("lot_id") int lot_id,
                                                 @Field("lot_name") String lot_name,
                                                 @Field("odoo_location_id") int odoo_location_id);


    @Headers("Accept: application/json")
//    @FormUrlEncoded
    @PUT("odoocountquantity/{odoocountquantity}")
    Call<DataResponse<ProductModel>> updateproductsodoo(@Header("Authorization") String Authorization, @Path("odoocountquantity") int id,@Body JsonObject data);
    @DELETE("odoocountquantity/{odoocountquantity}")
    Call<DataResponse<ProductModel>> deleteproductsodoo(@Header("Authorization") String Authorization, @Path("odoocountquantity") int id);

    @Headers("Accept: application/json")
    @GET("odoocountquantity/{odoocountquantity}")
    Call<DataResponse<ItemModel>> productodoo(@Header("Authorization") String Authorization, @Path("odoocountquantity") int inventory);
}
