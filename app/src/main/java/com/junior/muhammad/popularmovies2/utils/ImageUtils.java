package com.junior.muhammad.popularmovies2.utils;

import android.content.Context;
import android.widget.ImageView;

import com.junior.muhammad.popularmovies2.Constants;
import com.junior.muhammad.popularmovies2.R;
import com.squareup.picasso.Picasso;


/**
 * Using the help of picasso library to fetch our images
 */
public class ImageUtils {

    public static void bindImage(Context context, String imgPath, ImageView intoImage) {

        String url = Constants.IMAGE_QUERY_URL + imgPath;

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.image_place_holder)
                .error(R.drawable.image_broken_holder)
                .into(intoImage);
    }
}
