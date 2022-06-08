package org.example;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void doQueries() throws IOException, InterruptedException, ParseException {
        int variant = 0;

        long time = System.currentTimeMillis();

        if (variant == 0){
            doMultiThreading();
        }
        else if (variant == 1){
            dummy();
        }

        System.out.println(System.currentTimeMillis() - time);
    }

    private static void dummy() throws InterruptedException, IOException, ParseException {
        int productSize = 30;
        double ratingSum = 0;
        var client = HttpClient.newHttpClient();

        for (int i = 1; i <= productSize; i++) {
            var request = HttpRequest.newBuilder(
                            URI.create("https://dummyjson.com/products/" + i))
                    .GET()
                    .build();
            Thread.sleep(2000);
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = (JSONObject) new JSONParser().parse(response.body());
            double rating = Double.parseDouble(json.get("rating").toString());
            System.out.println(rating);
            ratingSum += rating;
        }

        System.out.println(ratingSum / productSize);
    }

    private static void doMultiThreading() throws InterruptedException {
        AtomicReference<Double> ratingSum = new AtomicReference<>(0.0);
        int productSize = 30;

        ExecutorService executorService = Executors.newFixedThreadPool(productSize);
        List<Callable<Double>> tasks = new ArrayList<>();
        for (int i = 1; i <= productSize; i++) {
            tasks.add(new RequestCallable(i));
        }
        List<Future<Double>> results = executorService.invokeAll(tasks);
        executorService.shutdown();

        results.forEach(result -> ratingSum.updateAndGet(v -> {
            try {
                return v + result.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }));

        System.out.println(ratingSum.get() / productSize);
    }

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        doQueries();
    }
}