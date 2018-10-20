package es.demo.privaliamobilechallenge.ui.fragments.listmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import es.demo.privaliamobilechallenge.R;
import es.demo.privaliamobilechallenge.commons.BaseFragment;
import es.demo.privaliamobilechallenge.data.models.Movie;
import es.demo.privaliamobilechallenge.data.models.MoviesResponse;
import es.demo.privaliamobilechallenge.ui.adapters.CategoriesAdapter;
import es.demo.privaliamobilechallenge.ui.adapters.MoviesListAdapter;
import es.demo.privaliamobilechallenge.ui.fragments.detailmovie.DetailMovieFragment;
import es.demo.privaliamobilechallenge.ui.fragments.listmovies.mvp.ListMoviesContract;
import es.demo.privaliamobilechallenge.ui.fragments.listmovies.mvp.ListMoviesPresenter;
import es.demo.privaliamobilechallenge.ui.listeners.CategoriesListener;

public class ListMoviesFragment extends BaseFragment implements ListMoviesContract.View
        , MoviesListAdapter.MoviesRecyclerListener
        , SwipeRefreshLayout.OnRefreshListener
        , CategoriesListener{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.llNoInternet)
    LinearLayout llNoInternet;
    @BindView(R.id.llError)
    LinearLayout llError;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerCat)
    RecyclerView recyclerCat;
    private ListMoviesPresenter presenter;
    private int page;
    private int pagesTotal;
    private String name_list;
    MoviesListAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<String> categories;
    List<String> categories_val;
    String selectedCat;
    CategoriesAdapter catAdapter;

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
        createCategories();
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MoviesListAdapter(getActivity(),this);
        recyclerView.setAdapter(adapter);
        presenter = new ListMoviesPresenter(this,getActivity());
        presenter.onAttach(this);
        if (etSearch.getText().toString().isEmpty())    presenter.getMovieList(name_list,page);
        else                                            presenter.getMovieByKeyword(etSearch.getText().toString(),page);

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void createCategories() {
        categories = new ArrayList<>();
        categories_val = new ArrayList<>();
        categories.addAll(Arrays.asList(getResources().getStringArray(R.array.categories)));
        categories_val.addAll(Arrays.asList(getResources().getStringArray(R.array.categories_values)));
        selectedCat = categories_val.get(0);
        catAdapter = new CategoriesAdapter(this,categories);
        recyclerCat.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerCat.setAdapter(catAdapter);
    }

    @Override
    public void showMovies(MoviesResponse response) {
        swipeRefreshLayout.setRefreshing(false);
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
    public void showError() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        etSearch.setEnabled(false);
        llError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoInternetConnection() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        etSearch.setEnabled(false);
        llNoInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(Movie movie) {
        listener.changeFragment(DetailMovieFragment.newInstance(movie));
    }

    @Override
    public void loadMore() {
        if (page < pagesTotal){
            if (getView()!=null)
                Snackbar.make(getView(), R.string.loading_movies, Snackbar.LENGTH_SHORT).show();
            page++;
            presenter.getMovieList(name_list,page);
        }else
            if (getView()!=null)
                Snackbar.make(getView(), R.string.limit, Snackbar.LENGTH_LONG).show();

    }

    @OnTextChanged(value = R.id.etSearch, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterTextChanged(Editable editable){
        if (editable.toString().isEmpty())
            presenter.getMovieList(name_list,page);
        else
            presenter.getMovieByKeyword(editable.toString(),page);
    }

    @OnTouch(R.id.recycler_view)
    boolean onTouch(View v){
        etSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        return false;
    }


    @Override
    public void onRefresh() {
        if (etSearch.getText().toString().isEmpty())    presenter.getMovieList(name_list,page);
        else                                            presenter.getMovieByKeyword(etSearch.getText().toString(),page);
    }

    @Override
    public void onClickCat(int pos) {
        name_list = categories_val.get(pos);
        page = 1;
        presenter.getMovieList(name_list,page);
        if (getView()!=null)
            Snackbar.make(getView(),getString(R.string.getting).replace("REPLACE",categories.get(pos)),Snackbar.LENGTH_SHORT).show();
    }
}
