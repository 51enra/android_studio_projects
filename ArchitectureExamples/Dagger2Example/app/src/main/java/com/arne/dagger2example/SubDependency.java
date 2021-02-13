package com.arne.dagger2example;

import android.util.Log;

import javax.inject.Inject;

public class SubDependency {

    private static final String TAG = "Car";

    @Inject
    SubDependency() {}

    void doSomethingWithCar(Car car) {
        // do something with car object
        Log.d(TAG, "doSomethingWithCar: ");
    }
}
