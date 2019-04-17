package com.tuliohdev.myapplication.withoutrx;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.tuliohdev.myapplication.CreditCardEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditCardRepository {

    private CreditCardApi creditCardApi;

    public CreditCardRepository(CreditCardApi creditCardApi) {
        this.creditCardApi = creditCardApi;
    }

    public LiveData<ResponseCallback<CreditCardEntity>> getCreditCardById(String id) {
        final MutableLiveData<ResponseCallback<CreditCardEntity>> creditCardEntityMutableLiveData = new MutableLiveData<>();

        creditCardApi.getCreditCardById(id).enqueue(new Callback<CreditCardEntity>() {
            @Override
            public void onResponse(Call<CreditCardEntity> call, Response<CreditCardEntity> response) {
                creditCardEntityMutableLiveData.postValue(new ResponseCallback<CreditCardEntity>(response.body()));
            }

            @Override
            public void onFailure(Call<CreditCardEntity> call, Throwable t) {
                creditCardEntityMutableLiveData.postValue(new ResponseCallback<CreditCardEntity>(t));
            }
        });

        return creditCardEntityMutableLiveData;
    }
}
