package com.junior.muhammad.popularmovies2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.junior.muhammad.popularmovies2.Constants;
import com.junior.muhammad.popularmovies2.R;
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

    /**
     * Adapter constructor helping setup the Adapter and ViewHolder with
     *
     * @param context             needed to be passed to picasso library
     * @param data                needed to update the views with its data
     * @param onItemClickListener This allow us to use the Adapter as a component with MainActivity
     */
    public TrailersAdapter(Context context, List<MovieTrailer> data,
                           OnItemClickListener onItemClickListener) {

        mContext = context;

        //member variable will be updated with the list size to be returned in getITemCount method
        if (data != null && data.size() > 0) mItemsInTheList = data.size();
        mTrailers = data;

        mOnItemClickListener = onItemClickListener;
    }

    /**
     * interface that will define the listener for trailers clicks
     */
    public interface OnItemClickListener {
        void onClick(int position);
    }

    /**
     * To create our ViewHolder by inflating our XML and returning a new MovieVieHolder
     */
    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int id = R.layout.trailers_card_view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(id, parent, false);

        return new TrailersAdapter.TrailerViewHolder(view);

    }


    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        //get the object associated with this position from the passed list of data
        MovieTrailer trailer = mTrailers.get(position);
        //extract the needed strings
        String trailerKey = trailer.getTrailerKey();
        String trailerName = trailer.getTrailerName();
        //creating a url string to be passed to picasso helping create a url for the trailer thumbnail
        String url = Constants.BASE_URL_FOR_TRAILER + trailerKey + Constants.TRAILER_IMAGE_QLT;
        //using the help of picasso set the image to the layout imageView
        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.trailer_place_holder)
                .into(holder.trailerScreen);
        //set the trailer name to help differentiate the trailers
        holder.trailerName.setText(trailerName.trim());

    }

    @Override
    public int getItemCount() {
        return mItemsInTheList;
    }


    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailer_view)
        ImageView trailerScreen;
        @BindView(R.id.trailer_name)
        TextView trailerName;

        TrailerViewHolder(View itemView) {
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
