package com.sergiocrespotoubes.restgenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Semaphore;

/**
 * Created by Sergio on 20-May-17.
 */

public class RestGenerator {

    String url;
    String result;
    Semaphore semaphore;
    boolean finished;

    public RestGenerator(String url) {
        this.url = url;
    }

    public void parse(Class<?> clazz) {
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {

            if (method.isAnnotationPresent(POST.class)) {
                post(method);
            }else if (method.isAnnotationPresent(GET.class)) {
                get(method);
            }
        }
    }

    public <T> T build(final Class<T> clazz) {

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        if (method.isAnnotationPresent(POST.class)) {
                            return post(method);
                        }else if (method.isAnnotationPresent(GET.class)) {
                            return get(method);
                        }
                        return "ERROR";
                    }
                });
    }

    private String post(Method method) {
        POST post = method.getAnnotation(POST.class);

        //Class classCalled = test.expected();
        Class declaringClass = method.getDeclaringClass();
        Class theClass = method.getClass();

        String url;

        url = this.url + post.value();


        return "post";
    }

    private String get(Method method) {
        GET get = method.getAnnotation(GET.class);

        //Class classCalled = test.expected();
        Class declaringClass = method.getDeclaringClass();
        Class theClass = method.getClass();

        String myUrl = this.url + get.value();
        result = "error";
        finished = false;

        serverCall(myUrl, new CallResponse() {
            @Override
            public void getCall(String response) {
                result = response;
                finished = true;
            }
        });

        while(!finished){

        }

        return result;
    }

    private void serverCall(final String auxUrl, final CallResponse callResponse){
        new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = new URL(auxUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept", "application/json");

                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (conn.getInputStream())));

                    String output = "";
                    String result = "";
                    System.out.println("Output from Server .... \n");
                    while ((output = br.readLine()) != null) {
                        result += output;
                    }

                    callResponse.getCall(result);
                    conn.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
