package sergiocrespotoubes.com.restgenerator;

import com.sergiocrespotoubes.restgenerator.GET;

/**
 * Created by Sergio on 20-May-17.
 */

public interface PruebaInterface {

    @GET("/users")
    String getData();

}
