package com.example.topofmovies.http;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class InfoMovieApiServiceModule {

    public final String INFO_BASE_URL = "https://www.omdbapi.com/";
    public final String INFO_API_KEY = "1547f6ea";


    @Provides
    public OkHttpClient provideHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url2 = request.url().newBuilder().addQueryParameter("apikey", INFO_API_KEY).build();
                        request = request.newBuilder().url(url2).build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    @Provides
    public Retrofit provideRetrofit(String baseUrl, OkHttpClient client){

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    public InfoMovieApiService provideInfoMovieService(){
        return provideRetrofit(INFO_BASE_URL, provideHttpClient()).create(InfoMovieApiService.class);
    }

}
