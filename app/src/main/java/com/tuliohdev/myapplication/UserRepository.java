package com.tuliohdev.myapplication;

import io.reactivex.Completable;

public class UserRepository {

    public Completable changeUserEmail(String email) {
        return Completable.complete();
    }

}
