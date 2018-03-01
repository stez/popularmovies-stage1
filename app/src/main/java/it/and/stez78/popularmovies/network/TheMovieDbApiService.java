package it.and.stez78.popularmovies.network;

import io.reactivex.Observable;
import it.and.stez78.popularmovies.model.MovieSearchResponse;
import it.and.stez78.popularmovies.model.MovieSearchResult;
import retrofit2.http.GET;

/**
 * Created by stefano on 19/02/18.
 */

public interface TheMovieDbApiService {

    @GET("movie/popular")
    Observable<MovieSearchResponse<MovieSearchResult>> getPopular();

    @GET("movie/top_rated")
    Observable<MovieSearchResponse<MovieSearchResult>> getTopRated();
}
