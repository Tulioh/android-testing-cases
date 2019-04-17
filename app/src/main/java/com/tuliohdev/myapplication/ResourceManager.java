package com.tuliohdev.myapplication;

import android.content.Context;

public class ResourceManager {

    private Context context;

    public ResourceManager(Context context) {
        this.context = context;
    }

    public String getString(int resourceId) {
        return context.getString(resourceId);
    }
}
