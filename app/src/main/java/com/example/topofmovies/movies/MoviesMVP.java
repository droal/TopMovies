package com.example.topofmovies.movies;

import io.reactivex.Observable;

public interface MoviesMVP {

    interface View{
        void updateData(MoviePOJO movie);

        void showMsg(String msg);
    }

    interface Presenter{
        void loadData();

        void rxJavaUnsuscribe();

        void setView(MoviesMVP.View view);
    }

    interface Model{
        Observable<MoviePOJO> result();
    }
}
