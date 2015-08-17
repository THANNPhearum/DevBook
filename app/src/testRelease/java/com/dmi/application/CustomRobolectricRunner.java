package com.dmi.devbook;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;

public class CustomRobolectricRunner extends RobolectricGradleTestRunner {

    public CustomRobolectricRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
        String buildVariant = (BuildConfig.FLAVOR_server.isEmpty()? "" : BuildConfig.FLAVOR_server)
                + (BuildConfig.FLAVOR_developer.isEmpty()? "" : BuildConfig.FLAVOR_developer + "/")
                + BuildConfig.BUILD_TYPE;

        System.setProperty("android.package", BuildConfig.APPLICATION_ID);
        System.setProperty("android.manifest", "build/intermediates/manifests/full/" + buildVariant + "/AndroidManifest.xml");
        System.setProperty("android.resources", "build/intermediates/res/" + buildVariant);
        System.setProperty("android.assets", "build/intermediates/assets/" + buildVariant);
    }
}