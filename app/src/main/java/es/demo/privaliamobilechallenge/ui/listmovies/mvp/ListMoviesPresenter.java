package es.demo.privaliamobilechallenge.ui.listmovies.mvp;

import es.demo.privaliamobilechallenge.data.models.MoviesResponse;

public class ListMoviesPresenter implements ListMoviesContract.Presenter,ListMoviesContract.ModelResultListener {
    private ListMoviesContract.View view;
    private ListMoviesModel model;

    public ListMoviesPresenter(ListMoviesContract.View view) {
        this.view = view;
        model = new ListMoviesModel();
    }

    @Override
    public void getMovieList(int page) {
        if (view==null) return;
        model.getMoviesList(page,this);
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
        view.showMessage(reason);
    }
}
