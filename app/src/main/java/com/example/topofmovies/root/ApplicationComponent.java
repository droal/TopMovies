package com.example.topofmovies.root;

import com.example.topofmovies.MainActivity;
import com.example.topofmovies.http.InfoMovieApiServiceModule;
import com.example.topofmovies.http.MoviesApiServiceModule;
import com.example.topofmovies.movies.MoviesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, MoviesModule.class, MoviesApiServiceModule.class, InfoMovieApiServiceModule.class})
public interface ApplicationComponent {

    void inject(MainActivity targer);
}
