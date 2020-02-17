package com.everfino.everfinorest.ApiConnection;

import com.everfino.everfinorest.Models.MenuList;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @GET("rest_Menu/7")
    Call<List<MenuList>> get_Rest_Menu();

}
