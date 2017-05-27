package sergiocrespotoubes.com.restgenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sergiocrespotoubes.restgenerator.RestGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView tv_result;
    TextView tv_result_get;
    TextView tv_result_retrofit;
    TextView tv_result_get_retrofit;
    TextView tv_result_volley;
    TextView tv_result_get_volley;

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
                .build();

        ApiControllerRetrofit service = retrofit.create(ApiControllerRetrofit.class);
    }

    private void volley() {

    }

}
