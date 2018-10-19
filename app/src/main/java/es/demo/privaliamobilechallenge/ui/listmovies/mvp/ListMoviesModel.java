package es.demo.privaliamobilechallenge.ui.listmovies.mvp;

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

    public void getMoviesList(int page, final ListMoviesContract.ModelResultListener listener){
        api.getMovies(BuildConfig.LIST,page,BuildConfig.API_KEY,"vote_average.desc")
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        Log.d(TAG,"onResponse Code: "+response.code());
                        if (response.code()==200){
                            if (response.body()!=null){
                                listener.onGetMoviesSuccess(response.body());
                            }else{
                                listener.onGetMoviesFailed("No data");
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
}
