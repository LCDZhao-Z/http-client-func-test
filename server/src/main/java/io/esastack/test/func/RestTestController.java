package io.esastack.test.func;

import esa.httpserver.core.AsyncRequest;
import esa.httpserver.core.AsyncResponse;
import esa.restlight.ext.multipart.annotation.FormParam;
import esa.restlight.ext.multipart.annotation.UploadFile;
import esa.restlight.ext.multipart.core.MultipartFile;
import esa.restlight.spring.shaded.org.springframework.web.bind.annotation.PostMapping;
import esa.restlight.spring.shaded.org.springframework.web.bind.annotation.RequestBody;
import esa.restlight.spring.shaded.org.springframework.web.bind.annotation.RequestMapping;
import esa.restlight.spring.shaded.org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/rest/")
public class RestTestController {

    @PostMapping("/postWithoutBody")
    public void postWithoutBody(AsyncRequest request, AsyncResponse response) throws IOException {
        response.sendResult(200);
    }

    @PostMapping("/postWithBody")
    public void postWithBody(AsyncRequest request, AsyncResponse response) throws IOException {
        response.sendResult(200, "postWithBody".getBytes(StandardCharsets.UTF_8));
    }

    @PostMapping("/multipartWithFile")
    public String testMultipartWithFile(@FormParam String test, @UploadFile MultipartFile mul) throws IOException {
        if (test.equals("test") && mul.string().equals("multipart")) {
            return "multipartWithFile";
        }
        throw new IllegalStateException("Error content!test:" + test + ", mul:" + mul.string());
    }

    @PostMapping("/multipartWithoutFile")
    public String testMultipartWithoutFile(@FormParam String test) throws IOException {
        if (test.equals("test")) {
            return "multipartWithoutFile";
        }
        throw new IllegalStateException("Error content!test:" + test);
    }

    @PostMapping("/json")
    @ResponseBody
    public Person json(@RequestBody Person person) throws IOException {
        if (person.getName().equals("LiMing")) {
            return new Person("WangHong", "aa", 11);
        }
        throw new IllegalStateException("Error content!person:" + person);
    }
}
