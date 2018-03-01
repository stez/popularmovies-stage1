package it.and.stez78.popularmovies.app;

import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import it.and.stez78.popularmovies.network.TheMovieDbApiService;

/**
 * Created by stefano on 23/02/18.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TheMovieDbApiService theMovieDbApiService;

    @Inject
    public ViewModelFactory(TheMovieDbApiService theMovieDbApiService) {
        this.theMovieDbApiService = theMovieDbApiService;
    }

    @Override
    public MainActivityViewModel create(Class modelClass) {
        return new MainActivityViewModel(theMovieDbApiService);
    }
}