package sergiocrespotoubes.com.restgenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sergiocrespotoubes.restgenerator.RestGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<List<User>> {

    TextView tv_result;
    TextView tv_result_get;
    TextView tv_result_retrofit;
    TextView tv_result_get_retrofit;
    TextView tv_result_volley;
    TextView tv_result_get_volley;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_result          = (TextView) findViewById(R.id.tv_result);
        tv_result_get          = (TextView) findViewById(R.id.tv_result_get);
        tv_result_retrofit = (TextView) findViewById(R.id.tv_result_retrofit);
        tv_result_get_retrofit = (TextView) findViewById(R.id.tv_result_get_retrofit);
        tv_result_volley   = (TextView) findViewById(R.id.tv_result_volley);
        tv_result_get_volley   = (TextView) findViewById(R.id.tv_result_get_volley);

        restGenerator();
        retrofit();
        volley();
    }

    private void restGenerator() {

        RestGenerator restGenerator = new RestGenerator("https://jsonplaceholder.typicode.com");
        ApiController apiController = restGenerator.build(ApiController.class);

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonData = new JSONObject();

        try {
            jsonData.put("title", "titulo");
            jsonData.put("body", "cuerpo");
            jsonData.put("userId", 1);
            jsonObject.put("data", jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String body = jsonObject.toString();
        String result = apiController.postData(body);

        String resultGet = apiController.getData();

        tv_result.setText(result);
        tv_result_get.setText(resultGet);
    }

    private void retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiControllerRetrofit service = retrofit.create(ApiControllerRetrofit.class);

        Map<String, Object>map = new HashMap<String, Object>();

        map.put("title", "titulo");
        map.put("body", "cuerpo");
        map.put("userId", 1);

        Call<List<User>> call = service.getData();
        call.enqueue(this);
    }

    private void volley() {
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/posts",
                null,
                new com.android.volley.Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("TEST", "onResponse: "+response.toString());
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected com.android.volley.Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                int statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        if(response.isSuccessful()) {
            List<User> changesList = response.body();

            for (int i = 0; i < changesList.size(); i++) {
                Log.i("TEST", "onResponse: "+changesList.get(i).getName());
            }
        }
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {

    }

}
