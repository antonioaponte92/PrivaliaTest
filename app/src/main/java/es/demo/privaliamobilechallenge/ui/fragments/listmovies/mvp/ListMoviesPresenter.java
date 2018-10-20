package es.demo.privaliamobilechallenge.ui.fragments.listmovies.mvp;

import android.content.Context;

import es.demo.privaliamobilechallenge.commons.Utils;
import es.demo.privaliamobilechallenge.data.models.MoviesResponse;

public class ListMoviesPresenter implements ListMoviesContract.Presenter,ListMoviesContract.ModelResultListener {
    private ListMoviesContract.View view;
    private ListMoviesModel model;
    private Context context;

    public ListMoviesPresenter(ListMoviesContract.View view, Context context) {
        this.view = view;
        this.context = context;
        model = new ListMoviesModel();
    }

    @Override
    public void getMovieList(String name_list,int page) {
        if (view==null) return;
        if (Utils.isNetworkAvailable(context))
            model.getMoviesList(name_list,page,this);
        else
            view.showNoInternetConnection();
    }

    @Override
    public void getMovieByKeyword(String query, int page) {
        if (view==null) return;
        if (Utils.isNetworkAvailable(context))
            model.getMoviesByKeywords(query,page,this);
        else
            view.showNoInternetConnection();
    }

    @Override
    public void onAttach(ListMoviesContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void onGetMoviesSuccess(MoviesResponse response) {
        if (view==null) return;
        if (response.getResults().size()==0)
            view.showNoResult();
        else
            view.showMovies(response);
    }

    @Override
    public void onGetMoviesFailed(String reason) {
        if (view==null) return;
        view.showError();
    }
}
