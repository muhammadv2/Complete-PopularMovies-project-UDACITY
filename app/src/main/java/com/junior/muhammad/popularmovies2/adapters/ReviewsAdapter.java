package com.junior.muhammad.popularmovies2.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junior.muhammad.popularmovies2.R;
import com.junior.muhammad.popularmovies2.models.MovieReviews;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private int mItemsInTheList;

    private List<MovieReviews> mReviewsList;

    public ReviewsAdapter( List<MovieReviews> data) {

        if (data != null && data.size() > 0) mItemsInTheList = data.size();
        mReviewsList = data;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int id = R.layout.reviews_card_view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(id, parent, false);

        return new ReviewsAdapter.ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {

        MovieReviews review = mReviewsList.get(position);

        String author = review.getReviewAuthor();
        String content = review.getReviewContent();

        holder.author.setText(author);
        holder.content.setText(content);
    }

    @Override
    public int getItemCount() {
        return mItemsInTheList;
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_review_author)
        TextView author;
        @BindView(R.id.tv_review_content)
        TextView content;

        public ReviewsViewHolder(View itemView) {
            super(itemView);

            //inject butterKnife library to use the constructor to set it self
            ButterKnife.bind(this, itemView);
        }
    }
}
