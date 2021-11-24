package io.esastack.test.func;

import esa.restlight.ext.multipart.annotation.UploadFile;
import esa.restlight.ext.multipart.core.MultipartFile;
import esa.restlight.spring.shaded.org.springframework.web.bind.annotation.PostMapping;
import esa.restlight.spring.shaded.org.springframework.web.bind.annotation.RequestBody;
import esa.restlight.spring.shaded.org.springframework.web.bind.annotation.RequestMapping;
import esa.restlight.spring.shaded.org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("bigFile")
public class BigFileController {

    @PostMapping("/uploadWithMultipart")
    public String uploadWithMultipart(@UploadFile MultipartFile file) {
        if (file.size() == 300L * 1024L * 1024L) {
            return "SUCCESS";
        }
        throw new UnsupportedOperationException("文件大小不为300M！");
    }

    @PostMapping("/uploadWithOutMultipart")
    public String uploadWithOutMultipart(@RequestBody byte[] body) {
        if (body.length == 1024L * 1024L * 1024L) {
            return "SUCCESS";
        }
        throw new UnsupportedOperationException("文件大小不为1G！");
    }

    @PostMapping("/uploadWithUrlEncoded")
    public String uploadWithUrlEncoded(@RequestParam String name, @RequestParam String sex) {
        if ("bob".equals(name) && "male".equals(sex)) {
            return "SUCCESS";
        }
        throw new UnsupportedOperationException("Unexpected named:" + name + " and sex:" + sex);
    }

}
