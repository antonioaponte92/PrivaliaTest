package es.demo.privaliamobilechallenge.data.remote;

import es.demo.privaliamobilechallenge.data.models.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {
    @Headers("Content-Type:application/json")
    @GET("list/{id_list}")
    Call<MoviesResponse> getMovies(@Path("id_list")  int id,
                                   @Query("page")    int page,
                                   @Query("api_key") String key,
                                   @Query("sort_by") String order);
}
