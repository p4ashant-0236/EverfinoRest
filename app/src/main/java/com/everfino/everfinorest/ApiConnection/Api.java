package com.everfino.everfinorest.ApiConnection;

import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.Models.RegisterRestResponse;

import com.everfino.everfinorest.Models.RestUserResponse;
import com.everfino.everfinorest.Models.TableList;
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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @POST("rest_staff_login/{restid}")
    Call<RestUserResponse> rest_staff_login(@Path("restid") int restid,@Body JsonObject obj);

    @POST("rest/add")
    Call<RegisterRestResponse>  register_rest(@Body JsonObject obj);

    @POST("rest_user/add/{restid}")
    Call<RestUserResponse> add_Manager(@Path("restid") int restid,@Body JsonObject obj);

    @GET("rest_Menu/7")
    Call<List<MenuList>> get_Rest_Menu();
    @POST("rest_Menu/add/7")
    Call<MenuList> add_Rest_Menu(@Body JsonObject object);

    @PUT("rest_Menu/modify/7")
    Call<MenuList> update_Rest_Menu(@Query("itemid") int itemid,@Body MenuList obj);

    @DELETE("rest_Menu/delete/7")
    Call<MenuList> delete_Rest_Menu(@Query("itemid") int itemid);



    @GET("rest_Table/7")
    Call<List<TableList>> get_Rest_Table();
    @POST("rest_Table/add/7")
    Call<TableList> add_Rest_Table(@Body JsonObject object);

    @PUT("rest_Table/modify/7")
    Call<TableList> update_Rest_Table(@Query("tableid") int tableid,@Body TableList obj);

    @DELETE("rest_Table/delete/7")
    Call<TableList> delete_Rest_Table(@Query("tableid") int tableid);

}
