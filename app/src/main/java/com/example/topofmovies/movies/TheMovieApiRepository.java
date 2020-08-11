package com.example.topofmovies.movies;

import com.example.topofmovies.http.InfoMovieApiService;
import com.example.topofmovies.http.MoviesApiService;
import com.example.topofmovies.http.apimodel.ResponseOMDbApi;
import com.example.topofmovies.http.apimodel.ResponseTheMovieApi;
import com.example.topofmovies.http.apimodel.Result;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class TheMovieApiRepository implements MoviesRepository {

    private MoviesApiService moviesApiService;
    private InfoMovieApiService infoMovieApiService;

    private List<String> countries;
    private List<Result> results;
    private long lastTimestamp;

    private static final long CACHE_LIFETIME = 20 * 100; //Teimpo para actualización de la caché (20 segundos)

    public TheMovieApiRepository(MoviesApiService moviesApiService, InfoMovieApiService infoMovieApiService) {
        this.moviesApiService = moviesApiService;
        this.infoMovieApiService = infoMovieApiService;

        //Tomar tiempo última actualización
        this.lastTimestamp = System.currentTimeMillis();
        //Inicializar arrays
        this.countries = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    //Validar si los datos en caché están actualizados
    public boolean isUpdated(){
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }

    @Override
    public Observable<Result> getMoviesFromNetwork() {
        Observable<ResponseTheMovieApi> topMoviesObservable = moviesApiService.getTopMovies("es-LAT", 1)
                .concatWith(moviesApiService.getTopMovies("es-LAT", 2))
                .concatWith(moviesApiService.getTopMovies("es-LAT", 3));

        return topMoviesObservable
                .concatMap(new Function<ResponseTheMovieApi, ObservableSource<Result>>() {
                    @Override
                    public ObservableSource<Result> apply(ResponseTheMovieApi responseTheMovieApi) throws Exception {
                        return Observable.fromIterable(responseTheMovieApi.getResults());
                    }
                })
                .doOnNext(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        results.add(result);
                    }
                });
    }

    @Override
    public Observable<Result> getMoviesFromCache() {
        if(isUpdated()){
            return Observable.fromIterable(results);
        }
        else{
           lastTimestamp = System.currentTimeMillis();
           results.clear();
           return Observable.empty();
        }
    }

    @Override
    public Observable<Result> getMoviesData() {
        return getMoviesFromCache().switchIfEmpty(getMoviesFromNetwork());
    }



    @Override
    public Observable<String> getCountryFromNetwork() {
        return getMoviesFromNetwork()
                .concatMap(new Function<Result, Observable<ResponseOMDbApi>>() {
                    @Override
                    public Observable<ResponseOMDbApi> apply(Result result) throws Exception {
                        return infoMovieApiService.getCountry(result.getTitle());
                    }
                })
                .concatMap(new Function<ResponseOMDbApi, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(ResponseOMDbApi response) throws Exception {
                        if(response == null || response.getCountry() == null){
                            return Observable.just("Desconocido");
                        }
                        else{
                            return Observable.just(response.getCountry());
                        }
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String country) throws Exception {
                        countries.add(country);
                    }
                });
    }

    @Override
    public Observable<String> getCountryFromCache() {
        if(isUpdated()){
            return Observable.fromIterable(countries);
        }
        else{
            lastTimestamp = System.currentTimeMillis();
            countries.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCountryData() {
        return getCountryFromCache().switchIfEmpty(getCountryFromNetwork());
    }
}
