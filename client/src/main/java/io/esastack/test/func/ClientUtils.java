package io.esastack.test.func;

import esa.commons.collection.AttributeKey;
import io.esastack.commons.net.http.HttpVersion;
import io.esastack.httpclient.core.HttpClient;
import io.esastack.restclient.RestClient;

import java.util.concurrent.CompletableFuture;

public final class ClientUtils {

    private static volatile HttpClient client;
    private static volatile RestClient rest;
    private static final AttributeKey<String> KEY = AttributeKey.valueOf("Key");
    private static final String VALUE = "value";
    private static final String OTHER_VALUE = "otherValue";

    private ClientUtils() {

    }

    public static HttpClient client() {
        if (client == null) {
            client = HttpClient.create()
                    .connectTimeout(1000)
                    .readTimeout(100000)
                    .version(HttpVersion.HTTP_1_1)
                    .addRequestFilter((request, ctx) -> {
                        ctx.attrs().attr(KEY).set(VALUE);
                        return CompletableFuture.completedFuture(null);
                    })
                    .addRequestFilter((request, ctx) -> {
                        String value = ctx.attrs().attr(KEY).get();
                        if (!VALUE.equals(value)) {
                            System.err.println("Attr value error:" + value);
                        } else {
                            System.out.println("Add attr value success!");
                        }
                        ctx.attrs().attr(KEY).set(OTHER_VALUE);
                        return CompletableFuture.completedFuture(null);
                    })
                    .addRequestFilter((request, ctx) -> {
                        String value = ctx.attrs().attr(KEY).get();
                        if (!OTHER_VALUE.equals(value)) {
                            System.err.println("Attr value error:" + value);
                        } else {
                            System.out.println("Change attr value success!");
                        }
                        ctx.attrs().attr(KEY).remove();
                        return CompletableFuture.completedFuture(null);
                    })
                    .addRequestFilter((request, ctx) -> {
                        String value = ctx.attrs().attr(KEY).get();
                        if (value != null) {
                            System.err.println("Attr value error:" + value);
                        } else {
                            System.out.println("Remove attr value success!");
                        }
                        return CompletableFuture.completedFuture(null);
                    })
                    .h2ClearTextUpgrade(false)
                    .useExpectContinue(true)
                    .build();
        }

        return client;
    }

    public static RestClient rest() {
        if (rest == null) {
            rest = RestClient.create()
                    .connectTimeout(1000)
                    .version(HttpVersion.HTTP_1_1)
                    .h2ClearTextUpgrade(false)
                    .useExpectContinue(false)
                    .build();
        }

        return rest;
    }
}
