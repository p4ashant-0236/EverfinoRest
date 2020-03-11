package com.everfino.everfinorest.ApiConnection;

import com.everfino.everfinorest.Models.Liveorder;
import com.everfino.everfinorest.Models.MenuList;
import com.everfino.everfinorest.Models.Order;
import com.everfino.everfinorest.Models.OrderItem;
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

    @GET("rest_liveorder/{restid}")
    Call<List<Liveorder>> get_Rest_Liveorder(@Path("restid") int restid);

    @GET("rest_liveorder/liveorder_order/{restid}")
    Call<List<Liveorder>> get_Rest_Liveorder_per_order(@Path("restid") int restid,@Query("orderid") int orderid);

    @PUT("rest_liveorder/modify_liveorderstatus/{restid}")
    Call<Liveorder> set_Rest_liveorderstatus(@Path("restid") int restid,@Query("liveid") int liveid,@Query("status") String status);



    @GET("rest_order/{restid}")
    Call<List<Order>> get_Rest_order(@Path("restid") int restid);

    @GET("rest_order/single_order/{restid}")
    Call<Order> get_Rest_single_order(@Path("restid") int restid,@Query("orderid") int orderid);


    @GET("rest_order/single_order/orderitems/{restid}")
    Call<List<OrderItem>> get_Rest_single_order_orderitem(@Path("restid") int restid, @Query("orderid") int orderid);

    @PUT("rest_order/modify/{restid}")
    Call<Order> update_Rest_Order(@Path("restid") int restid,@Query("orderid") int orderid,@Body Order obj);


    @GET("rest_user/{restid}")
    Call<List<RestUserResponse>> get_Rest_User(@Path("restid") int restid);

    @GET("rest_user/single/{restid}")
    Call<RestUserResponse> get_Rest_single_User(@Path("restid") int restid,@Query("userid") int userid);

    @POST("rest_user/add/{restid}")
    Call<RestUserResponse> add_Rest_User(@Path("restid") int restid,@Body JsonObject obj);

    @PUT("rest_user/modify/{restid}")
    Call<RestUserResponse> update_Rest_User(@Path("restid") int restid,@Query("userid") int userid,@Body RestUserResponse obj);

    @DELETE("rest_user/delete/{restid}")
    Call<RestUserResponse> delete_Rest_User(@Path("restid") int restid,@Query("userid") int userid);




    @GET("rest_Menu/{restid}")

    Call<List<MenuList>> get_Rest_Menu(@Path("restid") int restid);
    @POST("rest_Menu/add/{restid}")
    Call<MenuList> add_Rest_Menu(@Path("restid") int restid,@Body JsonObject object);

    @PUT("rest_Menu/modify/{restid}")
    Call<MenuList> update_Rest_Menu(@Path("restid") int restid,@Query("itemid") int itemid,@Body MenuList obj);

    @DELETE("rest_Menu/delete/{restid}")
    Call<MenuList> delete_Rest_Menu(@Path("restid") int restid,@Query("itemid") int itemid);



    @GET("rest_Table/{restid}")
    Call<List<TableList>> get_Rest_Table(@Path("restid") int restid);
    @POST("rest_Table/add/{restid}")
    Call<TableList> add_Rest_Table(@Path("restid") int restid,@Body JsonObject object);

    @PUT("rest_Table/modify/{restid}")
    Call<TableList> update_Rest_Table(@Path("restid") int restid,@Query("tableid") int tableid,@Body TableList obj);

    @DELETE("rest_Table/delete/{restid}")
    Call<TableList> delete_Rest_Table(@Path("restid") int restid,@Query("tableid") int tableid);

}
