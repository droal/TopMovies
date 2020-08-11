package com.example.topofmovies.movies;

import com.example.topofmovies.http.InfoMovieApiService;
import com.example.topofmovies.http.MoviesApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesModule {

    @Provides
    public MoviesMVP.Presenter provideMoviesPresenter(MoviesMVP.Model model){
        return new MoviesPresenter(model);
    }

    @Provides
    public MoviesMVP.Model provideMoviesModel(MoviesRepository repository){
        return new MoviesModel(repository);
    }

    @Singleton
    @Provides
    public MoviesRepository provideMoviesRepository(MoviesApiService moviesApiService, InfoMovieApiService infoMovieApiService){
        return new TheMovieApiRepository(moviesApiService, infoMovieApiService);
    }
}
