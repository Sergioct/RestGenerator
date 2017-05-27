package sergiocrespotoubes.com.restgenerator;


import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Sergio on 20-May-17.
 */

public interface ApiControllerRetrofit {

    @GET("/users")
    String getData();

    @POST("/posts")
    String postData(String body);

}