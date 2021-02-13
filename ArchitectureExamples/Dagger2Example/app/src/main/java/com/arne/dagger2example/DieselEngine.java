package com.arne.dagger2example;

import android.util.Log;

import javax.inject.Inject;

public class DieselEngine implements Engine {

    private static final String TAG = "Car";

    //Variable will be set at Runtime!
    private int horsePower;

    @Inject
    public DieselEngine(int horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public void start() {
        Log.d(TAG, "start: Diesel engine starts. Horsepower: " + horsePower);
    }
}
