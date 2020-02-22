package com.everfino.everfinorest.ApiConnection;

import com.everfino.everfinorest.Models.MenuList;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @GET("rest_Menu/7")
    Call<List<MenuList>> get_Rest_Menu();


    @POST("rest_Menu/add/7")
    Call<MenuList> add_Rest_Menu(@Body JsonObject object);

    @PUT("rest_Menu/modify/7")
    Call<MenuList> update_Rest_Menu(@Query("itemid") int itemid,@Body MenuList obj);

    @DELETE("rest_Menu/delete/7")
    Call<MenuList> delete_Rest_Menu(@Query("itemid") int itemid);

}
