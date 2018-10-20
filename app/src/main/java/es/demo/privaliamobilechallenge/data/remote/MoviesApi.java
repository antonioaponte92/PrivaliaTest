package es.demo.privaliamobilechallenge.data.remote;

import es.demo.privaliamobilechallenge.data.models.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {
    @Headers("Content-Type:application/json")
    @GET("movie/{list}")
    Call<MoviesResponse> getMovies(@Path("list") String name_list,
                                   @Query("api_key") String key,
                                   @Query("page") int page);

    @Headers("Content-Type:application/json")
    @GET("search/movie")
    Call<MoviesResponse>getMoviesByKeyword(@Query("api_key") String key,
                                           @Query("query") String query,
                                           @Query("page") int page);
}
