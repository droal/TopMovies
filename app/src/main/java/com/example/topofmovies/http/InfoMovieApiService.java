package com.example.topofmovies.http;

import com.example.topofmovies.http.apimodel.ResponseOMDbApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InfoMovieApiService {

    @GET("/")
    Observable<ResponseOMDbApi> getCountry(@Query("t") String title);

}
