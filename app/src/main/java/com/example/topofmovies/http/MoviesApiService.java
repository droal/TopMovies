package com.example.topofmovies.http;

import com.example.topofmovies.http.apimodel.ResponseTheMovieApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApiService {

    @GET("top_rated")
    Observable<ResponseTheMovieApi> getTopMovies(@Query("language") String language,
                                                 @Query("page") Integer page);
}
