package com.yozakuraMinato.j3be.test.api;

import com.yozakuraMinato.j3be.test.api.dto.TestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public TestResponse helloWorld() {
        return new TestResponse("Hello world!");
    }

}

