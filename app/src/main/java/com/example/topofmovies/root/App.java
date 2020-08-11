package com.example.topofmovies.root;

import android.app.Application;

import com.example.topofmovies.http.InfoMovieApiServiceModule;
import com.example.topofmovies.http.MoviesApiServiceModule;
import com.example.topofmovies.movies.MoviesModule;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .moviesModule(new MoviesModule())
                .moviesApiServiceModule(new MoviesApiServiceModule())
                .infoMovieApiServiceModule(new InfoMovieApiServiceModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
