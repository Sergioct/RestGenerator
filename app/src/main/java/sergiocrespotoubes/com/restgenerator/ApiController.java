package sergiocrespotoubes.com.restgenerator;

import com.sergiocrespotoubes.restgenerator.GET;
import com.sergiocrespotoubes.restgenerator.POST;

/**
 * Created by Sergio on 20-May-17.
 */

public interface ApiController {

    @POST("/")
    public String postData();

    @GET("/")
    public String getData();

}
