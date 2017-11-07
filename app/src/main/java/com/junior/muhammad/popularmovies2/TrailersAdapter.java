package com.junior.muhammad.popularmovies2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.junior.muhammad.popularmovies2.models.MovieTrailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private static final String TAG = TrailersAdapter.class.toString();
    private Context mContext;

    private int mItemsInTheList;

    private OnItemClickListener mOnItemClickListener;

    private List<MovieTrailer> mTrailers;


    public TrailersAdapter(Context context, List<MovieTrailer> data,
                           OnItemClickListener onItemClickListener) {

        mContext = context;

        if (data != null && data.size() > 0) mItemsInTheList = data.size();

        mTrailers = data;

        mOnItemClickListener = onItemClickListener;

    }

    interface OnItemClickListener {
        void onClick(int position);
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int id = R.layout.trailers_card_view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(id, parent, false);

        return new TrailersAdapter.TrailerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {

        MovieTrailer trailer = mTrailers.get(position);

        String trailerKey = trailer.getTrailerKey();
        String trailerName = trailer.getTrailerName();

        String url = Constants.BASE_URL_FOR_TRAILER + trailerKey + Constants.TRAILER_IMAGE_QLT;
        Log.d(TAG, "url is " + url);

        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.place_holder)
                .into(holder.trailerScreen);

        holder.trailerName.setText(trailerName.trim());

        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        Log.d("TrailersAdapter", "s" + mItemsInTheList);
        return mItemsInTheList;
    }


    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailer_view)
        ImageView trailerScreen;
        @BindView(R.id.trailer_name)
        TextView trailerName;

        public TrailerViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            //inject butterKnife library to use the constructor to set it self
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onClick(getAdapterPosition()); //pass the adapter position to our interface
        }
    }
}
