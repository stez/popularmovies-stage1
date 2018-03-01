package it.and.stez78.popularmovies.app;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import it.and.stez78.popularmovies.model.MovieSearchResponse;
import it.and.stez78.popularmovies.model.MovieSearchResult;
import it.and.stez78.popularmovies.network.TheMovieDbApiService;

/**
 * Created by stefano on 23/02/18.
 */

public class MainActivityViewModel extends ViewModel {

    public static final int MOST_POPULAR_POSITION = 0;
    public static final int TOP_RATED_POSITION = 1;

    private TheMovieDbApiService theMovieDbApiService;
    private int bottomSelection = MOST_POPULAR_POSITION;

    @Inject
    public MainActivityViewModel(TheMovieDbApiService theMovieDbApiService) {
        this.theMovieDbApiService = theMovieDbApiService;
    }

    public Observable<MovieSearchResponse<MovieSearchResult>> getObservableMovies() {
        return getSearchRequest();
    }

    public void setBottomSelection(int bottomSelection) {
        this.bottomSelection = bottomSelection;
    }

    private Observable<MovieSearchResponse<MovieSearchResult>> getSearchRequest() {
        switch (bottomSelection) {
            case MOST_POPULAR_POSITION:
                return theMovieDbApiService.getPopular();
            case TOP_RATED_POSITION:
                return theMovieDbApiService.getTopRated();
            default:
                return theMovieDbApiService.getPopular();
        }
    }
}
