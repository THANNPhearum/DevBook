package com.dmi.devbook.module;

import android.app.Application;

import com.dmi.devbook.BuildConfig;
import com.dmi.devbook.R;
import com.dmi.devbook.loader.LoremLoader;
import com.dmi.devbook.service.LoremService;
import com.dmi.devbook.service.TextConverter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

@Module(injects = {LoremLoader.class})
public class RestModule {

    private final String mEndpoint;

    public RestModule(final Application app) {
        mEndpoint = app.getString(R.string.server_endpoint);
    }

    @Provides
    @Singleton
    public Client provideRetrofitClient() {
        return new OkClient();
    }

    @Provides
    @Singleton
    public RestAdapter provideRestAdapter(final Client client) {
        final RestAdapter.Builder builder = new RestAdapter.Builder()
                .setClient(client)
                .setEndpoint(mEndpoint)
                .setConverter(new TextConverter());

        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        return builder.build();
    }

    @Provides
    @Singleton
    public LoremService provideLoremService(final RestAdapter restAdapter) {
        return restAdapter.create(LoremService.class);
    }
}
