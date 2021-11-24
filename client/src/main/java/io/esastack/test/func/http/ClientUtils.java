package io.esastack.test.func.http;

import io.esastack.commons.net.http.HttpVersion;
import io.esastack.httpclient.core.HttpClient;
import io.esastack.restclient.RestClient;

public final class ClientUtils {

    private static volatile HttpClient client;
    private static volatile RestClient rest;

    private ClientUtils() {

    }

    public static HttpClient client() {
        if (client == null) {
            client = HttpClient.create()
                    .connectTimeout(1000)
                    .readTimeout(100000)
                    .version(HttpVersion.HTTP_1_1)
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
                    .useExpectContinue(true)
                    .build();
        }

        return rest;
    }
}
