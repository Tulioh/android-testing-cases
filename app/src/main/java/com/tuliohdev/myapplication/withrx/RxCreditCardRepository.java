package com.tuliohdev.myapplication.withrx;

import com.tuliohdev.myapplication.CreditCardEntity;
import com.tuliohdev.myapplication.Cryptograph;
import com.tuliohdev.myapplication.NetworkUtils;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class RxCreditCardRepository {

    private RxCreditCardApi rxCreditCardApi;

    public RxCreditCardRepository(RxCreditCardApi rxCreditCardApi) {
        this.rxCreditCardApi = rxCreditCardApi;
    }

    public Single<CreditCardEntity> getCreditCardById(String id) {
        return rxCreditCardApi.getCreditCardById(id);
    }

    public Single<String> getCreditCardNumberByCardId(String id) {
        NetworkUtils.checkApplicationIntegrity();

        return rxCreditCardApi.getCreditCardNumberByCardId(id).map(new Function<String, String>() {
            @Override
            public String apply(String creditCardNumber) {
                return Cryptograph.decrypt(creditCardNumber);
            }
        });
    }
}
