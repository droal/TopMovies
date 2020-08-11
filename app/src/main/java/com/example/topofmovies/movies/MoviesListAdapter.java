package com.example.topofmovies.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topofmovies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ListItemViewHolder> {

    private List<MoviePOJO> list;

    public MoviesListAdapter(List<MoviePOJO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        holder.itemTitle.setText(list.get(position).getName());
        holder.itemSubTitle.setText(list.get(position).getCountry());
    }


    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public static class ListItemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_item_title)
        public TextView itemTitle;

        @BindView(R.id.tv_item_country)
        public TextView itemSubTitle;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
