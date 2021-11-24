package io.esastack.test.func;

import esa.httpserver.core.AsyncRequest;
import esa.httpserver.core.AsyncResponse;
import esa.restlight.spring.shaded.org.springframework.web.bind.annotation.PostMapping;
import esa.restlight.spring.shaded.org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("interceptTest")
public class InterceptTestController {

    private volatile boolean isContinue = true;
    private volatile AtomicInteger retryTimes = new AtomicInteger(0);

    @PostMapping("/100-continue")
    public void continue100(AsyncRequest request, AsyncResponse response) throws IOException {
        response.sendResult(200, "Success".getBytes());
    }

    @PostMapping("/readTimeout")
    public void readTimeout(AsyncRequest request, AsyncResponse response) throws IOException, InterruptedException {
        Thread.sleep(15 * 1000L);
    }

    @PostMapping("/interceptorAndFilter")
    public void interceptorAndFilter(AsyncRequest request, AsyncResponse response) throws IOException, InterruptedException {
        System.out.println(new String(request.body()));
        response.sendResult(200, "Success".getBytes());
    }

}
