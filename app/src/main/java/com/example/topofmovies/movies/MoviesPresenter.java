package com.example.topofmovies.movies;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MoviesPresenter implements MoviesMVP.Presenter{

    private MoviesMVP.View view;
    private MoviesMVP.Model model;

    private Disposable subscription = null;

    public MoviesPresenter(MoviesMVP.Model model) {
        this.model = model;
    }

    @Override
    public void loadData() {
        subscription = model.result()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MoviePOJO>() {
                    @Override
                    public void onNext(MoviePOJO moviePOJO) {
                        if(view != null){
                            view.updateData(moviePOJO);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if(view != null){
                            view.showMsg("Error cosnultando listado de peliculas");
                        }
                    }

                    @Override
                    public void onComplete() {
                        if(view != null){
                            view.showMsg("Información descargada");
                        }
                    }
                });
    }

    @Override
    public void rxJavaUnsuscribe() {
        if(subscription != null && !subscription.isDisposed()){
            subscription.dispose();
        }
    }

    @Override
    public void setView(MoviesMVP.View view) {
        this.view = view;
    }
}
