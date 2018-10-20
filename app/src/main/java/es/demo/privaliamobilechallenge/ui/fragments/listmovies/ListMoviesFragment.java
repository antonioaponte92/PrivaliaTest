package es.demo.privaliamobilechallenge.ui.fragments.listmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import es.demo.privaliamobilechallenge.R;
import es.demo.privaliamobilechallenge.commons.BaseFragment;
import es.demo.privaliamobilechallenge.data.models.Movie;
import es.demo.privaliamobilechallenge.data.models.MoviesResponse;
import es.demo.privaliamobilechallenge.ui.adapters.MoviesListAdapter;
import es.demo.privaliamobilechallenge.ui.fragments.detailmovie.DetailMovieFragment;
import es.demo.privaliamobilechallenge.ui.fragments.listmovies.mvp.ListMoviesContract;
import es.demo.privaliamobilechallenge.ui.fragments.listmovies.mvp.ListMoviesPresenter;

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
    private String name_list;
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
        name_list = "popular";
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MoviesListAdapter(getActivity(),this);
        recyclerView.setAdapter(adapter);
        presenter = new ListMoviesPresenter(this);
        presenter.onAttach(this);
        presenter.getMovieList(name_list,page);
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void showMovies(MoviesResponse response) {
        pagesTotal = response.getTotalPages();
        progressBar.setVisibility(View.GONE);
        if (page==1)    adapter.setData(response.getResults());
        else            adapter.addData(response.getResults());

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
        listener.changeFragment(DetailMovieFragment.newInstance(movie));
    }

    @Override
    public void loadMore() {
        if (page < pagesTotal){
            if (getView()!=null)
                Snackbar.make(getView(), R.string.loading_movies, Snackbar.LENGTH_LONG).show();
            page++;
            presenter.getMovieList(name_list,page);
        }else
            if (getView()!=null)
                Snackbar.make(getView(), R.string.limit, Snackbar.LENGTH_LONG).show();

    }
}
