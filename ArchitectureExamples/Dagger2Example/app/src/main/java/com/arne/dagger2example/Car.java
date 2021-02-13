package com.arne.dagger2example;

import android.util.Log;

import javax.inject.Inject;

public class Car {
    private static final String TAG = "Car";
    
    private Engine engine;
    private Wheels wheels;

    @Inject
    public Car(Engine engine, Wheels wheels) {
        this.engine = engine;
        this.wheels = wheels;
    }

    @Inject
    public void stageSubDependency(SubDependency dependency) {
        dependency.doSomethingWithCar(this);
    }

    public void drive() {
        engine.start();
        Log.d(TAG, " driving...");
    }
}
