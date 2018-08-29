package com.antipov.buildaroute.api;

import com.antipov.buildaroute.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.antipov.buildaroute.common.Const.BASE_URL;

/**
 * Utility for instantiating retrofit
 *
 * Created by AlexanderAntipov on 21.08.2018.
 */

public class RetrofitUtils {

    public static Retrofit getApi() {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        // set up logger for debug
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging);
        }

        Interceptor interceptor = chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            // todo: remove hardcoded key
            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", "AIzaSyCaEXBj4PxlL54wKHx3buhFeYvQmQZwjTs")
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };

        httpClientBuilder.addInterceptor(interceptor);

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .client(httpClientBuilder.build())
                .build();
    }

}
