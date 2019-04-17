package com.tuliohdev.myapplication;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;

enum Screen {
    X, Y, Z
}

public class EmailViewModel {

    private EmailValidator emailValidator;
    private UserRepository userRepository;
    private ResourceManager resourceManager;

    private MutableLiveData<String> invalidEmailLiveData = new MutableLiveData<>();
    private MutableLiveData<Screen> nextScreenLiveData = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public EmailViewModel(EmailValidator emailValidator, UserRepository userRepository, ResourceManager resourceManager) {
        this.emailValidator = emailValidator;
        this.userRepository = userRepository;
        this.resourceManager = resourceManager;
    }

    public void changeEmail(String email) {
        boolean validEmail = emailValidator.isValidEmail(email);
        if (validEmail) {
            changeEmailOnService(email);
        } else {
            invalidEmailLiveData.postValue(resourceManager.getString(R.string.invalid_email));
        }
    }

    private void changeEmailOnService(String email) {
        compositeDisposable.add(userRepository.changeUserEmail(email).subscribe(new Action() {
            @Override
            public void run() {
                nextScreenLiveData.postValue(Screen.X);
            }
        }));
    }

    public LiveData<String> getInvalidEmailLiveData() {
        return invalidEmailLiveData;
    }

    public LiveData<Screen> getNextScreenLiveData() {
        return nextScreenLiveData;
    }

    public void onCleared() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }
}
