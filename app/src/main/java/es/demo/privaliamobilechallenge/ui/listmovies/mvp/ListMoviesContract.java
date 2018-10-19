package es.demo.privaliamobilechallenge.ui.listmovies.mvp;

import es.demo.privaliamobilechallenge.commons.MVPContract;
import es.demo.privaliamobilechallenge.data.models.MoviesResponse;

public class ListMoviesContract extends MVPContract{
    public interface ModelResultListener{
        void onGetMoviesSuccess(MoviesResponse response);
        void onGetMoviesFailed(String reason);
    }

    public interface Presenter extends MVPContract.Presenter<View>{
        void getMovieList(int page);
    }

    public interface View{
        void showMessage(String message);
        void showMovies(MoviesResponse response);
        void showNoResult();
        void showNoInternetConnection();
    }
}
