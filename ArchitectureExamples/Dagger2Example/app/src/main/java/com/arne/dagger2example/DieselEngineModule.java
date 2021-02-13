package com.arne.dagger2example;

import dagger.Module;
import dagger.Provides;

@Module
public class DieselEngineModule {

    int horsePower;

    public DieselEngineModule(int horsePower) {
        this.horsePower = horsePower;
    }

    @Provides
    int provideHorsePower() {
        return horsePower;
    }

//    @Provides
//    Engine provideEngine() {
//      return new DieselEngine(horsePower);
//    }

    @Provides
    Engine provideEngine(DieselEngine engine) {
        return engine;
    }
}
