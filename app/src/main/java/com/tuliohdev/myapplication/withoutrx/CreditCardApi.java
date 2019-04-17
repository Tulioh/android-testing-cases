package com.tuliohdev.myapplication.withoutrx;

import com.tuliohdev.myapplication.CreditCardEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CreditCardApi {

    @GET("/creditcard/{id}")
    Call<CreditCardEntity> getCreditCardById(@Path("id") String id);

}
