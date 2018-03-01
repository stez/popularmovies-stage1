package it.and.stez78.popularmovies.app;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.and.stez78.popularmovies.R;
import it.and.stez78.popularmovies.app.adapter.ElementPosterAdapter;
import it.and.stez78.popularmovies.app.adapter.OnItemClickListener;
import it.and.stez78.popularmovies.model.MovieSearchResult;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private static final int MOST_POPULAR_POSITION = 0;
    private static final int TOP_RATED_POSITION = 1;

    @BindView(R.id.main_activity_rv)
    RecyclerView recyclerView;

    @BindView(R.id.main_activity_bottom_nav)
    BottomNavigationView bottomNavigationView;

    private ElementPosterAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Inject
    ViewModelFactory viewModelFactory;
    MainActivityViewModel viewModel;

    @Inject
    Picasso p;

    private List<MovieSearchResult> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);
        setUpRecyclerView();
        setUpBottomNavigation();
        loadMovies(false);
    }

    private void setUpRecyclerView() {
        int gridColumns = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridColumns = 3;
        }
        layoutManager = new StaggeredGridLayoutManager(gridColumns, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ElementPosterAdapter(movies, p, this);
        recyclerView.setAdapter(adapter);
    }

    private void setUpBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.bottom_top_rated:
                        viewModel.setBottomSelection(TOP_RATED_POSITION);
                        break;
                    case R.id.bottom_most_popular:
                        viewModel.setBottomSelection(MOST_POPULAR_POSITION);
                        break;
                }
                loadMovies(true);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            loadMovies(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMovies(final boolean scrollToTop) {
        movies.clear();
        adapter.notifyDataSetChanged();
        viewModel.getObservableMovies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    movies.clear();
                    movies.addAll(result.getResults());
                    adapter.notifyDataSetChanged();
                    if (scrollToTop) recyclerView.smoothScrollToPosition(0);
                }, error -> {
                    Timber.d(error);
                    Toast.makeText(this, R.string.internet_connection_error_msg, Toast.LENGTH_LONG)
                            .show();
                });
    }

    @Override
    public void onItemClick(Object item) {
        Intent detailsActivity = new Intent(this, DetailsActivity.class);
        detailsActivity.putExtra(DetailsActivity.MOVIE_KEY_LABEL, ((MovieSearchResult) item));
        startActivity(detailsActivity);
    }
}
