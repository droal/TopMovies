package com.example.topofmovies.movies;


import com.example.topofmovies.http.apimodel.Result;

import io.reactivex.Observable;

public interface MoviesRepository {

    Observable<Result> getMoviesFromNetwork();
    Observable<Result> getMoviesFromCache();
    Observable<Result> getMoviesData();

    Observable<String> getCountryFromNetwork();
    Observable<String> getCountryFromCache();
    Observable<String> getCountryData();


}
