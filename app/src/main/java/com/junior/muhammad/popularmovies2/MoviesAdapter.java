package com.junior.muhammad.popularmovies2;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.junior.muhammad.popularmovies2.models.Movie;
import com.junior.muhammad.popularmovies2.utils.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context mContext;

    private int mItemsInTheList;

    private OnItemClickListener mItemClickListener;

    private List<Movie> movies;

    /**
     * Adapter constructor helping setup the Adapter and ViewHolder with
     *
     * @param context             needed to be passed to picasso library
     * @param data                needed to update the imageView with Movie objects
     * @param onItemClickListener This allow us to use the Adapter as a component with MainActivity
     */
    MoviesAdapter(Context context, List<Movie> data, OnItemClickListener onItemClickListener) {

        mContext = context;

        //member variable will be updated with the list size to be returned in getITemCount method
        if (data != null && data.size() > 0) mItemsInTheList = data.size();

        movies = data;

        mItemClickListener = onItemClickListener;
    }


    /**
     * interface that will define our listener
     */
    interface OnItemClickListener {
        void onClick(int position);
    }

    /**
     * To create our ViewHolder by inflating our XML and returning a new MovieVieHolder
     */
    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int id = R.layout.main_activity_card_view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(id, parent, false);

        return new MoviesViewHolder(view);
    }

    /**
     * By getting our Movie object associated with the @param position and then creating a Url from
     * the base url and every object path to complete the url to be passed to the holder bind method
     */
    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {

        Movie movie = movies.get(position);

        holder.itemView.setTag(position);

        ImageUtils.bindImage(mContext, movie.getPosterPath(), holder.mPosterImageView);

    }


    @Override
    public int getItemCount() {
        return mItemsInTheList; // Simply return the number of the list size
    }


    /**
     * Creating of the ViewHolder class which will help decreasing of using findViewById methods
     */
    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //our only view will be showing in the RecyclerView
        @BindView(R.id.iv_main_screen_poster)
        ImageView mPosterImageView;

        //Constructor help finding our view and set up the view with onClickListener
        MoviesViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            //inject butterKnife library to use the constructor to set it self
            ButterKnife.bind(this, itemView);
        }


        @Override
        public void onClick(View view) {
            mItemClickListener.onClick(getAdapterPosition()); //pass the adapter position to our interface
        }
    }
}

