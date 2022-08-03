package com.imall.task.controller;

import com.imall.task.jobhandler.SampleJob;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

/**
 * 测试
 *
 * @author zhangpengjun
 * @date 2022/7/13
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SampleJob sampleJob;

    @GetMapping("/pushJson")
    public String pushJson() throws Exception {
        sampleJob.demoJobHandler();
        return "测试发送 ok~";
    }

    @PostMapping("/acceptJson")
    public String acceptJson(@RequestBody AcceptJsonTestVo vo) throws Exception {
        String data = new String(Base64.getDecoder().decode(vo.getJsonData()));
        log.warn("收到推送信息 data: {}", data);
        return "测试接受 ok~";
    }

    @Data
    public static class AcceptJsonTestVo {
        private String jsonData;
    }
}
