package com.yozakuraMinato.j3be.test.api;

import com.yozakuraMinato.j3be.test.api.dto.TestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mock")
public class TestController {

    @GetMapping
    public TestResponse get() {
        return new TestResponse("Hello world!");
    }

    @GetMapping("/{name}")
    public TestResponse post(@PathVariable String name) {
        return new TestResponse("Hello " + name + "!");
    }

}

