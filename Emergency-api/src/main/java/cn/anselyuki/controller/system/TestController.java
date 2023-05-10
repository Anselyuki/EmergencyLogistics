package cn.anselyuki.controller.system;

import cn.anselyuki.common.response.ResponseBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AnselYuki
 * @date 2022/10/19 16:04
 */
@RestController
@RequestMapping()
public class TestController {
    @RequestMapping("test")
    public ResponseBean<String> login() {
        return ResponseBean.success("Hello world");
    }
}
