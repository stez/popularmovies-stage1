package it.and.stez78.popularmovies.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;

import it.and.stez78.popularmovies.model.MovieSearchResult;

/**
 * Created by stefano on 20/02/18.
 */

public class TheMovieDbUtils {

    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String TMDB_IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/";

    private static final String IMAGE_SIZE_SMALL = "w185";
    private static final String IMAGE_SIZE_NORMAL = "w342";
    private static final String IMAGE_SIZE_LARGE = "w500";
    private static final String IMAGE_SIZE_XLARGE = "w780";


    public static String getPosterUrl(Context context, MovieSearchResult movie) {
        String width = getWidth(context);
        return TMDB_IMAGE_BASE_PATH + width + movie.getPosterPath();
    }

    public static String getBackdropUrl(Context context, MovieSearchResult movie, boolean fullSize) {
        String width = fullSize ? IMAGE_SIZE_XLARGE : getWidth(context);
        return TMDB_IMAGE_BASE_PATH + width + movie.getBackdropPath();
    }

    @NonNull
    private static String getWidth(Context context) {
        String width;
        int size = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (size) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                width = IMAGE_SIZE_XLARGE;
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                width = IMAGE_SIZE_LARGE;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                width = IMAGE_SIZE_NORMAL;
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                width = IMAGE_SIZE_SMALL;
                break;
            default:
                width = IMAGE_SIZE_SMALL;
        }
        return width;
    }
}
