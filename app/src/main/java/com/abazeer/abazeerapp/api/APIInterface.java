package com.abazeer.abazeerapp.api;

import com.abazeer.abazeerapp.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String Password, @Field("cat_id") int cat_id);

}
