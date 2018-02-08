package com.smart.interview.network;

import com.smart.interview.network.bean.TestJsonArrayBean;
import com.smart.interview.network.bean.TestJsonObjectBean;


import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ZhangPeng on 2-8-0008.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("jsonObject")
    public Observable<TestJsonObjectBean> getJsonObject(@FieldMap Map<String, String> map);

    @GET("jsonArray")
    public Observable<TestJsonArrayBean> getJsonArray(@Query("name") String name, @Query("pwd") String pwd);
}
