package com.tuliohdev.myapplication.withrx;

import com.tuliohdev.myapplication.CreditCardEntity;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RxCreditCardApi {

    @GET("/creditcard/{id}")
    Single<CreditCardEntity> getCreditCardById(@Path("id") String id);

    @GET("/creditcard/number/{id}")
    Single<String> getCreditCardNumberByCardId(@Path("id") String id);
}
