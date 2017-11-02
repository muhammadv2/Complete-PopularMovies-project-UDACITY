package com.junior.muhammad.popularmovies2;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Using the help of picasso library to fetch our images
 */

public class ImageUtils {



    public static void bindImage(Context context, String imgPath, ImageView intoImage) {

        String url = Constants.IMAGE_QUERY_URL + imgPath;

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.error_loading_image)
                .into(intoImage);

    }
}
