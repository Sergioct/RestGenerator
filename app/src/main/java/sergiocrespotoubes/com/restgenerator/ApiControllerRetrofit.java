package sergiocrespotoubes.com.restgenerator;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Sergio on 20-May-17.
 */

public interface ApiControllerRetrofit {

    @GET("/users")
    Call<List<User>> getData();

    @Headers("Content-Type: application/json")
    @POST("/posts")
    String postData(@Body Map<String, Object> body);

}