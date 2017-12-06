# RestGenerator
Android Restful generator

#### Init Rest Generator

```
RestGenerator restGenerator = new RestGenerator("https://jsonplaceholder.typicode.com");
ApiController apiController = restGenerator.build(ApiController.class);
```

#### Api Controller example

```
public interface ApiController {

    @GET("/users")
    String getData();

    @POST("/posts")
    String postData(String mybody);

}
```

## Get

```
String resultGet = apiController.getData();
```

## Post

```
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
String resultPost = apiController.postData(body);
```

## Inspired

This project have being inspired in retrofit.
