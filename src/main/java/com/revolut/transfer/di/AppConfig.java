package com.revolut.transfer.di;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {
    public AppConfig() {

        register(new AppBinder());
        register(JacksonFeature.class);
        packages("com.revolut.transfer.dtos");
    }
}
