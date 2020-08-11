package com.example.topofmovies.movies;

import com.example.topofmovies.http.apimodel.Result;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

public class MoviesModel implements MoviesMVP.Model {

    private  MoviesRepository repository;

    public MoviesModel(MoviesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<MoviePOJO> result() {
        return Observable.zip(repository.getMoviesData(), repository.getCountryData(), new BiFunction<Result, String, MoviePOJO>() {
            @Override
            public MoviePOJO apply(Result movie, String country) throws Exception {
                return new MoviePOJO(movie.getTitle(), country);
            }
        });
    }
}
