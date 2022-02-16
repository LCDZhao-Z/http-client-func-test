package io.esastack.test.func.rest;

import io.esastack.restclient.RestResponseBase;
import io.esastack.test.func.ClientUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;

public class RestClientTest {

    private static final String URL_PREFIX = "http://localhost:8080/rest/";
    private static final String JSON = "json";
    private static final String POST_WITHOUT_BODY = "postWithoutBody";
    private static final String POST_WITH_BODY = "postWithBody";
    private static final String MULTIPART_WITHOUT_FILE = "multipartWithoutFile";
    private static final String MULTIPART_WITH_FILE = "multipartWithFile";
    private static final String BIG_FILE_WITH_MULTIPART = "http://localhost:8080/bigFile/uploadWithMultipart";
    private static final String BIG_FILE_WITHOUT_MULTIPART = "http://localhost:8080/bigFile/uploadWithOutMultipart";

    public static void main(String[] args) throws Exception {
        create300MB();
        for (int i = 0; i < 10000; i++) {
            testPostWithBody();
            testPostWithoutBody();
            testMultipartWithFile();
            testMultipartWithoutFile();
            testJson();
            testUploadBigFileWithMultipart();
            testUploadBigFileWithoutMultipart();
            System.out.println(System.currentTimeMillis());
        }
        Thread.sleep(30 * 1000);
    }

    private static void testPostWithoutBody() throws Exception {
        RestResponseBase response = ClientUtils.rest()
                .post(URL_PREFIX + POST_WITHOUT_BODY)
                .execute()
                .toCompletableFuture()
                .get();
        if (response.status() == 200) {
            System.out.println("Success");
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.bodyToEntity(String.class));
        }
    }

    private static void testPostWithBody() throws Exception {
        RestResponseBase response = ClientUtils.rest()
                .post(URL_PREFIX + POST_WITH_BODY)
                .execute()
                .toCompletableFuture()
                .get();
        if (response.status() == 200 && POST_WITH_BODY.equals(response.bodyToEntity(String.class))) {
            System.out.println("Success");
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.bodyToEntity(String.class));
        }
    }

    private static void testMultipartWithoutFile() throws Exception {
        RestResponseBase response = ClientUtils.rest()
                .post(URL_PREFIX + MULTIPART_WITHOUT_FILE)
                .multipart()
                .attr("test", "test")
                .execute()
                .toCompletableFuture()
                .get();
        if (response.status() == 200) {
            System.out.println("Success" +
                    ", body:" + response.bodyToEntity(String.class));
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.bodyToEntity(String.class));
        }
    }

    private static void testMultipartWithFile() throws Exception {
        RestResponseBase response = ClientUtils.rest()
                .post(URL_PREFIX + MULTIPART_WITH_FILE)
                .multipart()
                .attr("test", "test")
                .file("mul", new File("D:/mul.txt"))
                .execute()
                .toCompletableFuture()
                .get();
        if (response.status() == 200) {
            System.out.println("Success" +
                    ", body:" + response.bodyToEntity(String.class));
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.bodyToEntity(String.class));
        }
    }

    private static void testUploadBigFileWithMultipart() throws Exception {
        RestResponseBase response = ClientUtils.rest()
                .post(BIG_FILE_WITH_MULTIPART)
                .multipart()
                .file("file", new File("D:/300MB"))
                .readTimeout(100 * 1000)
                .execute()
                .toCompletableFuture()
                .get();
        if (response.status() == 200) {
            System.out.println("Success" +
                    ", body:" + response.bodyToEntity(String.class));
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.bodyToEntity(String.class));
        }
    }

    private static void testUploadBigFileWithoutMultipart() throws Exception {
        RestResponseBase response = ClientUtils.rest()
                .post(BIG_FILE_WITHOUT_MULTIPART)
                .entity(new File("D:/300MB"))
                .readTimeout(100 * 1000)
                .execute()
                .toCompletableFuture()
                .get();
        if (response.status() == 200) {
            System.out.println("Success" +
                    ", body:" + response.bodyToEntity(String.class));
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.bodyToEntity(String.class));
        }
    }

    private static void testJson() throws Exception {
        RestResponseBase response = ClientUtils.rest()
                .post(URL_PREFIX + JSON)
                .entity(new Person("LiMing", "aa", 22))
                .readTimeout(100 * 1000)
                .execute()
                .toCompletableFuture()
                .get();
        if (response.status() == 200) {
            System.out.println("Success" +
                    ", body:" + response.bodyToEntity(Person.class));
        } else {
            System.err.println("Error occur!status:" + response.status() +
                    ", body:" + response.bodyToEntity(String.class));
        }
    }

    private static void create300MB() throws IOException {
        File file = new File("D:/300MB");
        if (file.exists()) {
            return;
        }
        byte[] bytes = new byte[1024];
        new SecureRandom().nextBytes(bytes);
        if (file.createNewFile()) {
            try (OutputStream out = new FileOutputStream(file)) {
                for (int i = 0; i < 300 * 1024; i++) {
                    out.write(bytes);
                }
                out.flush();
            }
        } else {
            throw new IllegalStateException("Create file failed!");
        }
    }

}
