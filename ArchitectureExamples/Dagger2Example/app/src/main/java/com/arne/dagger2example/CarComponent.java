package com.arne.dagger2example;

import javax.inject.Named;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Component;

@Component (modules = {WheelsModule.class, PetrolEngineModule.class})
public interface CarComponent {

void inject(MainActivity mainActivity);

@Component.Builder
interface Builder {
    @BindsInstance
    Builder horsePower(@Named("horse power") int horsePower);

    @BindsInstance
    Builder engineCapacity (@Named("engine capacity") int horsePower);

    CarComponent build();
}
}
