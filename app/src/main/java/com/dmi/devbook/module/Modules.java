package com.dmi.devbook.module;

import com.dmi.devbook.DevBookApplication;

/**
 * Created by kevin on 7/6/15.
 */
public final class Modules {
    private Modules() {
        // No instances.
    }

    public static Object[] list(DevBookApplication app) {
        return new Object[]{
            new ApplicationModule(app),
            new RestModule(app),
            new GoogleAnalyticsModule()
        };
    }
}
