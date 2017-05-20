package sergiocrespotoubes.com.restgenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sergiocrespotoubes.restgenerator.RestGenerator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_result = (TextView) findViewById(R.id.tv_result);

        RestGenerator restGenerator = new RestGenerator("https://jsonplaceholder.typicode.com");
        ApiController apiController = restGenerator.build(ApiController.class);

        String result = apiController.getData();

        tv_result.setText(result);
    }
}
