package com.example.topofmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.example.topofmovies.movies.MoviePOJO;
import com.example.topofmovies.movies.MoviesListAdapter;
import com.example.topofmovies.movies.MoviesMVP;
import com.example.topofmovies.root.App;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesMVP.View {

    private final String TAG = MainActivity.class.getName();

    @BindView(R.id.ll_main_container)
    ViewGroup llContainer;

    @BindView(R.id.rv_main_movies)
    RecyclerView rvMovies;


    @Inject
    MoviesMVP.Presenter presenter;

    private MoviesListAdapter listAdapter;
    private List<MoviePOJO> moviesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        listAdapter = new MoviesListAdapter(moviesList);
        rvMovies.setAdapter(listAdapter);
        rvMovies.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        rvMovies.setItemAnimator(new DefaultItemAnimator());
        rvMovies.setHasFixedSize(false);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.rxJavaUnsuscribe();
        moviesList.clear();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(MoviePOJO movie) {
        moviesList.add(movie);
        listAdapter.notifyItemInserted(moviesList.size()-1);
        Log.d(TAG, "Nuevo dato: "+ movie.getName());
    }

    @Override
    public void showMsg(String msg) {
        Snackbar.make(llContainer, msg, Snackbar.LENGTH_SHORT).show();
    }
}