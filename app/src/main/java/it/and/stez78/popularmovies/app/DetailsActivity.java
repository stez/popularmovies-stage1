package it.and.stez78.popularmovies.app;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import it.and.stez78.popularmovies.R;
import it.and.stez78.popularmovies.model.MovieSearchResult;
import it.and.stez78.popularmovies.utils.TheMovieDbUtils;

public class DetailsActivity extends AppCompatActivity {

    public static final String MOVIE_KEY_LABEL = "movieParcelable";

    @BindView(R.id.details_activity_background_image)
    ImageView background;

    @BindView(R.id.details_activity_poster_image)
    ImageView poster;

    @BindView(R.id.details_activity_title)
    TextView title;

    @BindView(R.id.details_activity_release_date)
    TextView releaseDate;

    @BindView(R.id.details_activity_rating_tv)
    TextView rating;

    @BindView(R.id.details_activity_rating_bar)
    RatingBar ratingBar;

    @BindView(R.id.details_activity_overview)
    TextView overview;

    @Inject
    Picasso p;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (getIntent() != null && getIntent().hasExtra(MOVIE_KEY_LABEL)) {
            MovieSearchResult el = getIntent().getParcelableExtra(MOVIE_KEY_LABEL);
            populateViews(el);
        }
    }

    private void populateViews(MovieSearchResult el) {
        p.load(Uri.parse(TheMovieDbUtils.getBackdropUrl(this, el, true))).fit().into(background);
        p.load(Uri.parse(TheMovieDbUtils.getPosterUrl(this, el))).into(poster);
        if (actionBar != null) actionBar.setTitle(el.getTitle());
        title.setText(el.getOriginalTitle());
        releaseDate.setText(el.getReleaseDate());
        rating.setText(getString(R.string.average_rating, el.getVoteAverage()));
        ratingBar.setRating(el.getVoteAverage() / 2);
        overview.setText(el.getOverview());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
