package com.revolut.transfer.di;

import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {
    public AppConfig() {
        register(new AppBinder());
    }
}
