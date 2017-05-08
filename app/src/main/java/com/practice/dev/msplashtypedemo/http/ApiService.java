package com.practice.dev.msplashtypedemo.http;

import com.practice.dev.msplashtypedemo.http.model.ConfigInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HY on 2017/5/8.
 */

public interface ApiService {
    @GET("tv/stmode.json")
    Call<ConfigInfo> getType();
}
