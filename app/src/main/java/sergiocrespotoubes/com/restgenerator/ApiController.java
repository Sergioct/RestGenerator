package sergiocrespotoubes.com.restgenerator;

import com.sergiocrespotoubes.restgenerator.GET;
import com.sergiocrespotoubes.restgenerator.POST;

/**
 * Created by Sergio on 20-May-17.
 */

public interface ApiController {

    @GET("/users")
    String getData();

    @POST("/posts")
    String postData(String mybody);

}
