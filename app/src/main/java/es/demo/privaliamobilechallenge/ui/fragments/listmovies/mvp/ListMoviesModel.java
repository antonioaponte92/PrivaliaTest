package es.demo.privaliamobilechallenge.ui.fragments.listmovies.mvp;

import android.util.Log;

import es.demo.privaliamobilechallenge.BuildConfig;
import es.demo.privaliamobilechallenge.data.models.MoviesResponse;
import es.demo.privaliamobilechallenge.data.remote.MoviesApi;
import es.demo.privaliamobilechallenge.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMoviesModel {
    public static final String TAG = ListMoviesModel.class.getSimpleName();
    MoviesApi api = RetrofitClient.getClient(BuildConfig.BASE_URL).create(MoviesApi.class);
    Call<MoviesResponse> cancellableCall;


    public void getMoviesList(String name_list,int page, final ListMoviesContract.ModelResultListener listener){
        api.getMovies(name_list,BuildConfig.API_KEY,page)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        Log.d(TAG,"onResponse Code: "+response.code());
                        if (response.code()==200){
                            if (response.body()!=null){
                                listener.onGetMoviesSuccess(response.body());
                            }
                        }else{
                            listener.onGetMoviesFailed("Error: "+response.code());
                        }

                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e(TAG,"onFailure "+t.getMessage());
                        listener.onGetMoviesFailed(t.getMessage());
                    }
                });
    }

    public void getMoviesByKeywords(final String query, int page, final ListMoviesContract.ModelResultListener listener){
        cancellableCall = api.getMoviesByKeyword(BuildConfig.API_KEY,query,page);
        cancellableCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Log.d(TAG,"onResponse Code: "+response.code());
                if (response.code()==200){
                    if (response.body()!=null){
                        listener.onGetMoviesSuccess(response.body());
                    }
                }else{
                    listener.onGetMoviesFailed("Error: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG,"onFailure "+t.getMessage());
                if (!t.getMessage().equals("Canceled"))
                    listener.onGetMoviesFailed(t.getMessage());
            }
        });
    }

    public void cancelRequests(){
        if (cancellableCall!=null)
            if (!cancellableCall.isCanceled())
                cancellableCall.cancel();
    }
}
