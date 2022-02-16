package io.esastack.test.func.http;

import io.esastack.httpclient.core.HttpResponse;
import io.esastack.restclient.RestResponseBase;
import io.esastack.test.func.ClientUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;

public class InterceptTest {

    private static final String URL_PREFIX = "http://localhost:8080/interceptTest/";
    private static final String POST_WITHOUT_BODY = "postWithoutBody";


    public static void main(String[] args) throws Exception {
        testRetry();
    }

//    private static void testPostWithoutBody() throws Exception {
//        HttpResponse response = ClientUtils.client()
//                .post("http://localhost:8080/rest/" + POST_WITHOUT_BODY)
//                .execute()
//                .get();
//        if (response.status() == 200) {
//            System.out.println("Success");
//        } else {
//            System.err.println("Error occur!status:" + response.status() +
//                    ", body:" + response.body().string(StandardCharsets.UTF_8));
//        }
//    }

    private static void testContinue100() throws Exception {
        HttpResponse response = ClientUtils.client()
                .post(URL_PREFIX + "100-continue")
                .body(new File("D://1GB"))
                .maxRetries(0)
                .execute()
                .get();

        if (response.status() == 200) {
            System.out.println("Success");
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.body().string(StandardCharsets.UTF_8));
        }

//        RestResponseBase responseBase = ClientUtils.rest()
//                .post(URL_PREFIX + "100-continue")
//                .entity(new File("D://1GB"))
//                .maxRetries(0)
//                .readTimeout(Integer.MAX_VALUE)
//                .execute()
//                .toCompletableFuture()
//                .get();
//
//        if (responseBase.status() == 200) {
//            System.out.println("Success");
//        } else {
//            System.err.println("Error occur!status:" + responseBase.status() +
//                    ", body:" + responseBase.bodyToEntity(String.class));
//        }
    }

    private static void testReadTimeout() throws Exception {
        HttpResponse response = ClientUtils.client()
                .post(URL_PREFIX + "readTimeout")
                .readTimeout(2000)
                .execute()
                .get();

        if (response.status() == 200) {
            System.out.println("Success");
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.body().string(StandardCharsets.UTF_8));
        }


//        RestResponseBase restResponse = ClientUtils.rest()
//                .post(URL_PREFIX + "readTimeout")
//                .readTimeout(2000)
//                .execute()
//                .toCompletableFuture()
//                .get();
//
//        if (restResponse.status() == 200) {
//            System.out.println("Success");
//        } else {
//            System.err.println("Error occur!status:" + restResponse.status() +
//                    ", body:" + restResponse.bodyToEntity(String.class));
//        }
    }

    private static void testRetry() throws Exception {
//        HttpResponse response = ClientUtils.client()
//                .post(URL_PREFIX + "retry")
//                .maxRetries(5)
//                .execute()
//                .get();
//
//        if (response.status() == 200) {
//            System.out.println("Success");
//        } else {
//            System.err.println("Error occur!status:" + response.status() +
//                    ", body:" + response.body().string(StandardCharsets.UTF_8));
//        }

        RestResponseBase restResponse = ClientUtils.rest()
                .post(URL_PREFIX + "retry")
                .maxRetries(5)
                .execute()
                .toCompletableFuture()
                .get();

        if (restResponse.status() == 200) {
            System.out.println("Success");
        } else {
            System.err.println("Error occur!status:" + restResponse.status() +
                    ", body:" + restResponse.bodyToEntity(String.class));
        }
    }

    private static void testInterceptorAndFilter() throws Exception {
        HttpResponse response = ClientUtils.client()
                .post(URL_PREFIX + "interceptorAndFilter")
                .body("aaaaa".getBytes())
                .execute()
                .get();

        if (response.status() == 200) {
            System.out.println("Success!" + response.version());
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.body().string(StandardCharsets.UTF_8));
        }

        /*RestResponseBase restResponse = ClientUtils.rest()
                .post(URL_PREFIX + "interceptorAndFilter")
                .entity("aaaaa".getBytes())
                .readTimeout(Integer.MAX_VALUE)
                .disableExpectContinue()
                .execute()
                .toCompletableFuture()
                .get();

        if (restResponse.status() == 200) {
            System.out.println("Success!" + restResponse.version());
        } else {
            System.err.println("Error occur!status:" + restResponse.status() +
                    ", body:" + restResponse.bodyToEntity(String.class));
        }*/
    }
}
