package com.sergiocrespotoubes.restgenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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

    public <T> T build(final Class<T> clazz) {

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        if (method.isAnnotationPresent(POST.class)) {
                            return post(method, args);
                        }else if (method.isAnnotationPresent(GET.class)) {
                            return get(method);
                        }
                        return "ERROR";
                    }
                });
    }

    private String post(Method method, Object[] args) {
        POST post = method.getAnnotation(POST.class);

        String myUrl = this.url + post.value();
        result = "error";
        finished = false;
        String body = args[0].toString();

        postCall(myUrl, body, new CallResponse() {
            @Override
            public void getCall(String response) {
                result = response;
                finished = true;
            }
        });

        while(!finished){}

        return result;
    }

    private String get(Method method) {
        GET get = method.getAnnotation(GET.class);

        String myUrl = this.url + get.value();
        result = "error";
        finished = false;

        getCall(myUrl, new CallResponse() {
            @Override
            public void getCall(String response) {
                result = response;
                finished = true;
            }
        });

        while(!finished){}

        return result;
    }

    private void getCall(final String auxUrl, final CallResponse callResponse){
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

    private void postCall(final String auxUrl, final String body, final CallResponse callResponse){
        new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = new URL(auxUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Accept", "application/json");

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(body);

                    writer.flush();
                    writer.close();
                    os.close();

                    if (conn.getResponseCode() != 200 && conn.getResponseCode() != 201) {
                        int responsecode = conn.getResponseCode();
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
