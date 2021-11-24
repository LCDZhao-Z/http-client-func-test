package io.esastack.test.func.http;

import io.esastack.commons.net.http.HttpHeaderNames;
import io.esastack.commons.net.http.HttpVersion;
import io.esastack.commons.net.http.MediaTypeUtil;
import io.esastack.httpclient.core.HttpClient;
import io.esastack.httpclient.core.HttpResponse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public class BigFileSender {

    private static final String FILE_NAME = "D://ebs-test.zip";
    private static final String URL_PREFIX = "http://localhost:8080/bigFile/";
    private static volatile HttpClient client;


    public static void main(String args[]) throws Exception {
        sendWithMultipart();
    }

    private static void create1GFile() throws IOException {
        File file = new File(FILE_NAME);
        file.createNewFile();
        byte[] data = new byte[1024];
        ThreadLocalRandom.current().nextBytes(data);
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        for (int i = 0; i < 1024 * 1024; i++) {
            outputStream.write(data);
        }
        outputStream.flush();
        outputStream.close();
    }

    private static void sendWithoutMultipart() throws ExecutionException, InterruptedException {
        HttpResponse response = ClientUtils.client()
                .post(URL_PREFIX + "uploadWithOutMultipart")
                .addHeader(HttpHeaderNames.CONTENT_TYPE, MediaTypeUtil.TEXT_PLAIN.value())
                .body(new File(FILE_NAME))
                .execute().get();

        if (response.status() == 200) {
            System.out.println("Success");
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.body().string(StandardCharsets.UTF_8));
        }
    }

    private static void sendWithMultipart() throws ExecutionException, InterruptedException {
        HttpResponse response = HttpClient.ofDefault()
                .post(URL_PREFIX + "uploadWithMultipart")
                .multipart()
                .attr("uploadPath","aaa")
                .file("file", new File(FILE_NAME))
                .execute().get();

        if (response.status() == 200) {
            System.out.println("Success");
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.body().string(StandardCharsets.UTF_8));
        }
    }

    private static void sendWithUrlEncoded() throws ExecutionException, InterruptedException {
        HttpResponse response = ClientUtils.client()
                .post(URL_PREFIX + "uploadWithUrlEncoded")
                .multipart()
                .attr("name", "bob")
                .attr("sex", "male")
                .multipartEncode(false)
                .execute().get();

        if (response.status() == 200) {
            System.out.println("Success");
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.body().string(StandardCharsets.UTF_8));
        }
    }

}
