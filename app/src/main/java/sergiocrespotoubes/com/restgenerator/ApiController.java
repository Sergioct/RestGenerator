package sergiocrespotoubes.com.restgenerator;

import com.sergiocrespotoubes.restgenerator.GET;

/**
 * Created by Sergio on 20-May-17.
 */

public interface ApiController {

    @GET("/users")
    String getData();

}
