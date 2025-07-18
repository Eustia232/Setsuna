package com.setsuna.controller;

import com.setsuna.dto.PlayGameDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 存放一些上下文无关的，功能测试类的接口。
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    private final AtomicInteger safeNum = new AtomicInteger(0);

    private int num = 0;

    /**
     * 这是一个简单的get示例
     * @return 一个简单的字符串
     */
    @GetMapping("/hello-world")
    public String helloWorld() {
        log.info("hello world");
        log.warn("hello world");
        return "Hello World";
    }

    /**
     * 每次请求times增加1，用于高并发测试
     * @return 请求的次数
     */
    @GetMapping("/hello-times")
    public String helloTimes() {
        return "Hello Times:" + num++;
    }

    @GetMapping("/hello-times-safely")
    public String helloTimesSafely() {
        return "Hello Times safely:" + safeNum.incrementAndGet();
    }

    @PostMapping("hello-world/{time}")
    public String helloWorld(@RequestBody PlayGameDTO playGameDTO, @PathVariable("time") @DateTimeFormat(pattern = "H" +
            ":m:s") LocalTime time) {
        return String.format("%s和%s在%s一起玩%s", playGameDTO
                .getCouple()
                .getHusband(), playGameDTO
                .getCouple()
                .getWife(), time, playGameDTO.getGame());
    }

}
