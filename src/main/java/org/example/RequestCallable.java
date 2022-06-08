package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Callable;

public class RequestCallable implements Callable<Double> {

    private int size;

    public RequestCallable(int size){
        this.size = size;
    }

    @Override
    public Double call() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                        URI.create("https://dummyjson.com/products/" + size))
                .GET()
                .build();
        Thread.sleep(2000);
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject json = (JSONObject) new JSONParser().parse(response.body());
        double rating = Double.parseDouble(json.get("rating").toString());
        System.out.println(rating);
        return rating;
    }
}
