package es.demo.privaliamobilechallenge.ui.listmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.support.design.widget.Snackbar;
import butterknife.BindView;
import es.demo.privaliamobilechallenge.R;
import es.demo.privaliamobilechallenge.commons.BaseFragment;
import es.demo.privaliamobilechallenge.data.models.Movie;
import es.demo.privaliamobilechallenge.data.models.MoviesResponse;
import es.demo.privaliamobilechallenge.ui.adapters.MoviesListAdapter;
import es.demo.privaliamobilechallenge.ui.detailmovie.DetailMovieFragment;
import es.demo.privaliamobilechallenge.ui.listmovies.mvp.ListMoviesContract;
import es.demo.privaliamobilechallenge.ui.listmovies.mvp.ListMoviesPresenter;

public class ListMoviesFragment extends BaseFragment implements ListMoviesContract.View
        , MoviesListAdapter.MoviesRecyclerListener{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private ListMoviesPresenter presenter;
    private int page;
    private int pagesTotal;
    MoviesListAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    public static ListMoviesFragment newInstance() {
        Bundle args = new Bundle();
        ListMoviesFragment fragment = new ListMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        page = 1;
        presenter = new ListMoviesPresenter(this);
        presenter.onAttach(this);
        presenter.getMovieList(page);
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void showMovies(MoviesResponse response) {
        pagesTotal = response.getTotalPages();
        progressBar.setVisibility(View.GONE);
        if(adapter==null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(true);
            adapter = new MoviesListAdapter(getActivity(),response.getResults(),this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

        }else{
            adapter.addData(response.getResults());
        }
    }

    @Override
    public void showNoResult() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetConnection() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(Movie movie) {
        if (getActivity().getFragmentManager()!=null){
            DetailMovieFragment fragment = DetailMovieFragment.newInstance(movie);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, fragment,"detail")
                    .addToBackStack("movie_detail")
                    .commit();

        }
    }

    @Override
    public void loadMore() {
        if (page < pagesTotal){
            if (getView()!=null)
                Snackbar.make(getView(), R.string.loading_movies, Snackbar.LENGTH_LONG).show();
            page++;
            presenter.getMovieList(page);
        }else
            if (getView()!=null)
                Snackbar.make(getView(), R.string.limit, Snackbar.LENGTH_LONG).show();

    }
}
